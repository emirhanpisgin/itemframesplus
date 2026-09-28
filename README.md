# Item Frames+

A Minecraft mod that makes item frames more useful by shrinking hitboxes and making them invisible.

## Features

- **Invisible item frames**: Toggle item frame visibility with a client-side command
- **Shrunk hitboxes**: Item frames have smaller collision boxes for easier placement
- **Per-player preferences**: Each player can independently toggle visibility
- **Cross-version support**: Works on Minecraft 1.16.5 through 26.3 (Fabric and Forge)

## Commands

```
/itemframesplus invisibleItemFrames <true|false>
```

Toggle whether item frames appear invisible to you.

## Installation

**Fabric**

1. Install [Fabric Loader](https://fabricmc.net/) for your Minecraft version
2. Download the latest release from [Modrinth](https://modrinth.com/mod/itemframesplus)
3. Place the mod jar in your `mods` folder
4. Requires [Fabric API](https://modrinth.com/mod/fabric-api)

**Forge**

1. Install [Forge](https://files.minecraftforge.net/) for your Minecraft version
2. Download the latest release from [Modrinth](https://modrinth.com/mod/itemframesplus)
3. Place the mod jar in your `mods` folder

## Building

```sh
# Build all versions
gradlew buildAndCollect

# Run a specific version's client
gradlew :1.21.4-fabric:runClient
```

Requires JDK 8+ (Java version varies by target MC version, configured automatically via toolchains).

## Version Matrix

### Fabric

| Minecraft | Node            | Range           |
|-----------|-----------------|-----------------|
| 1.16.5    | 1.16.5-fabric   | >=1.16.5 <1.19  |
| 1.19      | 1.19-fabric     | >=1.19 <1.20.5  |
| 1.20.5    | 1.20.5-fabric   | >=1.20.5 <1.21  |
| 1.21      | 1.21-fabric     | >=1.21 <1.21.4  |
| 1.21.4    | 1.21.4-fabric   | >=1.21.4 <1.21.9|
| 1.21.11   | 1.21.9-fabric   | >=1.21.9 <26.1  |
| 26.1      | 26.1-fabric     | >=26.1 <26.2    |
| 26.2      | 26.2-fabric     | >=26.2          |

### Forge

Each node builds one jar that covers a range of Minecraft versions.

| Minecraft | Node          | Range              | Forge version |
|-----------|---------------|--------------------|---------------|
| 1.18      | 1.18-forge    | >=1.18 <1.19       | 38.0.17       |
| 1.19      | 1.19-forge    | >=1.19 <1.20.2     | 41.1.0        |
| 1.20.2    | 1.20.2-forge  | >=1.20.2 <1.21     | 48.1.0        |
| 1.21      | 1.21-forge    | >=1.21 <1.21.3     | 51.0.3        |
| 1.21.3    | 1.21.3-forge  | >=1.21.3 <1.21.6   | 53.1.12       |
| 1.21.6    | 1.21.6-forge  | >=1.21.6 <1.21.11  | 56.0.9        |
| 1.21.11   | 1.21.11-forge | >=1.21.11 <26.1    | 61.2.0        |
| 26.1      | 26.1-forge    | >=26.1             | 62.0.9        |

## License

MIT, see [LICENSE](LICENSE).
