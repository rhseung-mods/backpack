package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey

object ModTags : IModInit {
    fun ofItem(name: String): TagKey<Item> {
        return TagKey.of(RegistryKeys.ITEM, ModMain.id(name));
    }

    val BACKPACK = ofItem("backpack");
}