# Jank Addons
[Fabric carpet](https://github.com/gnembon/fabric-carpet) extension that adds features which are missing from the original carpet and existing extensions.

Feel free to contribute or suggest features which you thing should be present in this extension.

## Index
* [Rules Index](#Rules)
* [Commands](#Commands)

## Rules

* [fungusRandomTickGrow](#fungusRandomTickGrow)
* [harvestableTallGrassFern](#harvestableTallGrassFern)
* [movableEnderChest](#movableEnderChest)
* [movableEndPortalFrame](#movableEndPortalFrame)
* [stackableFreshBows](#stackableFreshBows)
* [commandPortalMonitor](#commandPortalMonitor)

# Features

## fungusRandomTickGrow
Makes nylium and crimson fungus grow if they are random ticked
* Type: `Boolean`
* Default Value: `false`
* Required Options: `false`, `true`
* Category: `JANK`, `SURVIVAL`, `FEATURE`

## harvestableTallGrassFern
Makes tall grass and large fern harvestable with shears
* Type: `Boolean`
* Default Value: `false`
* Required Options: `false`, `true`
* Category: `JANK`, `SURVIVAL`, `FEATURE`

## movableEnderChest
Makes ender chests movable
* Type: `Boolean`
* Default Value: `false`
* Required Options: `false`, `true`
* Category: `JANK`, `SURVIVAL`, `EXPERIMENTAL`

## movableEndPortalFrame
Makes end portal frames movable by PUSH ONLY. Eye less portals only. Similar to how Glazed Terracotta works.
* Type: `Boolean`
* Default Value: `false`
* Required Options: `false`, `true`
* Category: `JANK`, `SURVIVAL`, `EXPERIMENTAL`

## stackableFreshBows
Makes undamaged, unenchanted bows stackable. Easier to masscraft dispensers now.
* Type: `Boolean`
* Default Value: `false`
* Required Options: `false`, `true`
* Category: `JANK`, `SURVIVAL`, `FEATURE`

## commandPortalMonitor
Enables `/portalmonitor` command to monitor portals loaders  
For usage of command, please refer [portalmonitor](#portalmonitor)
* Type: `String`
* Default Value: `false`
* Required Options: `false`, `true`, `ops`
* Category: `JANK`, `SURVIVAL`, `COMMAND`

# Commands
## portalmonitor
**Requires rule** [commandPortalMonitor](#commandPortalMonitor)  
ps: everytime I mention 'portal', it means nether portal.  

With the command `/portalmonitor` you can track which portal is being triggered how many times by which entity.  
Useful when trying to diagnose lag which may be coming from an unknown chunk loaded location.  
Or checking if your chunk loaders are correctly working

### Features
* To list all the active portals, use `/portalmonitor listLoaders`
  * To name a loader and adding it to permanent tracked list, use `/portalmonitor addLoader <name>`
  * To remove a named loader, use `/portalmonitor removeLoader <name>`
* To list all the tracked entities, use `/portalmonitor listEntities`
  * To add an entity to tracked entity list, use `/portalmonitor addEntity <entity>`
  * To remove an entity from tracked list, use `/portalmonitor removeEntity <entity>`

### Notes
* If a portal is not named, it will generate a name for it `<EntityType><AtomicNumber>`
