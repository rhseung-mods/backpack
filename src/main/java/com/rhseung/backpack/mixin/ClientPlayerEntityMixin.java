package com.rhseung.backpack.mixin;

import com.rhseung.backpack.backpack.BackpackItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(
        method = "closeHandledScreen",
        at = @At("HEAD")
    )
    public void closeHandledScreenMixin(CallbackInfo ci) {
        var player = (ClientPlayerEntity) (Object) this;
        var handStack = player.getStackInHand(Hand.MAIN_HAND);

        if (handStack.getItem() instanceof BackpackItem && player.currentScreenHandler instanceof GenericContainerScreenHandler) {
            BackpackItem.Companion.onCloseScreen(player, handStack);
        }
    }
}