package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.util.Color
import com.rhseung.backpack.util.Texture
import com.rhseung.backpack.util.Utils.drawGuiTextureColor
import com.rhseung.backpack.util.Utils.drawTexture2
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.RenderLayer
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import java.awt.Point

class BackpackScreen(
    handler: BackpackScreenHandler,
    playerInventory: PlayerInventory,
    title: Text
) : HandledScreen<BackpackScreenHandler>(handler, playerInventory, title) {

    val type = this.handler.backpackType;
    val color = DyedColorComponent.getColor(handler.backpackStack, BackpackItem.COLOR_DEFAULT);

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
        context.drawTexture2(
            RenderLayer::getGuiTextured,
            type.backpackTexture,
            Point(x0, y0),
            color
        );

        // backpack slots
        type.loop { i, j ->
            // slot background
            context.drawTexture2(
                RenderLayer::getGuiTextured,
                type.slotTexture,
                Point(
                    x0 + type.slotStartX + j * type.slotTexture.width + type.borderThickness,
                    y0 + type.slotStartY + i * type.slotTexture.height + type.borderThickness,
                ),
                color
            );

            // slot border
            context.drawTexture2(
                RenderLayer::getGuiTextured,
                type.slotBorderTexture,
                Point(
                    x0 + type.slotStartX + j * type.slotTexture.width,
                    y0 + type.slotStartY + i * type.slotTexture.height,
                ),
                color
            );
        };

        // player inventory
        context.drawTexture2(
            RenderLayer::getGuiTextured,
            type.playerTexture,
            Point(
                x0,
                y0 + type.backpackTexture.height,
            )
        );
    }
}