package com.plutothe5th.outerworld_attributes.api.item;

import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import com.plutothe5th.outerworld_attributes.item.enchantment.ElementProtectionEnchantment;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import org.apache.commons.lang3.mutable.MutableInt;

public class ElementHelper {
    public static int getDamageProtection(Iterable<ItemStack> pEquipment, DamageSource pSource){
        MutableInt mutableInt = new MutableInt();
        EnchantmentHelper.runIterationOnInventory((enchantment, level) -> {
            if(enchantment instanceof ProtectionEnchantment pEnchantment){
                if(pEnchantment.type == ProtectionEnchantment.Type.FIRE){
                    mutableInt.add(pEnchantment.getDamageProtection(level, pSource));
                }
            } else if(enchantment instanceof ElementProtectionEnchantment pEnchantment1) {
                mutableInt.add(pEnchantment1.getDamageProtection(level, pSource));
            }
        }, pEquipment);

        return mutableInt.intValue();
    }

    public static void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel, ElementDamage pElement){
        switch (pElement){
            case FIRE -> {
                break; // Handled in Fire Aspect
            }
            case NATURE -> {
                doNatureEffects(pAttacker, pLevel);
            }
            case WATER -> {
                doWaterEffects(pTarget, pLevel);
            }
            case ENDER, NULL -> {
                break;
            }
        }
    }

    private static void doWaterEffects(Entity pTarget, int pLevel){
        if (pTarget instanceof LivingEntity){
            ((LivingEntity) pTarget).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, pLevel - 1));
        }
    }

    private static void doNatureEffects(LivingEntity pAttacker, int pLevel){
        pAttacker.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 80, pLevel - 1));
    }
}
