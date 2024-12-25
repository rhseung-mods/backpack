package com.rhseung.backpack.backpack

import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ContainerComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand

class BackpackInventory(val size: BackpackSize, val player: PlayerEntity) : SimpleInventory(size.toInt()) {
    val backpack: ItemStack = player.getStackInHand(Hand.MAIN_HAND);
    val containerComponent: ContainerComponent = backpack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);

    init {
        containerComponent.copyTo(this.heldStacks);
    }

    fun update() {
        this.backpack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.heldStacks));
    }

    override fun removeStack(slot: Int): ItemStack {
        val ret = super.removeStack(slot);
        this.update();

        return ret;
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val ret = super.removeStack(slot, amount);
        this.update();

        return ret;
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        super.setStack(slot, stack);
        this.update();
    }

    override fun clear() {
        super.clear();
        this.update();
    }
}