package com.plutothe5th.outerworld_attributes.config;

import com.google.gson.*;
import com.mojang.logging.LogUtils;
import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityWeaknesses {
    public enum TYPE{
        WEAKNESS,
        RESISTANCE
    }
    private static Logger LOGGER = LogUtils.getLogger();

    private static Map<EntityType<?>, Set<ElementDamage>> weakness_map = new HashMap<>();
    private static Map<EntityType<?>, Set<ElementDamage>> resistance_map = new HashMap<>();

    public static void init(){
        Path path = FMLPaths.getOrCreateGameRelativePath(Path.of("otherworld"));

        File weaknessPolicy = path.resolve("weaknesspolicy.json").toFile();
        File resistancePolicy = path.resolve("resistancepolicy.json").toFile();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!weaknessPolicy.exists()){
            makeNewPolicy(weaknessPolicy, gson, ElementDamage.WATER.getKey(), "minecraft:blaze");
        }

        if (!resistancePolicy.exists()){
            makeNewPolicy(resistancePolicy, gson, ElementDamage.FIRE.getKey(), "minecraft:creeper");
        }

        readPolicy(weaknessPolicy, TYPE.WEAKNESS);
        readPolicy(resistancePolicy, TYPE.RESISTANCE);
    }

    public static boolean isWeakTo(Entity entity, ElementDamage element){
        boolean isWeak = false;
        if(weakness_map.containsKey(entity.getType())){
            isWeak = weakness_map.get(entity.getType()).contains(element);
        }

        return isWeak;
    }

    public static boolean isResistantTo(Entity entity, ElementDamage element){
        boolean isWeak = false;
        if(resistance_map.containsKey(entity.getType())){
            isWeak = resistance_map.get(entity.getType()).contains(element);
        }

        return isWeak;
    }

    private static void makeNewPolicy(File policy, Gson gson, String element, String mob){
        try{
            policy.createNewFile();

            JsonObject object = new JsonObject();
            JsonArray array = new JsonArray();
            array.add(element);
            object.add(mob, array);

            Files.writeString(policy.toPath(), gson.toJson(object));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void readPolicy(File policy, TYPE type){
        Gson gson = new Gson();

        try {
            JsonObject object = gson.fromJson(Files.readString(policy.toPath()), JsonObject.class);
            Set<String> keySet = object.keySet();

            for (String key : keySet){
                EntityType<?> entityType = EntityType.byString(key).orElseThrow();

                Set<ElementDamage> new_policy = new HashSet<>();
                JsonArray jsonArray = object.getAsJsonArray(key);

                for (JsonElement j: jsonArray){
                    new_policy.add(ElementDamage.fromString(j.getAsString(), null, false));
                }

                switch (type){
                    case WEAKNESS -> {
                        weakness_map.put(entityType, new_policy);
                    }
                    case RESISTANCE -> resistance_map.put(entityType, new_policy);
                }
            }

        } catch (Exception e) {
            LOGGER.error("{} rejected the elements", e.getClass());
            throw new RuntimeException(e);
        }
    }
}
