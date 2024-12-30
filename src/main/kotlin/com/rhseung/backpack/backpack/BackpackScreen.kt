package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.RenderLayer
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper

class BackpackScreen(
    handler: BackpackScreenHandler,
    playerInventory: PlayerInventory,
    title: Text
) : HandledScreen<BackpackScreenHandler>(handler, playerInventory, title) {

    val type = this.handler.backpackType;

    init {
        this.backgroundHeight = type.backpackHeight + type.playerHeight;
        this.backgroundWidth = type.playerWidth;
        this.titleX -= type.borderThickness;
        this.playerInventoryTitleY = this.backgroundHeight - type.playerHeight + type.borderThickness + 2;
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    override fun drawForeground(context: DrawContext, mouseX: Int, mouseY: Int) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, ColorHelper.fromFloats(1f, 1f, 1f, 1f), false);
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
        val color = DyedColorComponent.getColor(handler.backpackStack, BackpackItem.COLOR_DEFAULT);

        // backpack background
        context.drawTexture(
            RenderLayer::getGuiTextured,
            type.texture,
            x0,
            y0,
            type.backpackU.toFloat(),
            type.backpackV.toFloat(),
            type.backpackWidth,
            type.backpackHeight,
            type.textureWidth,
            type.textureHeight,
            color
        );

        // backpack slots
        for (i in 0..<type.row) {
            for (j in 0..<(type.size - i * 9).coerceAtMost(9)) {
                // slot background
                context.drawTexture(
                    RenderLayer::getGuiTextured,
                    type.texture,
                    x0 + type.slotStartX + j * type.slotWidth + type.borderThickness,
                    y0 + type.slotStartY + i * type.slotHeight + type.borderThickness,
                    type.slotU.toFloat(),
                    type.slotV.toFloat(),
                    type.slotWidth,
                    type.slotHeight,
                    type.textureWidth,
                    type.textureHeight,
                    color
                );

                // slot border
                context.drawTexture(
                    RenderLayer::getGuiTextured,
                    type.texture,
                    x0 + type.slotStartX + j * type.slotWidth,
                    y0 + type.slotStartY + i * type.slotHeight,
                    type.slotBorderU.toFloat(),
                    type.slotBorderV.toFloat(),
                    type.slotBorderWidth,
                    type.slotBorderHeight,
                    type.textureWidth,
                    type.textureHeight,
                    color
                );
            }
        }

        // player inventory
        context.drawTexture(
            RenderLayer::getGuiTextured,
            type.texture,
            x0,
            y0 + type.backpackHeight,
            type.playerU.toFloat(),
            type.playerV.toFloat(),
            type.playerWidth,
            type.playerHeight,
            type.textureWidth,
            type.textureHeight
        );
    }
}