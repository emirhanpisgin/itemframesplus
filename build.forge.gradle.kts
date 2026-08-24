import org.gradle.api.tasks.compile.JavaCompile

plugins {
    id("java")
    id("net.minecraftforge.gradle") version "7.0.34"
    id("forge-mutex")
}

version = "${property("mod.version")}+${sc.current.version}"
base.archivesName = "${property("mod.id") as String}-forge"

val requiredJava: JavaVersion = if (sc.current.parsed >= "26.1") JavaVersion.VERSION_25 else JavaVersion.VERSION_21

minecraft {
    runs {
        configureEach {
            workingDir.convention(rootProject.layout.projectDirectory.dir("run"))
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
    // The EventBus validator annotation processor cannot load the compiler type elements it needs
    // when compiling for Minecraft < 26.1 (--release 21) with a modern JDK, so it's only applied
    // where it works. It only validates @SubscribeEvent usage, which this template doesn't use.
    if (sc.current.parsed >= "26.1") {
        annotationProcessor("net.minecraftforge:eventbus-validator:7.0.5")
    }
}

java {
    withSourcesJar()
}

// Compile with the current JDK but target the Java version each Minecraft version requires.
// A toolchain would force foojay to download a JDK, which failed on this machine.
tasks.withType<JavaCompile>().configureEach {
    options.release.set(requiredJava.majorVersion.toInt())
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
        }

        filesMatching("META-INF/mods.toml") { expand(props) }

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
