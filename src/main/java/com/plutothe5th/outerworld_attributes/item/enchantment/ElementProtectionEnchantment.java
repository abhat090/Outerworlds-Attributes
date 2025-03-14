package com.plutothe5th.outerworld_attributes.item.enchantment;

import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import org.jetbrains.annotations.NotNull;

public class ElementProtectionEnchantment extends Enchantment {
    public final ElementDamage element;

    public ElementProtectionEnchantment(Rarity pRarity, ElementDamage pElement, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, EnchantmentCategory.ARMOR, pApplicableSlots);
        this.element = pElement;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 10 + (pLevel - 1) * 8;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return this.getMinCost(pLevel) + 8;
    }

    @Override
    public int getMaxLevel() {
        return 6;
    }

    @Override
    public int getDamageProtection(int pLevel, @NotNull DamageSource pSource) {
        if(pSource.is(this.element.getDamageResource())){
            return pLevel * 2;
        } else {
            return 0;
        }
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        if (pOther instanceof ElementProtectionEnchantment){
            return false;
        } else if (pOther instanceof ProtectionEnchantment){
            return false;
        }

        return super.checkCompatibility(pOther);
    }
}
