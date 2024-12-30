package com.rhseung.backpack.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.rhseung.backpack.backpack.BackpackTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.TooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin {
    @ModifyReturnValue(
        method = "of(Lnet/minecraft/item/tooltip/TooltipData;)Lnet/minecraft/client/gui/tooltip/TooltipComponent;",
        at = @At("RETURN")
    )
    private static TooltipComponent ofMixin(TooltipComponent original, @Local(argsOnly = true) TooltipData data) {
        return original;

//        if (data instanceof BackpackTooltipComponent.BackpackTooltipData backpackTooltipData)
//            return new BackpackTooltipComponent(backpackTooltipData.getInventory());
//        else
//            return original;
    }
}
