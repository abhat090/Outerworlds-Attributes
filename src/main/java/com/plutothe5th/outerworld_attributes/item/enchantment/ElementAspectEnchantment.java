package com.plutothe5th.outerworld_attributes.item.enchantment;

import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import com.plutothe5th.outerworld_attributes.api.item.ElementHelper;
import com.plutothe5th.outerworld_attributes.api.item.IElementProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;
import org.jetbrains.annotations.NotNull;

public class ElementAspectEnchantment extends Enchantment {
    public final ElementDamage element;

    public ElementAspectEnchantment(Rarity pRarity, ElementDamage pElement, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, EnchantmentCategory.WEAPON, pApplicableSlots);
        this.element = pElement;
    }

    @Override
    public int getMinCost(int pLevel) {
        return 10 + 20 * (pLevel - 1);
    }

    @Override
    public int getMaxCost(int pLevel) {
        return super.getMinCost(pLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        ElementHelper.doPostAttack(pAttacker, pTarget, pLevel, this.element);
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        if(pOther instanceof ElementAspectEnchantment){
            return false;
        }
        else if(pOther instanceof FireAspectEnchantment){
            return false;
        }
        return super.checkCompatibility(pOther);
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
        return ((IElementProvider)(Object)stack).tm$getElementDamage() == this.element && super.canApplyAtEnchantingTable(stack);
    }
}
