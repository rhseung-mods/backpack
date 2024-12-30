package com.rhseung.backpack.backpack

import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.item.tooltip.TooltipData

class BackpackTooltipComponent(val inventory: BackpackInventory) : TooltipComponent {
    class BackpackTooltipData(val inventory: BackpackInventory) : TooltipData;

    override fun getHeight(textRenderer: TextRenderer): Int {
        TODO("Not yet implemented")
    }

    override fun getWidth(textRenderer: TextRenderer): Int {
        TODO("Not yet implemented")
    }

    override fun drawItems(
        textRenderer: TextRenderer,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        context: DrawContext
    ) {
        super.drawItems(textRenderer, x, y, width, height, context)
    }
}