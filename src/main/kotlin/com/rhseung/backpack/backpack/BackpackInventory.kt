package com.rhseung.backpack.backpack

import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ContainerComponent
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.util.Hand

class BackpackInventory(
    size: BackpackSize,
    player: PlayerEntity,
    backpackData: BackpackData
) : SimpleInventory(size.toInt()) {

    // TODO: trinket slot도 고려할 때는 값에 -2도 넣고 그래야 할듯
    val backpack: ItemStack = backpackData.getStack(player);
    val containerComponent: ContainerComponent = backpack
        .getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);

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