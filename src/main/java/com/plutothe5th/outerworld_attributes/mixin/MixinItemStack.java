package com.plutothe5th.outerworld_attributes.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.plutothe5th.outerworld_attributes.api.ElementDamage;
import com.plutothe5th.outerworld_attributes.api.item.IElementProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import javax.annotation.Nullable;

@Mixin(ItemStack.class)
public abstract class MixinItemStack implements IElementProvider {

    @Shadow public abstract Item getItem();
    @Shadow @Nullable public abstract CompoundTag getTag();

    @ModifyReturnValue(method = "getHoverName", at = @At(value = "RETURN", remap = false))
    private Component onGetHoverName(Component original){
        ElementDamage damageType = this.tm$getElementDamage();
        if (damageType != null && !damageType.isImplied()){
            return (Component.empty()
                    .append(Component.translatable("outerworld.element.flourish." + damageType.getKey()).withStyle(damageType.getColor()))
                    .append(CommonComponents.space()).append(original));
        }
        return original;
    }

    @ModifyArg(method = "getTooltipLines",
            at = @At(value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 8
            ),
            remap = false
    )
    private Object onGetTooltipLines(Object original){
        if(original instanceof Component){
            ElementDamage damageType = this.tm$getElementDamage();
            if (damageType != null){
                return CommonComponents.space()
                        .append(Component.literal(damageType.getIconLiteral()))
                        .append(" +")
                        .append((int)damageType.getDamage() + " ")
                        .append(Component.translatable("outerworld.element." + damageType.getKey())).append(CommonComponents.space())
                        .append(Component.translatable("outerworld.damage"))
                        .withStyle(damageType.getColor());
            }
        }

        return original;
    }

    @ModifyReturnValue(method = "getAttributeModifiers", at = @At(value = "RETURN"), remap = false)
    private Multimap<Attribute, AttributeModifier> onGetAttributeModifiers(Multimap<Attribute, AttributeModifier> original, EquipmentSlot pSlot){
        ElementDamage damageType = this.tm$getElementDamage();
        if(damageType != null && pSlot == EquipmentSlot.MAINHAND){
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(damageType.getAttackAttribute(), damageType.getModifier());
            builder.putAll(original);

            return builder.build();
        }

        return original;
    }

    @Override
    public @Nullable ElementDamage tm$getElementDamage() {
        CompoundTag tag = this.getTag();
        if(tag != null && tag.contains(ElementDamage.TAG_KEY)){
            return ElementDamage.getElement(tag);
        }

        return null;
    }
}
