package com.rhseung.backpack.backpack

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot
import net.minecraft.util.Hand

class BackpackSlot(
    inventory: Inventory,
    index: Int,
    x: Int,
    y: Int,
) : Slot(inventory, index, x, y) {

    override fun canTakeItems(playerEntity: PlayerEntity): Boolean {
        // TODO: 셜커 상자처럼 다른 가방도 못 들어가게 할까?
        return !ItemStack.areEqual(inventory.getStack(index), playerEntity.getStackInHand(Hand.MAIN_HAND));
    }
}