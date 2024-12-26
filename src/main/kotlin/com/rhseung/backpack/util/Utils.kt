package com.rhseung.backpack.util

import net.minecraft.item.ItemStack
import net.minecraft.screen.Property
import net.minecraft.screen.PropertyDelegate

object Utils {
    fun property(default: Int = 0) = object : Property() {
        private var value = default;

        override fun get(): Int {
            return this.value;
        }

        override fun set(value: Int) {
            this.value = value;
        }
    }

    fun propertyDelegate(default: Int = 0) = object : PropertyDelegate {
        private var value = default;

        override fun get(index: Int): Int {
            return this.value;
        }

        override fun set(index: Int, value: Int) {
            this.value = value;
        }

        override fun size(): Int {
            return 1;
        }
    }

    fun Boolean.toInt() = if (this) 1 else 0;
}