package com.rhseung.backpack.mixin;

import com.rhseung.backpack.backpack.tooltip.BackpackTooltipSubmenuHandler;
import com.rhseung.backpack.util.Utils;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.TooltipSubmenuHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin {
    @Shadow protected abstract void addTooltipSubmenuHandler(TooltipSubmenuHandler handler);

    @Inject(
        method = "init",
        at = @At("RETURN")
    )
    protected void initMixin(CallbackInfo ci) {
        var that = (HandledScreen<?>) (Object) this;
        this.addTooltipSubmenuHandler(new BackpackTooltipSubmenuHandler(Utils.INSTANCE.get(that, "client")));
    }
}
