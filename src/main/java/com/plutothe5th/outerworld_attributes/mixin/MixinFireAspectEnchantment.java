package com.plutothe5th.outerworld_attributes.mixin;

import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import com.plutothe5th.outerworld_attributes.api.item.IElementProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.FireAspectEnchantment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireAspectEnchantment.class)
public abstract class MixinFireAspectEnchantment extends Enchantment {
    protected MixinFireAspectEnchantment(Enchantment.Rarity pRarity, EquipmentSlot... pApplicableSlots) {
        super(pRarity, EnchantmentCategory.WEAPON, pApplicableSlots);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ((IElementProvider)(Object)stack).tm$getElementDamage() == ElementDamage.FIRE && super.canApplyAtEnchantingTable(stack);
    }
}
