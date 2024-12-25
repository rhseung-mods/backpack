package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.BackpackSize
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys

object ModItems : IModInit {
    fun <T : Item> register(path: String, item: T, group: RegistryKey<ItemGroup>? = null): T {
        val ret = Registry.register(Registries.ITEM, ModMain.of(path), item);
        group?.let { ItemGroupEvents.modifyEntriesEvent(it).register { entries -> entries.add(ret) } }
        return ret;
    }

    fun ofSetting(id: String, settings: Settings = Settings()): Settings {
        return settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, ModMain.of(id)));
    }

    val BACKPACK = register("backpack", BackpackItem(BackpackSize.`9X3`, ofSetting("backpack")), ItemGroups.TOOLS);
}