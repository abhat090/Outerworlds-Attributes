package com.plutothe5th.outerworld_attributes;

import com.mojang.logging.LogUtils;
import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import com.plutothe5th.outerworld_attributes.config.CommonConfig;
import com.plutothe5th.outerworld_attributes.config.EntityWeaknesses;
import com.plutothe5th.outerworld_attributes.registries.ModAttributes;
import com.plutothe5th.outerworld_attributes.registries.ModEnchantments;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(OuterworldAttributes.MOD_ID)
public class OuterworldAttributes {

    public static final String MOD_ID = "outerworld_attributes";
    private static final Logger LOGGER = LogUtils.getLogger();

    public OuterworldAttributes(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        context.registerConfig(ModConfig.Type.COMMON, CommonConfig.init());

        ModAttributes.register(modEventBus);
        ModEnchantments.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        EntityWeaknesses.init();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
//        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
//        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
