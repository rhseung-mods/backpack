package com.rhseung.backpack.item

import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.ScreenHandlerType

enum class BackpackSize(private val size: Int) {
    GENERIC_9X1(9),
    GENERIC_9X2(18),
    GENERIC_9X3(27),
    GENERIC_9X4(36),
    GENERIC_9X5(45),
    GENERIC_9X6(54);

    val row = size / 9;

    fun toInt() = size;

    fun toScreenHandlerType(): ScreenHandlerType<GenericContainerScreenHandler> = when (this) {
        GENERIC_9X1 -> ScreenHandlerType.GENERIC_9X1
        GENERIC_9X2 -> ScreenHandlerType.GENERIC_9X2
        GENERIC_9X3 -> ScreenHandlerType.GENERIC_9X3
        GENERIC_9X4 -> ScreenHandlerType.GENERIC_9X4
        GENERIC_9X5 -> ScreenHandlerType.GENERIC_9X5
        GENERIC_9X6 -> ScreenHandlerType.GENERIC_9X6
    }

//    fun toGenericContainerScreenHandler(syncId: Int, playerInventory: PlayerInventory, inventory: SimpleInventory): GenericContainerScreenHandler {
//        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, inventory);
//    }
}