package com.rhseung.backpack.backpack.screen

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.tooltip.BackpackTooltipSubmenuHandler
import com.rhseung.backpack.util.Color
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class BackpackScreen(
    handler: BackpackScreenHandler,
    playerInventory: PlayerInventory,
    title: Text
) : HandledScreen<BackpackScreenHandler>(handler, playerInventory, title) {

    val type = this.handler.backpackType;
    val color = BackpackItem.Companion.getColor(handler.backpackStack);

    init {
        this.backgroundHeight = type.backpackHeight + type.playerHeight;
        this.backgroundWidth = type.playerWidth;
        this.titleX -= type.borderThickness;
        this.playerInventoryTitleY = this.backgroundHeight - type.playerHeight + type.borderThickness + type.pad;
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    override fun drawForeground(context: DrawContext, mouseX: Int, mouseY: Int) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, Color(color).brighter(0.25f).fullAlpha(), false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 4210752, false);
    }

    override fun drawBackground(
        context: DrawContext,
        delta: Float,
        mouseX: Int,
        mouseY: Int
    ) {
        val x0 = (this.width - this.backgroundWidth) / 2;
        val y0 = (this.height - this.backgroundHeight) / 2;

        // backpack background
        type.backpackTexture.draw(context, x0, y0, color);

        // backpack slots
        type.loop { i, j ->
            // slot background
            type.slotTexture.draw(
                context,
                x0 + type.slotStartX + j * type.slotTexture.width + type.borderThickness,
                y0 + type.slotStartY + i * type.slotTexture.height + type.borderThickness,
                color
            );

            // slot border
            type.slotBorderTexture.draw(
                context,
                x0 + type.slotStartX + j * type.slotTexture.width,
                y0 + type.slotStartY + i * type.slotTexture.height,
                color
            );
        };

        // player inventory
        type.playerTexture.draw(context,
            x0,
            y0 + type.backpackTexture.height,
        );
    }
}