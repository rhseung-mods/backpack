package com.rhseung.backpack.backpack

import com.rhseung.backpack.init.ModScreenHandlerTypes
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.ScreenHandlerType

enum class BackpackSize(private val size: Int) {
    `9X1`(9),
    `9X2`(18),
    `9X3`(27),
    `9X4`(36),
    `9X5`(45),
    `9X6`(54);

    val row = size / 9;

    fun toInt() = size;

    fun toScreenHandlerType() = when (this) {
        `9X1` -> ModScreenHandlerTypes.BACKPACK_9X1
        `9X2` -> ModScreenHandlerTypes.BACKPACK_9X2
        `9X3` -> ModScreenHandlerTypes.BACKPACK_9X3
        `9X4` -> ModScreenHandlerTypes.BACKPACK_9X4
        `9X5` -> ModScreenHandlerTypes.BACKPACK_9X5
        `9X6` -> ModScreenHandlerTypes.BACKPACK_9X6
    }
}