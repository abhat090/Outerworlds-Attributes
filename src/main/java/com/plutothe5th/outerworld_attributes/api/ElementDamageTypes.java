package com.plutothe5th.outerworld_attributes.api;

import com.plutothe5th.outerworld_attributes.OuterworldAttributes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface ElementDamageTypes {
    ResourceKey<DamageType> ELEMENT_FIRE = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(OuterworldAttributes.MOD_ID, "element_fire"));
    ResourceKey<DamageType> ELEMENT_WATER = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(OuterworldAttributes.MOD_ID, "element_water"));
    ResourceKey<DamageType> ELEMENT_NATURE = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(OuterworldAttributes.MOD_ID, "element_nature"));
    ResourceKey<DamageType> ELEMENT_ENDER = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(OuterworldAttributes.MOD_ID, "element_ender"));

    static void bootstrap(BootstapContext<DamageType> pContext){
        pContext.register(ELEMENT_FIRE, new DamageType("outerworld_attributes.element_fire", 0.1f));
        pContext.register(ELEMENT_WATER, new DamageType("outerworld_attributes.element_water", 0.1f));
        pContext.register(ELEMENT_NATURE, new DamageType("outerworld_attributes.element_nature", 0.1f));
        pContext.register(ELEMENT_ENDER, new DamageType("outerworld_attributes.element_ender", 0.1f));
    }
}
