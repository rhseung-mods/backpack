package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.item.BackpackItem
import com.rhseung.backpack.item.BackpackSize
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys

object ModItems {
    fun load() {}

    fun <T : Item> registerItem(path: String, item: T, group: RegistryKey<ItemGroup>? = null): T {
        val ret = Registry.register(Registries.ITEM, ModMain.of(path), item);
        group?.let { ItemGroupEvents.modifyEntriesEvent(it).register { entries -> entries.add(ret) } }
        return ret;
    }

    val BACKPACK = registerItem(
        "backpack", BackpackItem(BackpackSize.GENERIC_9X3,
            Settings().registryKey(
                RegistryKey.of(
                    RegistryKeys.ITEM, ModMain
                        .of("backpack")
                )
            )
        ), ItemGroups.TOOLS
    );
}