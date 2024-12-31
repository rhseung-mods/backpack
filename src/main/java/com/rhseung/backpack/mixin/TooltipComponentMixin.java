package com.rhseung.backpack.mixin;

import com.rhseung.backpack.backpack.storage.BackpackContentsComponent;
import com.rhseung.backpack.backpack.storage.BackpackInventory;
import com.rhseung.backpack.backpack.tooltip.BackpackTooltipComponent;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.client.gui.tooltip.ProfilesTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.BundleTooltipData;
import net.minecraft.item.tooltip.TooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin {
    @Inject(
        method = "of(Lnet/minecraft/item/tooltip/TooltipData;)Lnet/minecraft/client/gui/tooltip/TooltipComponent;",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void ofMixin(TooltipData tooltipData, CallbackInfoReturnable<TooltipComponent> cir) {
        switch (tooltipData) {
            case BundleTooltipData d -> cir.setReturnValue(new BundleTooltipComponent(d.contents()));
            case ProfilesTooltipComponent.ProfilesData d -> cir.setReturnValue(new ProfilesTooltipComponent(d));
            case BackpackInventory d -> cir.setReturnValue(new BackpackTooltipComponent(d));
            default -> throw new IllegalArgumentException("Unknown TooltipComponent");
        }
    }
}
