package com.rhseung.backpack.backpack.screen

import com.rhseung.backpack.backpack.storage.BackpackInventory
import com.rhseung.backpack.backpack.BackpackItem
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.item.tooltip.TooltipData

class BackpackTooltipComponent(val inventory: BackpackInventory) : TooltipComponent {
    class BackpackTooltipData(val inventory: BackpackInventory) : TooltipData;

    val backpackItem = inventory.backpackStack.item as BackpackItem;
    val type = backpackItem.type;

    fun getHeight(): Int {
        return type.tooltipTopTexture.height + type.tooltipMiddleTexture.height + type.tooltipBottomTexture.height;
    }

    override fun getHeight(textRenderer: TextRenderer): Int {
        return 0;
    }

    override fun getWidth(textRenderer: TextRenderer): Int {
        return 0;
    }

    override fun drawItems(
        textRenderer: TextRenderer,
        x0: Int,
        y0: Int,
        width: Int,
        height: Int,
        context: DrawContext
    ) {
        val x0 = x0 - 4;
        val y0 = y0 - (getHeight() + 2) - 16;
        val color = BackpackItem.Companion.getColor(inventory.backpackStack);

        var y = y0;
        type.tooltipTopTexture.draw(context, x0, y, color);

        y += type.tooltipTopTexture.height;
        type.tooltipMiddleTexture.draw(context, x0, y, color);
        type.loop { i, j ->
            val x = x0 + 3 + type.pad + j * type.slotTexture.width;
            val y = y0 + 3 + type.pad + i * type.slotTexture.height;

            type.slotTexture.draw(
                context,
                x + type.borderThickness,
                y + type.borderThickness,
                color
            );
            type.slotBorderTexture.draw(context, x, y, color);
        }

        y += type.tooltipMiddleTexture.height;
        type.tooltipBottomTexture.draw(context, x0, y, color);

        type.loop { i, j ->
            val x = x0 + 3 + type.pad + j * type.slotTexture.width;
            val y = y0 + 3 + type.pad + i * type.slotTexture.height;
            val stack = inventory.getStack(j + i * 9);

            context.drawItem(stack, x + type.borderThickness * 2, y + type.borderThickness * 2);
            context.drawStackOverlay(
                textRenderer,
                stack,
                x + type.borderThickness * 2,
                y + type.borderThickness * 2
            );
        }
    }
}