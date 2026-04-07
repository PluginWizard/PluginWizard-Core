# PluginWizard-Core
PluginWizard java utility classes that power plugins created with PluginWizard

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

## Using PluginWizard Core Utilities

**Features**
- [Regions]('')
- Creating Commands
- Event Registration
- Location Utilities
- Item Utilities
- Title Utilities

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
