package com.rhseung.backpack.backpack

import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ContainerComponent
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack

class BackpackContainer(val size: BackpackSize, val backpack: ItemStack) : SimpleInventory(size.toInt()) {
    val containerComponent: ContainerComponent = backpack
        .getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);

    init {
        containerComponent.copyTo(this.heldStacks);
    }

    override fun removeStack(slot: Int): ItemStack {
        val ret = super.removeStack(slot);
        this.backpack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.heldStacks));
        return ret;
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val ret = super.removeStack(slot, amount);
        this.backpack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.heldStacks));
        return ret;
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        super.setStack(slot, stack);
        this.backpack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.heldStacks));
    }

    override fun clear() {
        super.clear();
        this.backpack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.heldStacks));
    }
}