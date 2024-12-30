package com.rhseung.backpack.util

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
}