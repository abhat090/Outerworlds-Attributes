package com.plutothe5th.outerworld_attributes.registries;

import com.plutothe5th.outerworld_attributes.OuterworldAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, OuterworldAttributes.MOD_ID);

    public static final RegistryObject<Attribute> FIRE_DAMAGE = elementAttribute("fire_damage");
    public static final RegistryObject<Attribute> WATER_DAMAGE = elementAttribute("water_damage");
    public static final RegistryObject<Attribute> NATURE_DAMAGE = elementAttribute("nature_damage");
    public static final RegistryObject<Attribute> ENDER_DAMAGE = elementAttribute("ender_damage");

    public static final RegistryObject<Attribute> FIRE_DEFENSE = elementAttribute("fire_defense");
    public static final RegistryObject<Attribute> WATER_DEFENSE = elementAttribute("water_defense");
    public static final RegistryObject<Attribute> NATURE_DEFENSE = elementAttribute("nature_defense");
    public static final RegistryObject<Attribute> ENDER_DEFENSE = elementAttribute("ender_defense");

    public static void register(IEventBus eventBus){
        ATTRIBUTES.register(eventBus);
    }

    private static RegistryObject<Attribute> elementAttribute(String name){
        return ATTRIBUTES.register("generic.outerworlds." + name, () ->
                new RangedAttribute("attribute.outerworlds.name.generic." + name, 0.0, 0.0, 2048.0));
    }
}
