package com.plutothe5th.outerworld_attributes.datagen.providers;

import com.plutothe5th.outerworld_attributes.OuterworldAttributes;
import com.plutothe5th.outerworld_attributes.api.ElementDamageTypes;
import com.plutothe5th.outerworld_attributes.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends TagsProvider<DamageType> {
    public ModDamageTypeTagsProvider(
            PackOutput packOutput,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, Registries.DAMAGE_TYPE, lookupProvider, OuterworldAttributes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.ElementalDamageType.IS_ELEMENTAL)
                .add(ElementDamageTypes.ELEMENT_FIRE)
                .add(ElementDamageTypes.ELEMENT_WATER)
                .add(ElementDamageTypes.ELEMENT_NATURE)
                .add(ElementDamageTypes.ELEMENT_ENDER);

        this.tag(DamageTypeTags.BYPASSES_ARMOR)
                .addTag(ModTags.ElementalDamageType.IS_ELEMENTAL);

        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS)
                .addTag(ModTags.ElementalDamageType.IS_ELEMENTAL);

        this.tag(DamageTypeTags.IS_FIRE)
                .add(ElementDamageTypes.ELEMENT_FIRE);
    }
}
