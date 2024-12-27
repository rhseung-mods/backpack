package com.rhseung.backpack.backpack

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.slot.Slot

class BackpackSlot(
    val backpackStack: ItemStack,
    val inventory: Inventory,
    index: Int,
    x: Int,
    y: Int,
) : Slot(inventory, index, x, y) {

    fun cannotMove(stack: ItemStack): Boolean {
        return stack.item is BackpackItem && ItemStack.areEqual(stack, backpackStack);
    }

    override fun canTakeItems(playerEntity: PlayerEntity): Boolean {
        return !cannotMove(this.stack);
    }

    // 왼손 스왑(f) 가방 여는 중에 금지
    override fun canInsert(stack: ItemStack): Boolean {
        return !cannotMove(stack);
    }
}