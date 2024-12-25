package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.init.ModItems
import com.rhseung.backpack.init.ModSounds
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.item.ModelPredicateProviderRegistry
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.world.World

class BackpackItem(val size: BackpackSize, settings: Settings) : Item(settings.maxCount(1)) {
    companion object {
        val OPEN_PREDICATE: Identifier = ModMain.of("open");
    }

    fun onCloseScreen(player: ClientPlayerEntity, backpack: ItemStack) {
        player.world.playSound(player, player.blockPos, ModSounds.CLOSE_BACKPACK, SoundCategory.PLAYERS, 1f, 1f);
    }

    fun onOpenScreen(player: ClientPlayerEntity, backpack: ItemStack) {
        player.world.playSound(player, player.blockPos, ModSounds.OPEN_BACKPACK, SoundCategory.PLAYERS, 1f, 1f);
    }

    /**
     * @see <a href="https://wiki.fabricmc.net/tutorial:extendedscreenhandler">
     */
    override fun use(world: World, user: PlayerEntity, hand: Hand): ActionResult {
        if (world.isClient) {
            onOpenScreen(user as ClientPlayerEntity, user.getStackInHand(hand));
            return ActionResult.CONSUME;
        }
        else {
            user.setCurrentHand(hand);

            user.openHandledScreen(object : NamedScreenHandlerFactory {
                override fun createMenu(
                    syncId: Int,
                    playerInventory: PlayerInventory,
                    player: PlayerEntity
                ): ScreenHandler {
                    return BackpackScreenHandler(size, syncId, playerInventory);
                }

                override fun getDisplayName(): Text {
                    return Text.translatable("container.backpack");
                }
            });

            return ActionResult.SUCCESS_SERVER;
        }
    }
}