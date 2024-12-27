package com.rhseung.backpack.backpack

enum class BackpackSize(val row: Int, val col: Int) {
    SMALL(3, 3),
    MEDIUM(4, 3),   // +3
    LARGE(3, 5),    // +3
    HUGE(4, 5),     // +5
    GIGANTIC(4, 7), // +8
    `9X3`(3, 9),
    `9X4`(4, 9);

    val size = row * col;

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