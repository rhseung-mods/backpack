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

    val backpackType = (this.backpackStack.item as BackpackItem).type;

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
            val type = backpack.type;

            this.backpackInventory.onOpen(playerInventory.player);
            this.addBackpackSlots(this.backpackInventory, type,
                type.slotStartX + 2 * type.borderThickness,
                type.slotStartY + 2 * type.borderThickness
            );
            this.addPlayerSlots(this.playerInventory,
                8,
                type.backpackHeight + 14
            );
            this.backpackInventory.update();
        }
    }

    fun addBackpackSlots(backpackInventory: BackpackInventory, type: BackpackType, left: Int, top: Int) {
        for (i in 0..<type.row) {
            for (j in 0..<(type.size - i * 9).coerceAtMost(9)) {
                this.addSlot(BackpackSlot(backpackStack, backpackInventory, j + i * 9, left + j * 18, top + i * 18));
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
            val type = this.backpackType;
            val size = type.size;

            if (slotIndex < size) {
                if (!this.insertItem(stack, size, this.slots.size, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.insertItem(stack, 0, size, false)) {
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

    override fun onClosed(player: PlayerEntity) {
        super.onClosed(player);
        this.backpackInventory.onClose(player);
    }
}