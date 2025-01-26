package com.rhseung.backpack.backpack.tooltip

import com.rhseung.backpack.backpack.BackpackItem
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipData

class BackpackTooltipData(val backpackStack: ItemStack) : TooltipData {
    init {
        require(backpackStack.item is BackpackItem) { "backpackStack.item is not a BackpackItem" }
    }
}