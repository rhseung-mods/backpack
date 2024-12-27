package com.rhseung.backpack.mixin;

import com.rhseung.backpack.backpack.BackpackItem;
import com.rhseung.backpack.backpack.BackpackScreenHandler;
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

        if (player.currentScreenHandler instanceof BackpackScreenHandler backpackScreenHandler) {
            BackpackItem.Companion.playCloseSound(player, backpackScreenHandler.getBackpackStack());
        }
    }
}