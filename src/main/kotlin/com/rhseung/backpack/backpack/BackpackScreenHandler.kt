package com.rhseung.backpack.backpack

import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.screen.GenericContainerScreenHandler

class BackpackScreenHandler(
    val size: BackpackSize,
    syncId: Int,
    playerInventory: PlayerInventory,
    val inventory: BackpackInventory,
) : GenericContainerScreenHandler(size.toScreenHandlerType(), syncId, playerInventory, inventory, size.row) {

    constructor(size: BackpackSize, syncId: Int, playerInventory: PlayerInventory) :
        this(size, syncId, playerInventory, BackpackInventory(size, playerInventory.player))

    companion object {
        fun create9x1(syncId: Int, playerInventory: PlayerInventory) =
            BackpackScreenHandler(BackpackSize.`9X1`, syncId, playerInventory);

        fun create9x2(syncId: Int, playerInventory: PlayerInventory) =
            BackpackScreenHandler(BackpackSize.`9X2`, syncId, playerInventory);

        fun create9x3(syncId: Int, playerInventory: PlayerInventory) =
            BackpackScreenHandler(BackpackSize.`9X3`, syncId, playerInventory);

        fun create9x4(syncId: Int, playerInventory: PlayerInventory) =
            BackpackScreenHandler(BackpackSize.`9X4`, syncId, playerInventory);

        fun create9x5(syncId: Int, playerInventory: PlayerInventory) =
            BackpackScreenHandler(BackpackSize.`9X5`, syncId, playerInventory);

        fun create9x6(syncId: Int, playerInventory: PlayerInventory) =
            BackpackScreenHandler(BackpackSize.`9X6`, syncId, playerInventory);
    }

    override fun addPlayerHotbarSlots(playerInventory: Inventory, left: Int, y: Int) {
        for (i in 0..<9) {
            this.addSlot(BackpackSlot(playerInventory, i, left + i * 18, y));
        }
    }

    override fun addPlayerInventorySlots(playerInventory: Inventory, left: Int, top: Int) {
        for (i in 0..<3) {
            for (j in 0..<9) {
                this.addSlot(BackpackSlot(playerInventory, j + (i + 1) * 9, left + j * 18, top + i * 18));
            }
        }
    }
}