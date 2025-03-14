package com.plutothe5th.outerworld_attributes.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import com.plutothe5th.outerworld_attributes.api.ElementDamageTypes;
import com.plutothe5th.outerworld_attributes.api.entity.IElementDamageRecipient;
import com.plutothe5th.outerworld_attributes.api.item.ElementHelper;
import com.plutothe5th.outerworld_attributes.config.CommonConfig;
import com.plutothe5th.outerworld_attributes.config.EntityWeaknesses;
import com.plutothe5th.outerworld_attributes.registries.ModAttributes;
import com.plutothe5th.outerworld_attributes.tags.ModTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity implements IElementDamageRecipient {

    @Shadow protected abstract void actuallyHurt(DamageSource pDamageSource, float pDamageAmount);

    @Shadow public abstract double getAttributeValue(Attribute pAttribute);

    @Shadow public abstract Iterable<ItemStack> getArmorSlots();

    @Shadow protected float lastHurt;


    @Shadow public abstract boolean isDeadOrDying();

    @Shadow protected abstract boolean checkTotemDeathProtection(DamageSource pDamageSource);

    @Shadow @Nullable protected abstract SoundEvent getDeathSound();

    @Shadow protected abstract float getSoundVolume();

    @Shadow public abstract float getVoicePitch();

    @Shadow public abstract void die(DamageSource pDamageSource);

    @Shadow protected abstract void playHurtSound(DamageSource pSource);

    @Shadow @Nullable private DamageSource lastDamageSource;

    @Shadow private long lastDamageStamp;

    @Shadow public abstract boolean hasEffect(MobEffect pEffect);

    @Inject(method = "<init>", at = @At(value = "TAIL"), remap = false)
    private void onInit(EntityType<? extends LivingEntity> pEntityType, Level pLevel, CallbackInfo ci){

    }

    @ModifyReturnValue(method = "createLivingAttributes", at = @At(value = "RETURN"), remap = false)
    private static AttributeSupplier.Builder onCreateAttributes(AttributeSupplier.Builder original) {
        return original
                .add((Attribute) ModAttributes.FIRE_DAMAGE.get())
                .add((Attribute) ModAttributes.WATER_DAMAGE.get())
                .add((Attribute) ModAttributes.NATURE_DAMAGE.get())
                .add((Attribute) ModAttributes.ENDER_DAMAGE.get())

                .add((Attribute) ModAttributes.FIRE_DEFENSE.get())
                .add((Attribute) ModAttributes.WATER_DEFENSE.get())
                .add((Attribute) ModAttributes.NATURE_DEFENSE.get())
                .add((Attribute) ModAttributes.ENDER_DEFENSE.get());
    }

    @Override
    public boolean outerworld$hasElementWeakness(ElementDamage element) {
        return EntityWeaknesses.isWeakTo((Entity)(Object)this, element);
    }

    @Override
    public boolean outerworld$hasElementResistance(ElementDamage element) {
        return EntityWeaknesses.isResistantTo((Entity)(Object)this, element);
    }

    @Override
    public void outerworld$hurtWithElement(DamageSource pSource, float pAmount, ElementDamage pElement) {
        if(this.isDeadOrDying()) return;

        if(pSource.is(ModTags.ElementalDamageType.IS_ELEMENTAL)){
            float r = 0.0f;
            if(pElement != ElementDamage.NULL){
                r = (float)this.getAttributeValue(pElement.getDefenseAttribute());
            }

            float damage = IElementDamageRecipient.getDamageAfterElementAbsorb(r, pAmount);

            int k = ElementHelper.getDamageProtection(this.getArmorSlots(), pSource);
            if(k > 0){
                damage = IElementDamageRecipient.getDamageAfterEnchantAbsorb((float) k, damage);
            }

            if(pElement == ElementDamage.FIRE && this.hasEffect(MobEffects.FIRE_RESISTANCE)){
                damage *= 0.5f;
            }

            boolean flag = false;
            if(damage > 0.0f){
                if(this.outerworld$hasElementWeakness(pElement)){
                    damage *= CommonConfig.ELEMENTAL_WEAKNESS_SCALE.get();
                }

                if(this.outerworld$hasElementResistance(pElement)){
                    damage *= CommonConfig.ELEMENTAL_RESISTANCE_SCALE.get();
                }

                this.lastHurt = damage;
                this.actuallyHurt(pSource, damage);

                flag = true;
            }

            if(flag){
                ((Entity)(Object)this).level().broadcastDamageEvent(((Entity)(Object)this), pSource);
            }

            if (this.isDeadOrDying()){
                if(!this.checkTotemDeathProtection(pSource)){
                    SoundEvent sound = this.getDeathSound();
                    if(flag && sound != null){
                        ((Entity)(Object)this).playSound(sound, this.getSoundVolume(), this.getVoicePitch());
                    }

                    this.die(pSource);
                }
            } else {
                this.playHurtSound(pSource);
            }

            boolean flag1 = damage > 0.0F;
            if (flag1) {
                this.lastDamageSource = pSource;
                this.lastDamageStamp = ((Entity)(Object)this).level().getGameTime();
            }

            if (((LivingEntity)(Object)this) instanceof ServerPlayer) {
                CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((ServerPlayer)(Object)this, pSource, pAmount, damage, false);
            }

            Entity entity = pSource.getEntity();
            if(entity instanceof ServerPlayer){
                CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayer)entity,(Entity)(Object)this, pSource, pAmount, damage, false);
            }
        }

    }
}
