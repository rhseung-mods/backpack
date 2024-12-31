package com.rhseung.backpack.backpack.tooltip

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.network.BackpackItemSelectedC2SPayload
import com.rhseung.backpack.init.ModTags
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.gui.tooltip.TooltipSubmenuHandler
import net.minecraft.client.input.Scroller
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import net.minecraft.screen.slot.SlotActionType

class BackpackTooltipSubmenuHandler : TooltipSubmenuHandler {
    val scroller = Scroller();

    override fun isApplicableTo(slot: Slot): Boolean {
        return slot.stack.isIn(ModTags.BACKPACK);
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

        val vector2i = this.scroller.update(horizontal, vertical);
        val scrollAmount = if (vector2i.y == 0) -vector2i.x else vector2i.y;

        if (scrollAmount != 0) {
            val size = BackpackItem.getInventory(backpackStack).notEmptyStacks.size;
            val selectedItemIndex = BackpackItem.getSelectedStackIndex(backpackStack);
            val scrollCyclingAmount = Scroller.scrollCycling(scrollAmount.toDouble(), selectedItemIndex, size);

            if (scrollCyclingAmount != selectedItemIndex)
                this.sendPacket(backpackStack, slotId, scrollCyclingAmount);
        }

        return true;
    }

    override fun reset(slot: Slot) {
        this.sendPacket(slot.stack, slot.id, -1);
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