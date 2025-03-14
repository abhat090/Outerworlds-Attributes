package com.plutothe5th.outerworld_attributes.api;

import com.plutothe5th.outerworld_attributes.registries.ModAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.annotation.Nullable;
import java.util.UUID;

public enum ElementDamage {
    FIRE(ChatFormatting.GOLD, "\uD83D\uDD25", "fire",
            ElementDamageTypes.ELEMENT_FIRE,
            ModAttributes.FIRE_DAMAGE.get(),
            ModAttributes.FIRE_DEFENSE.get()),
    NATURE(ChatFormatting.YELLOW, "\u26A1", "nature",
            ElementDamageTypes.ELEMENT_NATURE,
            ModAttributes.NATURE_DAMAGE.get(),
            ModAttributes.NATURE_DEFENSE.get()),
    WATER(ChatFormatting.DARK_AQUA, "\u2635", "water",
            ElementDamageTypes.ELEMENT_WATER,
            ModAttributes.WATER_DAMAGE.get(),
            ModAttributes.WATER_DEFENSE.get()),
    ENDER(ChatFormatting.DARK_PURPLE, "\u2749", "ender",
            ElementDamageTypes.ELEMENT_ENDER,
            ModAttributes.ENDER_DAMAGE.get(),
            ModAttributes.ENDER_DEFENSE.get()),
    NULL(ChatFormatting.LIGHT_PURPLE, "\u05D0", "null");

    private final ChatFormatting color;
    private final String iconLiteral;
    private final String key;
    private Float damage;
    private boolean implied;

    private final ResourceKey<DamageType> damageType;
    private final Attribute attackAttribute;
    private final Attribute defenseAttribute;

    public static final String TAG_KEY = "element";

    public static final UUID FIRE_DAMAGE_UUID = UUID.fromString("f962a963-1e31-4b7f-8d1f-06330f08e8b0");
    public static final UUID NATURE_DAMAGE_UUID = UUID.fromString("dbd8c69a-4404-4c37-803c-374b83cfd8a0");
    public static final UUID WATER_DAMAGE_UUID = UUID.fromString("82c01071-b79d-4695-802f-4210ef6507c8");
    public static final UUID ENDER_DAMAGE_UUID = UUID.fromString("a30efddd-43e5-4833-96b6-e0e7fa6f1215");
    public static final UUID NULL_DAMAGE_UUID = UUID.fromString("21dbd362-8499-4f2f-b769-0a8d188f518a");

    ElementDamage(ChatFormatting color, String iconLiteral, String key,
                  ResourceKey<DamageType> damageType, Attribute attackAttribute, Attribute defenseAttribute) {
        this.color = color;
        this.iconLiteral = iconLiteral;
        this.key = key;
        this.damageType = damageType;
        this.attackAttribute = attackAttribute;
        this.defenseAttribute = defenseAttribute;
        this.damage = 0.0f;
        this.implied = false;
    }

    ElementDamage(ChatFormatting color, String iconLiteral, String key) {
        this(color, iconLiteral, key, DamageTypes.PLAYER_ATTACK, Attributes.ATTACK_DAMAGE, Attributes.ARMOR);
    }

    public ChatFormatting getColor() {
        return color;
    }

    public String getIconLiteral() {
        return iconLiteral;
    }

    public String getKey() {
        return key;
    }

    public CompoundTag getAsTag(){
        CompoundTag tag = new CompoundTag();
        CompoundTag elementTag = new CompoundTag();

        elementTag.putString("name", this.key);
        elementTag.putFloat("damage", this.damage);
        elementTag.putBoolean("impl", this.implied);
        tag.put(TAG_KEY, elementTag);

        return tag;
    }

    public static ElementDamage getElement(CompoundTag tag){
        ElementDamage element = NULL;
        if (tag.contains(TAG_KEY)){
            CompoundTag elementTag = tag.getCompound(TAG_KEY);
            Float damage = null;
            String type = "";
            boolean impl = false;

            if(elementTag.contains("type")) type = elementTag.getString("type");
            if(elementTag.contains("damage")) damage = elementTag.getFloat("damage");
            if(elementTag.contains("impl")) impl = elementTag.getBoolean("impl");

            element = fromString(type, damage, impl);
        }
        return element;
    }

    public static ElementDamage fromString(String name, @Nullable Float damage, boolean implied){
        ElementDamage element = NULL;

        switch (name){
            case "fire" -> element = FIRE;
            case "nature" -> element = NATURE;
            case "water" -> element = WATER;
            case "ender" -> element = ENDER;
            default -> {}
        }

        return damage != null ? element.withDamage(damage).implied(implied) : element.implied(implied);
    }

    public ElementDamage withDamage(float damage){
        this.damage = damage;
        return this;
    }

    public ElementDamage implied(boolean implied){
        this.implied = implied;
        return this;
    }

    public float getDamage(){
        return this.damage;
    }

    public boolean isImplied(){
        return this.implied;
    }

    public ResourceKey<DamageType> getDamageResource(){
        return this.damageType;
    }

    public Attribute getAttackAttribute(){
        return this.attackAttribute;
    }

    public Attribute getDefenseAttribute(){
        return this.defenseAttribute;
    }

    public AttributeModifier getModifier(){
        return switch (this){
            case FIRE -> new AttributeModifier(FIRE_DAMAGE_UUID, "Fire Damage", this.damage, AttributeModifier.Operation.ADDITION);
            case NATURE -> new AttributeModifier(NATURE_DAMAGE_UUID, "Nature Damage", this.damage, AttributeModifier.Operation.ADDITION);
            case WATER -> new AttributeModifier(WATER_DAMAGE_UUID, "Water Damage", this.damage, AttributeModifier.Operation.ADDITION);
            case ENDER -> new AttributeModifier(ENDER_DAMAGE_UUID, "Ender Damage", this.damage, AttributeModifier.Operation.ADDITION);
            case NULL -> new AttributeModifier(NULL_DAMAGE_UUID, "Null Damage", this.damage, AttributeModifier.Operation.ADDITION);
        };
    }
}
