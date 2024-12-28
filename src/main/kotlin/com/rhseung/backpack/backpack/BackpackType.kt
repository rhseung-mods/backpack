package com.rhseung.backpack.backpack

import com.rhseung.backpack.ModMain
import net.minecraft.util.Identifier


enum class BackpackType(
    val row: Int,
    val col: Int,
    screenTexture: String,
    val u: Int,
    val v: Int,
    val width: Int,
    val height: Int
) {
    SMALL(3, 3, "backpack_1", 54, 45, 68, 74),
    MEDIUM(4, 3, "backpack_2", 54, 27, 68, 92),   // +3
    LARGE(3, 5, "backpack_3", 36, 45, 104, 74),    // +3
    HUGE(4, 5, "backpack_4", 36, 27, 104, 92),     // +5
    GIGANTIC(4, 7, "backpack_5", 18, 27, 140, 92), // +8
    ;

    val size = row * col;
    val texture: Identifier = ModMain.of("textures/gui/container/$screenTexture.png");
    val textureWidth = 176;
    val textureHeight = 115;
    val playerInventoryHeight = 92;
    val playerInventoryU = 0;
    val playerInventoryV = 108;

    fun toInt() = size;

//    fun toScreenHandlerType() = when (this) {
//        `9X1` -> ModScreenHandlerTypes.BACKPACK_9X1
//        `9X2` -> ModScreenHandlerTypes.BACKPACK_9X2
//        `9X3` -> ModScreenHandlerTypes.BACKPACK_9X3
//        `9X4` -> ModScreenHandlerTypes.BACKPACK_9X4
//        `9X5` -> ModScreenHandlerTypes.BACKPACK_9X5
//        `9X6` -> ModScreenHandlerTypes.BACKPACK_9X6
//    }
}