package com.plutothe5th.outerworld_attributes.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import com.plutothe5th.outerworld_attributes.api.entity.IElementDamageRecipient;
import com.plutothe5th.outerworld_attributes.api.item.IElementProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {
    protected MixinPlayer(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", ordinal = 0, shift = At.Shift.AFTER),
            remap = false,
            cancellable = true
    )
    private void onAttack(Entity pTarget, CallbackInfo ci, @Local(ordinal = 2) float f2, @Local(ordinal = 2) boolean flag2, @Local CriticalHitEvent hitEvent) {
        if (!(pTarget instanceof IElementDamageRecipient)) ci.cancel();

        ItemStack stack = this.getItemInHand(InteractionHand.MAIN_HAND);
        ElementDamage damageType = ((IElementProvider) (Object) stack).tm$getElementDamage();

        if (damageType != null && damageType.getAttackAttribute() != null) {
            float e = (float) this.getAttributeValue(damageType.getAttackAttribute());
            e *= 0.2F + f2 * f2 * 0.8F; // Scale with attack system

            if (flag2){
                e *= hitEvent.getDamageModifier();
            }

            ((IElementDamageRecipient)(Object)pTarget).outerworld$hurtWithElement(
                    this.damageSources().source(damageType.getDamageResource()), e, damageType);
        }
    }
}
