package com.rhseung.backpack.backpack.storage

import com.mojang.serialization.DataResult
import com.rhseung.backpack.init.ModComponents
import net.minecraft.component.type.ContainerComponent
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipData
import net.minecraft.network.codec.PacketCodecs
import org.apache.commons.lang3.math.Fraction

class BackpackInventory(
    stacks: List<ItemStack>,
    val selectedStackIndex: Int
) : SimpleInventory(stacks.size) {

    constructor(stacks: List<ItemStack>) : this(stacks, -1);

    init {
        stacks.forEachIndexed { index, stack ->
            this.setStack(index, stack);
        }
    }

    val maxSize: Int
        get() = this.heldStacks.size;

    val occupancy: Fraction
        get() {
            var total = Fraction.ZERO;
            this.heldStacks.forEach {
                total = total.add(Fraction.getFraction(it.count, it.maxCount));
            }
            return total.divideBy(Fraction.getFraction(this.maxSize, 1));
        }

    val isFull: Boolean
        get() = this.occupancy >= Fraction.ONE;

    val notEmptyStacks: List<ItemStack>
        get() = this.heldStacks.filterNot { it.isEmpty };

    fun update(backpackStack: ItemStack, selectedStackIndex: Int = -1) {
        backpackStack.set(
            ModComponents.BACKPACK_CONTENTS,
            BackpackInventory(this.heldStacks, selectedStackIndex)
        );
    }

//    override fun removeStack(slot: Int): ItemStack {
//        val ret = super.removeStack(slot);
//        this.update();
//
//        return ret;
//    }
//
//    override fun removeStack(slot: Int, amount: Int): ItemStack {
//        val ret = super.removeStack(slot, amount);
//        this.update();
//
//        return ret;
//    }
//
//    override fun setStack(slot: Int, stack: ItemStack) {
//        super.setStack(slot, stack);
//        this.update();
//    }
//
//    override fun addStack(stack: ItemStack): ItemStack {
//        val ret = super.addStack(stack);
//        stack.count = ret.count;
//        this.update();
//
//        return ret;
//    }

    fun removeFirstStack(): ItemStack {
        val index = this.heldStacks.indexOfFirst { it.isEmpty.not() };
        if (index == -1) {
            return ItemStack.EMPTY;
        }

        return this.removeStack(index);
    }

    override fun clear() {
        super.clear();
//        this.update();
    }

    companion object {
        val DEFAULT = { size: Int -> BackpackInventory(List(size) { ItemStack.EMPTY }) };
        val CODEC = ItemStack.OPTIONAL_CODEC.listOf().flatXmap(
            { stacks -> DataResult.success(BackpackInventory(stacks)) },
            { inventory -> DataResult.success(inventory.heldStacks) }
        );
        val PACKET_CODEC = ItemStack.OPTIONAL_LIST_PACKET_CODEC.xmap(::BackpackInventory, BackpackInventory::heldStacks);
    }
}