# PluginWizard-Core
Collection of Java utility classes that power plugins created with PluginWizard

<a href="https://github.com/PluginWizard/PluginWizard-Core/actions/">
  <img alt="Build Status" src="https://github.com/PluginWizard/PluginWizard-Core/actions/workflows/publish.yml/badge.svg" />
</a>
<a href="https://central.sonatype.com/artifact/net.kalbskinder/pluginwizard-core">
  <img alt="Maven Central" src="https://img.shields.io/maven-central/v/net.kalbskinder/pluginwizard-core?color=1bcc94&logo=apache-maven" />
</a>

## Adding dependency for Maven

```html
<dependency>
    <groupId>net.kalbskinder</groupId>
    <artifactId>pluginwizard-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Adding dependency for Gradle

```gradle
implementation("net.kalbskinder:pluginwizard-core:1.0.0")
```

## Enabling PluginWizard-Core in your Plugin

```java
private final RegionManager regionManager = new RegionManager(); // Keep track of registered regions

@Override
public void onEnable() {
  Helpers helpers = new Helpers(regionManager);
  helpers.initialize();
}
```

## Using PluginWizard Core Utilities

**Features**
- [Regions](https://github.com/PluginWizard/PluginWizard-Core/tree/main?tab=readme-ov-file#regions)
- [Creating Commands](https://github.com/PluginWizard/PluginWizard-Core/tree/main?tab=readme-ov-file#creating-commands)
- [Location Utilities](https://github.com/PluginWizard/PluginWizard-Core/tree/main?tab=readme-ov-file#location-utilities)
- Item Utilities
- Title & Message Utilities
- [MiniMessage Utilities](https://github.com/PluginWizard/PluginWizard-Core/tree/main?tab=readme-ov-file#minimessage-utilities)
- Sound Utilities
- Teleportation Utilities
- Player Inventory Utilities
- Event Registration (Coming with 1.2.0)
- Database Utilities (Coming with 1.4.0)

### Regions

You can easialy register regions with custom flags (gamerules) and entry/exit events

**Registering a new Region**

```java
Region myRegion = new Region(UUID.randomUUID(), "my_region", Helpers.locationHelper.stringToLocation("world,0,0,0"), Helpers.locationHelper.stringToLocation("world,20,20,20"));
Helpers.regionHelper.addRegion(myRegion);
```

**Registering a Region Event**

```java
myRegion.onRegionEnter(regionEvent -> {
  Helpers.messageHelper.sendMessage(regionEvent.getPlayer(), "<green><bold>Hello!");
});
Helpers.regionHelper.addRegion(myRegion); // Update region entry in the manager
```

### Creating Commands

```java
private final List<CommandHelper> commands = new ArrayList<>(); // Store commands to register later

// Command with a string argument 'x'
// Command '/mycommand <x>'
commands.add(CommandHelper.create("mycommand").stringArg("x")
  .executes(ctx -> {
    Helpers.titleHelper.displayTitle(ctx.getSender(), ctx.getString("x"), 0, 1, 0); // will be executed if the command is triggered
  })
);

// Command with a subcommand, no arguments
// Command '/mycommand help'
commands.add(CommandHelper.create("myplugin").sub("help")
  .executes(ctx -> {
    Bukkit.getServer().getOnlinePlayers().forEach(player -> {
      player.sendMessage(Helpers.miniMessageHelper.parse("Help subcommand executed")) // subcommand code execution
    });
  }).end()
  .executes(ctx -> {}) // command code execution
);

commandManager.registerCommands(commands); // Register all commands
```

### Location Utilities

This utility makes it easy to convert locations to strings to store them in your database

```java
Location location = Helpers.locationHelper.stringToLocation("world,0,64,0"); // convert a String to Location
String locationString = Helpers.locationHelper.locationToString(location); // convert a Location to a String
```

### MiniMessage Utilities

Parse any string (containing `&c` or `<red>` color formats) to a valid text component.

> [!NOTE]
> Easy usage of [Sprites](https://www.gamergeeks.net/apps/minecraft/list-of-atlas-sprites) in text messages is expected soon.
>

```java
Component textComponent = Helpers.miniMessageHelper.parse("<red>Hello &r&l&aWorld!");
```
