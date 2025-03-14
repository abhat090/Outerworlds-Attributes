package com.plutothe5th.outerworld_attributes.api.item;

import com.plutothe5th.outerworld_attributes.api.ElementDamage;

import javax.annotation.Nullable;

public interface IElementProvider {
    @Nullable
    ElementDamage tm$getElementDamage();
}
