# Item Frames+

A Minecraft mod that makes item frames more useful by shrinking hitboxes and making them invisible.

## Features

- **Invisible item frames**: Toggle item frame visibility with a client-side command
- **Shrunk hitboxes**: Item frames have smaller collision boxes for easier placement
- **Per-player preferences**: Each player can independently toggle visibility
- **Cross-version support**: Works on Minecraft 1.16.5 through 26.3

## Commands

```
/itemframesplus invisibleItemFrames <true|false>
```

Toggle whether item frames appear invisible to you.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/) for your Minecraft version
2. Download the latest release from [Modrinth](https://modrinth.com/mod/itemframesplus)
3. Place the mod jar in your `mods` folder
4. Requires [Fabric API](https://modrinth.com/mod/fabric-api)

## Building

```sh
# Build all versions
gradlew buildAndCollect

# Run a specific version's client
gradlew :1.21.4-fabric:runClient
```

Requires JDK 8+ (Java version varies by target MC version, configured automatically via toolchains).

## Version Matrix

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

## License

MIT, see [LICENSE](LICENSE).
