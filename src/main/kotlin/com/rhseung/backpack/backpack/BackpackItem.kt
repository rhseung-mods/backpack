package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.init.ModSounds
import com.rhseung.backpack.util.Utils.toInt
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.item.ModelPredicateProviderRegistry
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.component.type.EquippableComponent
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.world.World

class BackpackItem(
    val size: BackpackSize,
    settings: Settings
) : Item(settings
    .maxCount(1)
    .component(
        DataComponentTypes.EQUIPPABLE,
        EquippableComponent
            .builder(EquipmentSlot.CHEST)
            .equipSound(ModSounds.OPEN_BACKPACK)
            .build()    // TODO: model도 추가하기
    )
) {
    companion object {
        val PREDICATE_OPEN: Identifier = ModMain.of("open");
        const val KEY_OPEN = "key.${ModMain.MOD_ID}.open";
        const val KEY_CATEGORY = "key.category.${ModMain.MOD_ID}.backpack";
        const val SCREEN_NAME = "container.backpack";

        @Environment(EnvType.CLIENT)
        fun onClient(item: BackpackItem) {
            ModelPredicateProviderRegistry.register(item, BackpackItem.PREDICATE_OPEN) { stack, world, entity, seed ->
                if (entity !is PlayerEntity)
                    return@register 0f;
                else if (stack.item !is BackpackItem)
                    return@register 0f;
                else if (entity.currentScreenHandler !is BackpackScreenHandler)
                    return@register 0f;

                val backpackScreenHandler = entity.currentScreenHandler as BackpackScreenHandler;
                val backpack = backpackScreenHandler.openedBy.getStack(entity);

                return@register ItemStack.areEqual(stack, backpack).toInt().toFloat();
            }

            ColorProviderRegistry.ITEM.register(ItemColorProvider { stack, tintIndex ->
                if (tintIndex == 1) DyedColorComponent.getColor(stack, 0) else -1
            }, item);
        }

        fun openScreen(world: ServerWorld, player: ServerPlayerEntity, backpack: ItemStack, index: Int, hand: Hand = Hand.MAIN_HAND) {
            require(backpack.item is BackpackItem) { "ItemStack($backpack) is not a backpack" };

            // TODO: f 키로 스왑한다던가 등 문제가 있어서 왼손으로 열리는 것을 막음. 나중에 수정할 것

            player.openHandledScreen(object : NamedScreenHandlerFactory {
                override fun createMenu(
                    syncId: Int,
                    playerInventory: PlayerInventory,
                    player: PlayerEntity
                ): ScreenHandler {
                    return BackpackScreenHandler(
                        (backpack.item as BackpackItem).size,
                        syncId,
                        playerInventory,
                        BackpackData(index, hand)
                    );
                }

                override fun getDisplayName(): Text {
                    return Text.translatable(SCREEN_NAME);
                }
            });
        }

        fun onCloseScreen(player: ClientPlayerEntity, backpack: ItemStack) {
            player.world.playSound(
                player,
                player.blockPos,
                ModSounds.CLOSE_BACKPACK.value(),
                SoundCategory.PLAYERS,
                1f,
                1f
            );
        }

        fun onOpenScreen(player: ClientPlayerEntity) {
            player.world.playSound(
                player,
                player.blockPos,
                ModSounds.OPEN_BACKPACK.value(),
                SoundCategory.PLAYERS,
                1f,
                1f
            );
        }
    }

    /**
     * @see <a href="https://wiki.fabricmc.net/tutorial:extendedscreenhandler">
     */
    override fun use(world: World, user: PlayerEntity, hand: Hand): ActionResult {
        val stack = user.getStackInHand(hand);

        if (world.isClient) {
            BackpackItem.onOpenScreen(user as ClientPlayerEntity);
            return ActionResult.CONSUME;
        }
        else {
            user.setCurrentHand(hand);
            BackpackItem.openScreen(
                world as ServerWorld,
                user as ServerPlayerEntity,
                stack,
                user.inventory.selectedSlot,
                hand
            );
            return ActionResult.SUCCESS_SERVER;
        }
    }
}