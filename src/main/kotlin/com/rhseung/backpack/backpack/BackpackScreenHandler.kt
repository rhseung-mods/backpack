package com.rhseung.backpack.backpack

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.ScreenHandlerType

class BackpackScreenHandler(
    type: ScreenHandlerType<*>,
    syncId: Int,
    playerInventory: PlayerInventory,
    inventory: Inventory,
    rows: Int
) : GenericContainerScreenHandler(type, syncId, playerInventory, inventory, rows) {
}