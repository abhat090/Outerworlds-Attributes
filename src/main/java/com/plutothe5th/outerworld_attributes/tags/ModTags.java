package com.plutothe5th.outerworld_attributes.tags;

import com.plutothe5th.outerworld_attributes.OuterworldAttributes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class ModTags {

    public static class ElementalDamageType{
        public static final TagKey<DamageType> IS_ELEMENTAL = create("is_elemental");

        public static TagKey<DamageType> create(String pName){
            return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(OuterworldAttributes.MOD_ID, pName));
        }

    }
}
