# Outerworlds: Attributes
### Version 1.1.0 | Minecraft 1.20.1 (Forge)

> Authored by: [Plutothe5th](https://github.com/abhat090)

##### Heavy mixin based mod that reworks the damage system in game:

###### _As of v1.1.0:_
- Adds 4 new damage types to the game: Fire, Water, Nature and Ender
- Adds relative attributes to all Living Entities for each element (AD and Defense)
- Adds ability to give any item a typing, as well as respective type damage, working seamlessly with the attribute system
- Added both offensive and defensive enchantments, as well as tweaking vanilla fire enchantments to interact with the new fire type (Fire Aspect & Protection)
- Configurable implicit weaknesses and resistances for mobs*

### Config:
Configuration is available as part of the common config in config/outerworld_attributes-common.toml, which contains the scaling for weaknesses and resistances.
Weaknesses and resistances for individual entities can be added through the otherworld folder in the main game directory. The two .json files contain the example format for adding these.

### Types & Enchantments:
The aspect enchantments (including Fire Aspect) can no longer be applied to a regular weapon, and instead can only be applied to their respective type of weapon:

- Fire: Also named as Ashen, inherits the Fire Aspect enchantment
- Water: Also named as Tidal, inherits the Water Aspect enchantment (applies slowness)
- Nature: Also named as Voltaic, inherits the Nature Aspect enchantment (gives haste on hit)

