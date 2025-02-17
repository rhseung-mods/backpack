package com.rhseung.backpack.backpack.tooltip

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.network.payload.BackpackItemSelectedC2SPayload
import com.rhseung.backpack.backpack.screen.BackpackScreenHandler
import com.rhseung.backpack.init.ModTags
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.tooltip.TooltipSubmenuHandler
import net.minecraft.client.input.Scroller
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import net.minecraft.screen.slot.SlotActionType

class BackpackTooltipSubmenuHandler(val client: MinecraftClient) : TooltipSubmenuHandler {
    val scroller = Scroller();

    override fun isApplicableTo(slot: Slot): Boolean {
        val stack = slot.stack;
        val screenHandler = client.player?.currentScreenHandler;

        return if (!stack.isIn(ModTags.BACKPACK))
            false;
        else if (screenHandler is BackpackScreenHandler && BackpackItem.areEqual(screenHandler.backpackStack, stack))
            false;
        else
            true;
    }

    override fun onScroll(
        horizontal: Double,
        vertical: Double,
        slotId: Int,
        backpackStack: ItemStack
    ): Boolean {
        val item = backpackStack.item;
        if (item !is BackpackItem)
            return false;

        val screenHandler = client.player?.currentScreenHandler;
        val slotIndex = screenHandler?.slots?.indexOfFirst { ItemStack.areEqual(it.stack, backpackStack) } ?: error("impossible case");

        val vector2i = this.scroller.update(horizontal, vertical);
        val scrollAmount = if (vector2i.y == 0) -vector2i.x else vector2i.y;

        if (scrollAmount != 0) {
            val size = BackpackItem.getInventory(backpackStack).notEmptyStacks.size;
            if (size == 0)
                return false;

            val selectedItemIndex = BackpackItem.getSelectedStackIndex(backpackStack);
            val scrollCyclingAmount = Scroller.scrollCycling(scrollAmount.toDouble(), selectedItemIndex, size);

            if (scrollCyclingAmount != selectedItemIndex)
                this.sendPacket(backpackStack, slotIndex, scrollCyclingAmount);
        }

        return true;
    }

    override fun reset(slot: Slot) {
        this.sendPacket(slot.stack, slot.index, -1);
    }

    override fun onMouseClick(slot: Slot, actionType: SlotActionType) {
        if (actionType == SlotActionType.QUICK_MOVE || actionType == SlotActionType.SWAP)
            this.reset(slot);
    }

    fun sendPacket(backpackStack: ItemStack, slot: Int, selectedItemIndex: Int) {
        val item = backpackStack.item;
        if (item !is BackpackItem)
            return;

        if (selectedItemIndex in 0..<item.type.size || selectedItemIndex == -1) {
            BackpackItem.setSelectedStackIndex(backpackStack, selectedItemIndex);
            ClientPlayNetworking.send(BackpackItemSelectedC2SPayload(slot, selectedItemIndex));
        }
    }
}