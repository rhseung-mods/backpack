package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.init.ModSounds
import com.rhseung.backpack.backpack.network.BackpackScreenS2CPayload
import com.rhseung.backpack.backpack.screen.BackpackScreenHandler
import com.rhseung.backpack.backpack.tooltip.BackpackTooltipComponent
import com.rhseung.backpack.backpack.storage.BackpackInventory
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
import net.minecraft.inventory.StackReference
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipData
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.ClickType
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.math.ColorHelper
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import java.util.Optional

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
            .build()
    )
) {
    companion object {
        // TODO: predicate 추가하기
        val PREDICATE_OPEN: Identifier = ModMain.id("open");
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

            player.openHandledScreen(object : ExtendedScreenHandlerFactory<BackpackScreenS2CPayload> {
                override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler {
                    return BackpackScreenHandler(syncId, playerInventory, backpack);
                }

                override fun getDisplayName(): Text {
                    return Text.translatable(backpack.item.translationKey);
                }

                override fun getScreenOpeningData(player: ServerPlayerEntity): BackpackScreenS2CPayload {
                    return BackpackScreenS2CPayload(backpack);
                }
            });
        }

        fun playOpenSound(player: ClientPlayerEntity) {
            player.playSound(ModSounds.OPEN_BACKPACK.value(), 0.8f, 0.8f + player.world.random.nextFloat() * 0.4f);
        }

        fun playCloseSound(player: ClientPlayerEntity, backpack: ItemStack) {
            player.playSound(ModSounds.CLOSE_BACKPACK.value(), 0.8f, 0.8f + player.world.random.nextFloat() * 0.4f);
        }

        fun playInsertSound(player: PlayerEntity) {
            player.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8f, 0.8f + player.world.random.nextFloat() * 0.4f);
        }

        fun playInsertFailSound(player: PlayerEntity) {
            player.playSound(SoundEvents.ITEM_BUNDLE_INSERT_FAIL, 1f, 1f);
        }

        fun playTakeSound(player: PlayerEntity) {
            player.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8f, 0.8f + player.world.random.nextFloat() * 0.4f);
        }

        fun getInventory(stack: ItemStack): BackpackInventory {
            return BackpackInventory(stack);
        }

        fun getColor(stack: ItemStack): Int {
            return DyedColorComponent.getColor(stack, COLOR_DEFAULT);
        }

        fun getSelectedStackIndex(stack: ItemStack): Int {
            return getSelectedStackIndex(stack, getInventory(stack));
        }

        fun getSelectedStackIndex(stack: ItemStack, inventory: BackpackInventory): Int {
            return inventory.selectedStackIndex;
        }

        fun getSelectedStackIndexWithEmpty(inventory: BackpackInventory): Int {
            return getSelectedStackIndexWithEmpty(inventory.selectedStackIndex, inventory);
        }

        fun getSelectedStackIndexWithEmpty(selectedStackIndex: Int, inventory: BackpackInventory): Int {
            var countNotEmptyStack = 0;
            var notEmptySelectedStackIndex = -1;

            for (i in 0..<inventory.heldStacks.size) {
                val stack = inventory.heldStacks[i];

                if (!stack.isEmpty) {
                    if (selectedStackIndex == countNotEmptyStack) {
                        notEmptySelectedStackIndex = i;
                        break;
                    }

                    countNotEmptyStack++;
                }
            }

            return notEmptySelectedStackIndex;
        }

        fun setSelectedStackIndex(stack: ItemStack, index: Int) {
            setSelectedStackIndex(stack, index, getInventory(stack));
        }

        fun setSelectedStackIndex(stack: ItemStack, index: Int, inventory: BackpackInventory) {
            inventory.update(index);
        }

        fun getSelectedStack(stack: ItemStack): ItemStack {
            return getSelectedStack(stack, getInventory(stack));
        }

        fun getSelectedStack(stack: ItemStack, inventory: BackpackInventory): ItemStack {
            return inventory.getStack(getSelectedStackIndexWithEmpty(inventory.selectedStackIndex, inventory));
        }

        fun hasSelectedStack(stack: ItemStack): Boolean {
            return hasSelectedStack(stack, getInventory(stack));
        }

        fun hasSelectedStack(stack: ItemStack, inventory: BackpackInventory): Boolean {
            return getSelectedStackIndex(stack, inventory) != -1;
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

    fun onContentChanged(player: PlayerEntity) {
        player.currentScreenHandler?.onContentChanged(player.inventory);
    }

    /**
     * 가방을 클릭했을 때
     */
    override fun onClicked(
        stack: ItemStack,
        otherStack: ItemStack,
        slot: Slot,
        clickType: ClickType,
        player: PlayerEntity,
        cursorStackReference: StackReference
    ): Boolean {
        val inventory = getInventory(stack);

        // 좌클릭 + 다른 아이템을 들고 있음 -> 가방에 아이템 추가
        if (clickType == ClickType.LEFT && otherStack.isEmpty.not()) {
            if (slot.canTakePartial(player) && inventory.isFull.not()) {
                playInsertSound(player);
                inventory.addStack(otherStack);
            } else {
                playInsertFailSound(player);
            }

            this.onContentChanged(player);
            return true;
        }
        // 우클릭 + 아무것도 안 들고 있음 -> 가방에서 아이템 빼내기
        else if (clickType == ClickType.RIGHT && otherStack.isEmpty) {
            if (slot.canTakePartial(player)) {
                val removed = if (hasSelectedStack(stack, inventory))
                    inventory.removeStack(getSelectedStackIndexWithEmpty(inventory))
                else
                    inventory.removeFirstStack();

                if (removed.isEmpty.not()) {
                    playTakeSound(player);
                    cursorStackReference.set(removed);
                }
            }

            this.onContentChanged(player);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 가방을 들고 있는 상태에서 다른 슬롯을 클릭했을 때
     */
    override fun onStackClicked(
        stack: ItemStack,
        slot: Slot,
        clickType: ClickType,
        player: PlayerEntity
    ): Boolean {
        val inventory = getInventory(stack);
        val slotStack = slot.stack;

        // 가방 들고 있는 상태에서 다른 슬롯 (아이템이 이미 있음) 좌클릭 -> 가방에 아이템 넣음
        if (clickType == ClickType.LEFT && slotStack.isEmpty.not()) {
            if (inventory.isFull.not()) {
                playInsertSound(player);
                inventory.addStack(slotStack);
            } else {
                playInsertFailSound(player);
            }

            this.onContentChanged(player);
            return true;
        }
        // 가방 들고 있는 상태에서 다른 슬롯 (비어 있음) 우클릭 -> 가방에서 해당 슬롯에 아이템을 뺌
        else if (clickType == ClickType.RIGHT && slotStack.isEmpty) {
            val removed = if (hasSelectedStack(stack, inventory))
                inventory.removeStack(getSelectedStackIndexWithEmpty(inventory))
            else
                inventory.removeFirstStack();

            if (removed.isEmpty.not()) {
                val remain = slot.insertStack(removed); // 슬롯에 64개 이상 들어가면 나머지를 반환함

                if (remain.count > 0)
                    inventory.addStack(remain);
                else
                    playTakeSound(player);
            }

            this.onContentChanged(player);
            return true;
        }
        else {
            return false;
        }
    }

    override fun getTooltipData(stack: ItemStack): Optional<TooltipData> {
        return Optional.of(getInventory(stack));
    }
}