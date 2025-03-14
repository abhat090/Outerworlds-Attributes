package com.plutothe5th.outerworld_attributes.registries;

import com.plutothe5th.outerworld_attributes.OuterworldAttributes;
import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import com.plutothe5th.outerworld_attributes.item.enchantment.ElementAspectEnchantment;
import com.plutothe5th.outerworld_attributes.item.enchantment.ElementProtectionEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, OuterworldAttributes.MOD_ID);

    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public static final RegistryObject<Enchantment> WATER_PROTECTION = ENCHANTMENTS.register("water_protection",
            () -> new ElementProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ElementDamage.WATER, ARMOR_SLOTS));
    public static final RegistryObject<Enchantment> NATURE_PROTECTION = ENCHANTMENTS.register("nature_protection",
            () -> new ElementProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ElementDamage.NATURE, ARMOR_SLOTS));
    public static final RegistryObject<Enchantment> ENDER_PROTECTION = ENCHANTMENTS.register("ender_protection",
            () -> new ElementProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ElementDamage.ENDER, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> WATER_ASPECT = ENCHANTMENTS.register("water_aspect",
            () -> new ElementAspectEnchantment(Enchantment.Rarity.UNCOMMON, ElementDamage.WATER, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static final RegistryObject<Enchantment> NATURE_ASPECT = ENCHANTMENTS.register("nature_aspect",
            () -> new ElementAspectEnchantment(Enchantment.Rarity.UNCOMMON, ElementDamage.NATURE, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));

    public static void register(IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }
}
