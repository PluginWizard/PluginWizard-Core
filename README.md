<h1 align="center">PluginWizard-Core</h1>

<p align="center">
    <a href="https://github.com/PluginWizard/PluginWizard-Core/actions/">
        <img alt="Build Status" src="https://github.com/PluginWizard/PluginWizard-Core/actions/workflows/publish.yml/badge.svg" />
    </a>
    <a href="https://central.sonatype.com/artifact/net.kalbskinder/pluginwizard-core">
      <img alt="Maven Central" src="https://img.shields.io/maven-central/v/net.kalbskinder/pluginwizard-core?color=1bcc94&logo=apache-maven" />
    </a>
    <a href="https://javadoc.io/doc/net.kalbskinder/pluginwizard-core/latest/index.html">
      <img alt="Javadoc" src="https://javadoc.io/badge2/net.kalbskinder/pluginwizard-core/javadoc.svg" />
    </a>
</p>


<p align="center">
    Collection of Java utility classes that make plugin development easier.<br>
    ❤️ Powers plugins created with <a href="https://github.com/PluginWizard/PluginWizard">PluginWizard</a> (Coming summer 2026 🌴)
</p>


## Adding dependency for Maven

```html
<dependency>
    <groupId>net.kalbskinder</groupId>
    <artifactId>pluginwizard-core</artifactId>
    <version>1.3.1</version>
</dependency>
```

## Adding dependency for Gradle

```gradle
implementation("net.kalbskinder:pluginwizard-core:1.3.1")
```

## Enabling PluginWizard-Core in your Plugin

```java
@Override
public void onEnable() {
    Helpers.initialize(this);
}
```

## Using PluginWizard Core Utilities

**Features**
- [Regions](https://github.com/PluginWizard/PluginWizard-Core/tree/main?tab=readme-ov-file#regions)
- [Creating Commands](https://github.com/PluginWizard/PluginWizard-Core/tree/main?tab=readme-ov-file#creating-commands)
- [Event Listeners](https://github.com/PluginWizard/PluginWizard-Core/tree/main?tab=readme-ov-file#event-listeners)
- [Location Utilities](https://github.com/PluginWizard/PluginWizard-Core/tree/main?tab=readme-ov-file#location-utilities)
- [Item Utilities](https://github.com/PluginWizard/PluginWizard-Core?tab=readme-ov-file#item-utilities)
- Title & Message Utilities
- [Sprite Utilities](https://github.com/PluginWizard/PluginWizard-Core?tab=readme-ov-file#sprite-utilities)
- [MiniMessage Utilities](https://github.com/PluginWizard/PluginWizard-Core/tree/main?tab=readme-ov-file#minimessage-utilities)
- Sound Utilities
- Teleportation Utilities
- Player Inventory Utilities
- Database Utilities (Coming with 1.4.0)

### Regions

You can easily register regions with custom flags (game rules) and entry/exit events

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

CommandManager#registerCommands(commands); // Register all commands
```

## Event Listeners

Easily register event listeners without creating a new class that implements `Listener`

```java
// Sends a welcome message to players when they join the server ("[+] {playerName} welcome to the server")
eventHelper.subscribe(PlayerJoinEvent.class, event -> {
    event.getPlayer().sendMessage(miniMessageHelper.parse("&7[&a+&7] &f%s welcome to the server!".formatted(event.getPlayer().getName())));
});
```

### Location Utilities

This utility makes it easy to convert locations to strings to store them in your database

```java
Location location = Helpers.locationHelper.stringToLocation("world,0,64,0"); // convert a String to Location
String locationString = Helpers.locationHelper.locationToString(location); // convert a Location to a String
```

### Item Utilities

Create custom items with custom names and lore.

```java
ItemStack item = itemHelper.newItem()
        .name("<blue><bold>My Custom Item")
        .material(Material.DIAMOND_SWORD)
        .amount(2)
        .enchant(Enchantment.UNBREAKING, 3)
        .lore(Arrays.asList("line1", null, "line2"))
        .itemFlag(ItemFlag.HIDE_ATTRIBUTES)
        .build();

// Place the item into the first hotbar slot of a player
Helpers.playerItemHelper.setItem(item, 0, item.getAmount(), player);
```

### Sprite Utilities

Create texts with custom sprites.\
Supported sprites: [javadoc - helpers.sprites.types](https://javadoc.io/doc/net.kalbskinder/pluginwizard-core/latest/net/kalbskinder/helpers/sprites/types/package-summary.html)

```java
String text = Sprite.of(BlockSprite.DIAMOND_BLOCK) + " Hello World!";
Component textComponent =  Sprite.parse(text);
```

### MiniMessage Utilities

Parse any string (containing `&c` or `<red>` color formats) to a valid text component.

```java
Component textComponent = Helpers.miniMessageHelper.parse("<red>Hello &r&l&aWorld!");
```
