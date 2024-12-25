package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.RenderLayer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class BackpackScreen(
    handler: BackpackScreenHandler,
    playerInventory: PlayerInventory,
    title: Text
) : HandledScreen<BackpackScreenHandler>(handler, playerInventory, title) {

    val rows = handler.size.row;

    init {
        this.backgroundHeight = 114 + rows * 18;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    override fun drawBackground(
        context: DrawContext,
        delta: Float,
        mouseX: Int,
        mouseY: Int
    ) {
        val i = (this.width - this.backgroundWidth) / 2;
        val j = (this.height - this.backgroundHeight) / 2;

        val backpackInventoryTexture = ModMain.of("textures/gui/container/backpack.png");
        val playerInventoryTexture = Identifier.ofVanilla("textures/gui/container/generic_54.png");

        // backpack gui
        context.drawTexture(
            RenderLayer::getGuiTextured,
            backpackInventoryTexture,
            i,
            j,
            0f,
            0f,
            this.backgroundWidth,
            this.rows * 18 + 17,
            256,
            256
        );

        // player inventory gui
        context.drawTexture(
            RenderLayer::getGuiTextured,
            playerInventoryTexture,
            i,
            j + this.rows * 18 + 17,
            0f,
            126f,
            this.backgroundWidth,
            96,
            256,
            256
        );
    }
}