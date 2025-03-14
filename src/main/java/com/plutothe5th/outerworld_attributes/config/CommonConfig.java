package com.plutothe5th.outerworld_attributes.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static ForgeConfigSpec.DoubleValue ELEMENTAL_WEAKNESS_SCALE;
    public static ForgeConfigSpec.DoubleValue ELEMENTAL_RESISTANCE_SCALE;

    public static ForgeConfigSpec init(){
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("Living Entity Weakness and Resistance");

        ELEMENTAL_WEAKNESS_SCALE = builder.defineInRange("WeaknessScale", 1.5, 1.0, 1024.0);
        ELEMENTAL_RESISTANCE_SCALE = builder.defineInRange("ResistanceScale", 0.5, 0.0, 1.0);

        builder.pop();

        return builder.build();
    }
}
