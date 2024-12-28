package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.init.ModSounds
import com.rhseung.backpack.network.BackpackScreenPayload
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.component.type.EquippableComponent
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.math.ColorHelper
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

class BackpackItem(
    val type: BackpackType,
    settings: Settings
) : Item(settings
    .maxCount(1)
    .component(
        DataComponentTypes.EQUIPPABLE,
        EquippableComponent
            .builder(EquipmentSlot.CHEST)
            .equipSound(ModSounds.EQUIP_BACKPACK)
            .build()    // TODO: model도 추가하기
    )
) {
    companion object {
        val PREDICATE_OPEN: Identifier = ModMain.of("open");
        const val KEY_OPEN = "key.${ModMain.MOD_ID}.open";
        const val KEY_CATEGORY = "key.category.${ModMain.MOD_ID}.backpack";
        val ITEM_BAR_COLOR = ColorHelper.fromFloats(1.0F, 0.44F, 0.53F, 1.0F);
        val ITEM_BAR_COLOR_FULL = ColorHelper.fromFloats(1.0F, 1.0F, 0.33F, 0.33F);
        val COLOR_DEFAULT = ColorHelper.fullAlpha(0x825939);

        @Environment(EnvType.CLIENT)
        fun onClient(item: BackpackItem) {
//            ModelPredicateProviderRegistry.register(item, BackpackItem.PREDICATE_OPEN) { stack, world, entity, seed ->
//                if (entity !is PlayerEntity)
//                    return@register 0f;
//                else if (entity.currentScreenHandler !is BackpackScreenHandler)
//                    return@register 0f;
//
//                val backpackScreenHandler = entity.currentScreenHandler as BackpackScreenHandler;
//                return@register ItemStack.areEqual(stack, backpackScreenHandler.backpackStack).toInt().toFloat();
//            }

            ColorProviderRegistry.ITEM.register(ItemColorProvider { stack, tintIndex ->
                if (tintIndex == 0) DyedColorComponent.getColor(stack, COLOR_DEFAULT) else -1
            }, item);
        }

        fun openScreen(player: ServerPlayerEntity, backpack: ItemStack) {
            require(backpack.item is BackpackItem) { "ItemStack($backpack) is not a backpack" };

            // TODO: f 키로 스왑한다던가 등 문제가 있어서 왼손으로 열리는 것을 막음. 나중에 수정할 것

            player.openHandledScreen(object : ExtendedScreenHandlerFactory<BackpackScreenPayload> {
                override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler {
                    return BackpackScreenHandler(syncId, playerInventory, backpack);
                }

                override fun getDisplayName(): Text {
                    return Text.translatable(backpack.item.translationKey);
                }

                override fun getScreenOpeningData(player: ServerPlayerEntity): BackpackScreenPayload {
                    return BackpackScreenPayload(backpack);
                }
            });
        }

        fun playCloseSound(player: ClientPlayerEntity, backpack: ItemStack) {
            player.world.playSound(player, player.blockPos, ModSounds.CLOSE_BACKPACK.value(), SoundCategory.PLAYERS, 1f, 1f);
        }

        fun playOpenSound(player: ClientPlayerEntity) {
            player.world.playSound(player, player.blockPos, ModSounds.OPEN_BACKPACK.value(), SoundCategory.PLAYERS, 1f, 1f);
        }
    }

    /**
     * @see <a href="https://wiki.fabricmc.net/tutorial:extendedscreenhandler">
     */
    override fun use(world: World, user: PlayerEntity, hand: Hand): ActionResult {
        val stack = user.getStackInHand(hand);
        user.setCurrentHand(hand);

        if (world.isClient) {
            BackpackItem.playOpenSound(user as ClientPlayerEntity);
            return ActionResult.CONSUME.withNewHandStack(stack);
        }
        else {
            BackpackItem.openScreen(user as ServerPlayerEntity, stack);
            return ActionResult.SUCCESS_SERVER.withNewHandStack(stack);
        }
    }

    fun getInventory(stack: ItemStack): BackpackInventory {
        return BackpackInventory(stack);
    }

    override fun isItemBarVisible(stack: ItemStack): Boolean {
        val inventory = getInventory(stack);
        return !inventory.isEmpty;
    }

    override fun getItemBarColor(stack: ItemStack): Int {
        val inventory = getInventory(stack);
        return if (inventory.isFull) ITEM_BAR_COLOR_FULL else ITEM_BAR_COLOR;
    }

    override fun getItemBarStep(stack: ItemStack): Int {
        val inventory = getInventory(stack);
        return (1 + MathHelper.multiplyFraction(inventory.occupancy, 12)).coerceAtMost(13);
    }
}