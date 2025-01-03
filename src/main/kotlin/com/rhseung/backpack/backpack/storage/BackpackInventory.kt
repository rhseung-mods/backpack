package com.rhseung.backpack.backpack.storage

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.init.ModComponents
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ContainerComponent
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipData
import org.apache.commons.lang3.math.Fraction

class BackpackInventory(
    val backpackStack: ItemStack
) : SimpleInventory((backpackStack.item as BackpackItem).type.size), TooltipData {

    val backpackItem = backpackStack.item as BackpackItem;
    val backpackContentsComponent: BackpackContentsComponent =
        backpackStack.getOrDefault(ModComponents.BACKPACK_CONTENTS, BackpackContentsComponent.DEFAULT);
    val selectedStackIndex: Int
        get() = this.backpackContentsComponent.selectedStackIndex;

    val maxSize: Int
        get() = this.heldStacks.size;

    val occupancy: Fraction
        get() {
            var total = Fraction.ZERO;
            for (stack in this.heldStacks) {
                total = total.add(
                    Fraction
                        .getFraction(stack.count, stack.maxCount)
                );
            }

            return total.divideBy(Fraction.getFraction(this.maxSize, 1));
        }

    val isFull: Boolean
        get() = this.occupancy >= Fraction.ONE;

    val notEmptyStacks: List<ItemStack>
        get() = this.heldStacks.filterNot { it.isEmpty };

    init {
        backpackContentsComponent.containerComponent.copyTo(this.heldStacks);
    }

    fun update(selectedStackIndex: Int = -1) {
        this.backpackStack.set(
            ModComponents.BACKPACK_CONTENTS,
            BackpackContentsComponent(ContainerComponent.fromStacks(this.heldStacks), selectedStackIndex)
        );
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

    override fun addStack(stack: ItemStack): ItemStack {
        val ret = super.addStack(stack);
        stack.count = ret.count;
        this.update();

        return ret;
    }

    fun removeFirstStack(): ItemStack {
        val index = this.heldStacks.indexOfFirst { it.isEmpty.not() };
        if (index == -1) {
            return ItemStack.EMPTY;
        }

        return this.removeStack(index);
    }

    override fun clear() {
        super.clear();
        this.update();
    }
}