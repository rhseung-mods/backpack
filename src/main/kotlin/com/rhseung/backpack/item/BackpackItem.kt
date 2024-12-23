package com.rhseung.backpack.item

import com.rhseung.backpack.init.ModSounds
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World

class BackpackItem(val size: BackpackSize, settings: Settings) : Item(settings.maxCount(1)) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): ActionResult {
        user.setCurrentHand(hand);

        if (world.isClient)
            world.playSound(user, user.blockPos, ModSounds.OPEN_BACKPACK, SoundCategory.PLAYERS, 1f, 1f);

        openScreen(size, user, user.getStackInHand(hand));

        return ActionResult.SUCCESS.withNewHandStack(user.getStackInHand(hand));
    }

    companion object {
        fun openScreen(size: BackpackSize, player: PlayerEntity, backpack: ItemStack) {
            player.openHandledScreen(object : NamedScreenHandlerFactory {
                override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler {
                    val inventory = BackpackContainer(size, backpack);
                    return BackpackScreenHandler(size.toScreenHandlerType(), syncId, playerInventory, inventory, size.row);
                }

                override fun getDisplayName(): Text {
                    return Text.of("Backpack");
                }
            });
        }
    }
}