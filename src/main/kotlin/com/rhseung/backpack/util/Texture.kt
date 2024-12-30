package com.rhseung.backpack.util

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.RenderLayer
import net.minecraft.util.Identifier

data class Texture(
    val id: Identifier,
    val textureWidth: Int,
    val textureHeight: Int,
    val width: Int = textureWidth,
    val height: Int = textureHeight,
    val u: Int = 0,
    val v: Int = 0
) {
    fun part(u: Int, v: Int, width: Int, height: Int) = Texture(id, textureWidth, textureHeight, width, height, u, v);

    fun draw(context: DrawContext, x: Int, y: Int) {
        context.drawTexture(
            RenderLayer::getGuiTextured,
            this.id,
            x,
            y,
            this.u.toFloat(),
            this.v.toFloat(),
            this.width,
            this.height,
            this.textureWidth,
            this.textureHeight
        );
    }

    fun draw(context: DrawContext, x: Int, y: Int, color: Int) {
        context.drawTexture(
            RenderLayer::getGuiTextured,
            this.id,
            x,
            y,
            this.u.toFloat(),
            this.v.toFloat(),
            this.width,
            this.height,
            this.textureWidth,
            this.textureHeight,
            color
        );
    }
}