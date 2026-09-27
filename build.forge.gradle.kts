import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    id("java")
    id("net.minecraftforge.gradle") version "7.0.34"
    id("forge-mutex")
}

version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = "${property("mod.id") as String}-forge"

// FG7's mavenizer recompiles Minecraft+Forge sources with the JVM that runs this
// build. Older ModLauncher/ASM releases cannot read bytecode emitted by newer
// JDKs, so some Minecraft versions require building on a specific JDK.
// Declared per-node as `session_jdk` in stonecutter.properties.toml; enforced here
// so misconfigured sessions fail fast with instructions instead of cryptic ASM
// errors deep inside a game launch.
val expectedJdk = sc.properties["session_jdk"]
if (expectedJdk != null) {
    val current = JavaVersion.current().majorVersion.toInt()
    val required = (expectedJdk as String).toInt()
    if (current != required) {
        throw GradleException(
            "Project ':${project.name}' must be built on Java $required, but this Gradle daemon runs Java $current.\n" +
            "Stop daemons (`gradlew --stop`) and re-run with JAVA_HOME set to a Java $required installation."
        )
    }
}

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    else -> JavaVersion.VERSION_1_8
}

// Forge < 1.17 bundles LWJGL 3.2.2 whose package sealing breaks on JDK 17+.
// Resolve a JDK 8 launcher at configuration time for those versions.
val jdk8Launcher = if (sc.current.parsed < "1.17") {
    javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
} else null

minecraft {
    mappings("official", sc.current.version)

    runs {
        configureEach {
            workingDir.convention(rootProject.layout.projectDirectory.dir("run"))
            // Forge < 1.17 ships LWJGL 3.2.2 with internal class hierarchy changes that fail
            // bytecode verification. JDK 8 supports -noverify natively.
            if (sc.current.parsed < "1.17") jvmArgs("-noverify")
        }

        register("client")
        register("server")
    }
}

repositories {
    minecraft.mavenizer(this)
    maven(fg.forgeMaven)
    maven(fg.minecraftLibsMaven)
    mavenCentral()
}

// modlauncher requires the `jopt.simple` module, which only jopt-simple 5.0.4 provides.
// Newer releases (6.0-alpha-3, pulled in via modlauncher/accesstransformers) declare the
// automatic module name `joptsimple`, so the run fails at startup with
// "Module jopt.simple not found, required by cpw.mods.modlauncher".
configurations.configureEach {
    resolutionStrategy {
        force("net.sf.jopt-simple:jopt-simple:5.0.4")
    }
}

dependencies {
    implementation(minecraft.dependency("net.minecraftforge:forge:${sc.current.version}-${property("deps.forge_loader")}"))
    // Mixin 0.8.x's service bootstrap requires LaunchClassLoader on the classpath for
    // legacy (launchwrapper-based) Forge; excluded LWJGL 2 collides with Forge's LWJGL 3.
    if (sc.current.parsed < "1.17") {
        "runtimeOnly"("net.minecraft:launchwrapper:1.12") {
            exclude(group = "org.lwjgl.lwjgl")
        }
    }
    // The EventBus validator annotation processor cannot load the compiler type elements it needs
    // when compiling for older Minecraft with a modern JDK, so it's only applied where it works.
    // It only validates @SubscribeEvent usage, which this template doesn't use.
    if (sc.current.parsed >= "26.1") {
        annotationProcessor("net.minecraftforge:eventbus-validator:7.0.5")
    }
}

java {
    withSourcesJar()
}

// Compile with the current JDK but target the Java version each Minecraft version requires.
tasks.withType<JavaCompile>().configureEach {
    options.release.set(requiredJava.majorVersion.toInt())
}

// Run tasks on the resolved JDK: <1.17 must launch under Java 8.
if (jdk8Launcher != null) {
    tasks.withType<JavaExec>().configureEach {
        if (name == "runClient" || name == "runServer") {
            javaLauncher.set(jdk8Launcher)
        }
    }
}

tasks {
    processResources {
        fun MutableMap<String, String>.register(key: String, property: String) {
            val value: String = sc.properties[property]
            inputs.property(key, value)
            set(key, value)
        }

        val props = buildMap {
            register("id", "mod.id")
            register("name", "mod.name")
            register("version", "mod.version")
            register("minecraft", "mod.mc_compat")
            register("fml", "deps.forge_fml")
            register("pack_format", "pack_format")
        }

        filesMatching("META-INF/mods.toml") { expand(props) }
        filesMatching("pack.mcmeta") { expand(props) }

        exclude("fabric.mod.json", "META-INF/neoforge.mods.toml")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", project.property("mod.version"))
        from(jar.flatMap { it.archiveFile }, named<Jar>("sourcesJar").flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
    }
}
