package com.rhseung.backpack.backpack

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.slot.Slot

class BackpackSlot(
    val handler: BackpackScreenHandler,
    inventory: Inventory,
    index: Int,
    x: Int,
    y: Int,
) : Slot(inventory, index, x, y) {

    override fun canTakeItems(playerEntity: PlayerEntity): Boolean {
        // TODO: 셜커 상자처럼 다른 가방도 못 들어가게 할까?
        return handler.openedBy.get() != index;
    }
}