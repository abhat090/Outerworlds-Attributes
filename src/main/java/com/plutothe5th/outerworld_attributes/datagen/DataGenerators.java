package com.plutothe5th.outerworld_attributes.datagen;

import com.plutothe5th.outerworld_attributes.OuterworldAttributes;
import com.plutothe5th.outerworld_attributes.api.ElementDamageTypes;
import com.plutothe5th.outerworld_attributes.config.EntityWeaknesses;
import com.plutothe5th.outerworld_attributes.datagen.providers.ModDamageTypeTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = OuterworldAttributes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, ElementDamageTypes::bootstrap);

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        var packProvider = generator.addProvider(true,
                new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, BUILDER, Set.of(OuterworldAttributes.MOD_ID)));

        generator.addProvider(true,
                new ModDamageTypeTagsProvider(packOutput, packProvider.getRegistryProvider(), existingFileHelper));

        EntityWeaknesses.init();
    }
}
