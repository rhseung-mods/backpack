package com.rhseung.backpack.backpack

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

    fun toScreenHandlerType(): ScreenHandlerType<GenericContainerScreenHandler> = when (this) {
        `9X1` -> ScreenHandlerType.GENERIC_9X1
        `9X2` -> ScreenHandlerType.GENERIC_9X2
        `9X3` -> ScreenHandlerType.GENERIC_9X3
        `9X4` -> ScreenHandlerType.GENERIC_9X4
        `9X5` -> ScreenHandlerType.GENERIC_9X5
        `9X6` -> ScreenHandlerType.GENERIC_9X6
    }

//    fun toGenericContainerScreenHandler(syncId: Int, playerInventory: PlayerInventory, inventory: SimpleInventory): GenericContainerScreenHandler {
//        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, inventory);
//    }
}