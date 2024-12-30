package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.util.Texture
import net.minecraft.util.Identifier


enum class BackpackType(val size: Int, backpackCoverTexture: String) {
    SMALL(9, "backpack_cover_0"),
    MEDIUM(18, "backpack_cover_1"),
    LARGE(27, "backpack_cover_2"),
    HUGE(36, "backpack_cover_3"),
    GIGANTIC(45, "backpack_cover_4");

    init { require(size in 1..54) { "Invalid backpack size: $size" } }

    val row = (size / 9f).toInt();
    val backpackCoverTextureId: Identifier = ModMain.of(backpackCoverTexture);

    val texture = Texture(ModMain.of("textures/gui/container/backpack.png"), 176, 226);
    val slotTexture = Texture(ModMain.of("textures/gui/container/backpack_slot.png"), 18, 18);
    val slotBorderTexture = Texture(ModMain.of("textures/gui/container/backpack_slot_border.png"), 20, 20);
    val tooltipTexture = Texture(ModMain.of("textures/gui/container/backpack_tooltip.png"), 176, 122);

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

    val tooltipWidth = 3 + pad + slotTexture.textureWidth * size.coerceAtMost(9) + 2 * borderThickness + pad + 3;

    val backpackTexture = texture.part(backpackU, backpackV, backpackWidth, backpackHeight);
    val playerTexture = texture.part(playerU, playerV, playerWidth, playerHeight);
    val tooltipTopTexture = tooltipTexture.part(0, 0, tooltipWidth, 3 + pad);
    val tooltipMiddleTexture = tooltipTexture.part(0, 3 + pad, tooltipWidth, row * slotTexture.textureHeight + 2 * borderThickness);
    val tooltipBottomTexture = tooltipTexture.part(0, tooltipTexture.textureHeight - (3 + pad), tooltipWidth, 3 + pad);

    fun loop(action: (Int, Int) -> Unit) {
        for (i in 0..<row) {
            for (j in 0..<(size - i * 9).coerceAtMost(9)) {
                action(i, j);
            }
        }
    }
}