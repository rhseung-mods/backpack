package com.rhseung.backpack.backpack

import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ContainerComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack

class BackpackInventory(
    val backpackStack: ItemStack
) : SimpleInventory((backpackStack.item as BackpackItem).size.toInt()) {

    val containerComponent: ContainerComponent = backpackStack
        .getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);

    init {
        containerComponent.copyTo(this.heldStacks);
    }

    fun update() {
        this.backpackStack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.heldStacks));
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