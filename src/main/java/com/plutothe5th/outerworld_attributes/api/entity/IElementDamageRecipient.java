package com.plutothe5th.outerworld_attributes.api.entity;

import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;

public interface IElementDamageRecipient {
    void outerworld$hurtWithElement(DamageSource pSource, float pAmount, ElementDamage pElement);

    boolean outerworld$hasElementWeakness(ElementDamage element);
    boolean outerworld$hasElementResistance(ElementDamage element);

    static float getDamageAfterElementAbsorb(float pResist, float pDamage){
        float resist = Mth.clamp(pResist - (pDamage / 4.0f), pResist * 0.2f, 20.0f);
        return pDamage * (1.0f - (resist / 25.0f));
    }

    static float getDamageAfterEnchantAbsorb(float pResist, float pDamage){
        float resist = Mth.clamp(pResist, 0.0f, 20.0f);
        return pDamage * (1.0f - (resist / 25.0f));
    }
}