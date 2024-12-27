package com.rhseung.backpack.backpack

import com.rhseung.backpack.init.ModScreenHandlerTypesClient
import com.rhseung.backpack.network.BackpackScreenPayload
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler

class BackpackScreenHandler(
    syncId: Int,
    val playerInventory: PlayerInventory,
    val backpackInventory: BackpackInventory,
    val backpackStack: ItemStack
) : ScreenHandler(ModScreenHandlerTypesClient.BACKPACK_SCREEN_HANDLER, syncId) {

    val size = (this.backpackStack.item as BackpackItem).size;

    constructor(syncId: Int, playerInventory: PlayerInventory, backpackStack: ItemStack) :
        this(syncId, playerInventory, BackpackInventory(backpackStack), backpackStack);

    constructor(syncId: Int, playerInventory: PlayerInventory, backpackScreenPayload: BackpackScreenPayload) :
        this(syncId, playerInventory, backpackScreenPayload.stack);

    init {
        val backpack = this.backpackStack.item;

        if (backpack !is BackpackItem) {
            this.onClosed(playerInventory.player);
        }
        else {
            val size = backpack.size;

            this.backpackInventory.onOpen(playerInventory.player);
            this.addInventorySlots(this.backpackInventory, size, 8, 18);
            this.addPlayerSlots(this.playerInventory, 8, 18 + size.row * 18 + 13);
            this.backpackInventory.update();
        }
    }

    fun addInventorySlots(inventory: BackpackInventory, size: BackpackSize, left: Int, top: Int) {
        for (i in 0..<size.row) {
            for (j in 0..<9) {
                this.addSlot(BackpackSlot(backpackStack, inventory, j + i * 9, left + j * 18, top + i * 18));
            }
        }
    }

    override fun addPlayerHotbarSlots(playerInventory: Inventory, left: Int, y: Int) {
        for (i in 0..<9) {
            this.addSlot(BackpackSlot(backpackStack, playerInventory, i, left + i * 18, y));
        }
    }

    override fun addPlayerInventorySlots(playerInventory: Inventory, left: Int, top: Int) {
        for (i in 0..<3) {
            for (j in 0..<9) {
                this.addSlot(BackpackSlot(backpackStack, playerInventory, j + (i + 1) * 9, left + j * 18, top + i * 18));
            }
        }
    }

    override fun quickMove(player: PlayerEntity, slotIndex: Int): ItemStack {
        val slot = this.slots[slotIndex];

        if (slot.hasStack()) {
            val stack = slot.stack;
            val size = this.size;

            if (slotIndex < size.row * 9) {
                if (!this.insertItem(stack, size.row * 9, this.slots.size, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.insertItem(stack, 0, size.row * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty)
                slot.stack = ItemStack.EMPTY;
            else
                slot.markDirty();

            return stack;
        }
        else
            return ItemStack.EMPTY;
    }

    override fun canUse(player: PlayerEntity): Boolean {
        return this.backpackInventory.canPlayerUse(player);
    };

}