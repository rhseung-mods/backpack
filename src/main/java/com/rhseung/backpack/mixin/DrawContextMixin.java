package com.rhseung.backpack.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.rhseung.backpack.backpack.screen.BackpackTooltipComponent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(DrawContext.class)
public class DrawContextMixin {
    @ModifyVariable(
        method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;Lnet/minecraft/util/Identifier;)V",
        at = @At("STORE"),
        ordinal = 3
    )
    public int modifyTooltipY(int y, @Local(argsOnly = true) List<TooltipComponent> components) {
        if (y <= 0)
            return components.size() == 1 || (components.size() == 2 && components.get(1) instanceof BackpackTooltipComponent) ? -2 : 0;
        else
            return y;
    }
}
