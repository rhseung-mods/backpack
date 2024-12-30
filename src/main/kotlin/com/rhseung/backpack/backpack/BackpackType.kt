package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import net.minecraft.util.Identifier


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

    val texture: Identifier = ModMain.of("textures/gui/container/backpack.png");
    val textureWidth = 255;
    val textureHeight = 255;

    val slotStartX = 3 + 3;
    val slotStartY = 3 + 13;

    val slotU = 175;
    val slotV = 0;
    val slotWidth = 18;
    val slotHeight = 18;
    val slotBorderU = 176;
    val slotBorderV = 18;
    val slotBorderWidth = 20;
    val slotBorderHeight = 20;

    val borderThickness = 1;

    val backpackU = 0;
    val backpackV = 0;
    val backpackWidth = 175;
    val backpackHeight = slotStartY + 2 * borderThickness + row * slotHeight + 3 + borderThickness + 1

    val playerU = 0;
    val playerV = 129;
    val playerWidth = 175;
    val playerHeight = 97;

//    fun toScreenHandlerType() = when (this) {
//        `9X1` -> ModScreenHandlerTypes.BACKPACK_9X1
//        `9X2` -> ModScreenHandlerTypes.BACKPACK_9X2
//        `9X3` -> ModScreenHandlerTypes.BACKPACK_9X3
//        `9X4` -> ModScreenHandlerTypes.BACKPACK_9X4
//        `9X5` -> ModScreenHandlerTypes.BACKPACK_9X5
//        `9X6` -> ModScreenHandlerTypes.BACKPACK_9X6
//    }
}