package com.rhseung.backpack.backpack

import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ContainerComponent
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import org.apache.commons.lang3.math.Fraction

class BackpackInventory(
    val backpackStack: ItemStack
) : SimpleInventory((backpackStack.item as BackpackItem).type.size) {

    val containerComponent: ContainerComponent = backpackStack
        .getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);

    val maxSize: Int
        get() = this.heldStacks.size;

    val occupancy: Fraction
        get() = Fraction.getReducedFraction(this.heldStacks.filterNot { it.isEmpty }.size, this.maxSize);

    val isFull: Boolean
        get() = this.occupancy >= Fraction.ONE;

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