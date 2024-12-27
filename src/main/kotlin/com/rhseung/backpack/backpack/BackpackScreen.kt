package com.rhseung.backpack.backpack

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

    init {
        val type = this.handler.backpackType;
        this.backgroundHeight = type.textureHeight - type.v;
        this.backgroundWidth = type.textureWidth;
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    override fun drawForeground(context: DrawContext, mouseX: Int, mouseY: Int) {
        // container title 생략
    }

    override fun drawBackground(
        context: DrawContext,
        delta: Float,
        mouseX: Int,
        mouseY: Int
    ) {
        val i = (this.width - this.backgroundWidth) / 2;
        val j = (this.height - this.backgroundHeight) / 2;
        val type = this.handler.backpackType;

        // player inventory gui
        context.drawTexture(
            RenderLayer::getGuiTextured,
            type.texture,
            i + type.playerInventoryU,
            j + type.playerInventoryV,
            type.playerInventoryU.toFloat(),
            type.playerInventoryV.toFloat(),
            this.backgroundWidth,
            this.backgroundHeight - type.playerInventoryV,
            type.textureWidth,
            type.textureHeight
        );

        // backpack gui
        context.drawTexture(
            RenderLayer::getGuiTextured,
            type.texture,
            i + type.u,
            j + type.v,
            type.u.toFloat(),
            type.v.toFloat(),
            type.width,
            type.height,
            type.textureWidth,
            type.textureHeight,
            DyedColorComponent.getColor(handler.backpackStack, ColorHelper.fullAlpha(0x825939))
        );
    }
}