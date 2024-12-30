package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.util.Texture


enum class BackpackType(
    val size: Int
) {
    SMALL(9),
    MEDIUM(18),
    LARGE(27),
    HUGE(36),
    GIGANTIC(45),
    ;

    init { require(size in 1..54) { "Invalid backpack size: $size" } }

    val row = (size / 9f).toInt();

    val texture = Texture(ModMain.of("textures/gui/container/backpack.png"), 176, 226);
    val slotTexture = Texture(ModMain.of("textures/gui/container/slot.png"), 18, 18);
    val slotBorderTexture = Texture(ModMain.of("textures/gui/container/slot_border.png"), 20, 20);

    val pad = 3;

    val slotStartX = 3 + pad;
    val slotStartY = 3 + 13;

    val borderThickness = 1;

    val backpackU = 0;
    val backpackV = 0;
    val backpackWidth = texture.textureWidth;
    val backpackHeight = slotStartY + row * slotTexture.textureHeight + 2 * borderThickness + pad;

    val playerU = 0;
    val playerV = 129;
    val playerWidth = texture.textureWidth;
    val playerHeight = 97;

    val backpackTexture = texture.part(backpackU, backpackV, backpackWidth, backpackHeight);
    val playerTexture = texture.part(playerU, playerV, playerWidth, playerHeight);

    fun loop(action: (Int, Int) -> Unit) {
        for (i in 0..<row) {
            for (j in 0..<(size - i * 9).coerceAtMost(9)) {
                action(i, j);
            }
        }
    }
}