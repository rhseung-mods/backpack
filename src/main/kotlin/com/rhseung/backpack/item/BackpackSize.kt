package com.rhseung.backpack.item

import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.ScreenHandlerType

enum class BackpackSize(private val size: Int) {
    _9X1(9),
    _9X2(18),
    _9X3(27),
    _9X4(36),
    _9X5(45),
    _9X6(54);

    val row = size / 9;

    fun toInt() = size;

    fun toScreenHandlerType(): ScreenHandlerType<GenericContainerScreenHandler> = when (this) {
        _9X1 -> ScreenHandlerType.GENERIC_9X1
        _9X2 -> ScreenHandlerType.GENERIC_9X2
        _9X3 -> ScreenHandlerType.GENERIC_9X3
        _9X4 -> ScreenHandlerType.GENERIC_9X4
        _9X5 -> ScreenHandlerType.GENERIC_9X5
        _9X6 -> ScreenHandlerType.GENERIC_9X6
    }

//    fun toGenericContainerScreenHandler(syncId: Int, playerInventory: PlayerInventory, inventory: SimpleInventory): GenericContainerScreenHandler {
//        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, inventory);
//    }
}