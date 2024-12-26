package com.rhseung.backpack.backpack

import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.util.Hand

class BackpackData(private var hotBarIndex: Int = -1, private val hand: Hand = Hand.MAIN_HAND) : PropertyDelegate {
    fun isHotBar(): Boolean {
        return this.hotBarIndex >= 0;
    }

    fun isChestEquipped(): Boolean {
        return this.hotBarIndex == -1;
    }

    // TODO: trinket은 -2
    fun getStack(player: PlayerEntity): ItemStack {
        return if (this.isHotBar())
            player.getStackInHand(hand);
        else if (this.isChestEquipped())
            player.getEquippedStack(EquipmentSlot.CHEST);
        else
            ItemStack.EMPTY;
    }

    fun get(): Int {
        return this.hotBarIndex;
    }

    fun set(value: Int) {
        this.hotBarIndex = value;
    }

    override fun get(index: Int): Int {
        return this.hotBarIndex;
    }

    override fun set(index: Int, value: Int) {
        this.hotBarIndex = value;
    }

    override fun size(): Int {
        return 1;
    }
}