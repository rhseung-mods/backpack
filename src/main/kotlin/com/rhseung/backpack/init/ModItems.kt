package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.BackpackType
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
        val ret = Registry.register(Registries.ITEM, ModMain.id(path), item);
        group?.let { ItemGroupEvents.modifyEntriesEvent(it).register { entries -> entries.add(ret) } }
        return ret;
    }

    fun ofSetting(id: String, settings: Settings = Settings()): Settings {
        return settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, ModMain.id(id)));
    }

    fun registerBackpack(path: String, type: BackpackType): BackpackItem {
        return register(path, BackpackItem(type, ofSetting(path)), ItemGroups.TOOLS);
    }

    val SMALL_BACKPACK = registerBackpack("small_backpack", BackpackType.SMALL);
    val MEDIUM_BACKPACK = registerBackpack("medium_backpack", BackpackType.MEDIUM);
    val LARGE_BACKPACK = registerBackpack("large_backpack", BackpackType.LARGE);
    val HUGE_BACKPACK = registerBackpack("huge_backpack", BackpackType.HUGE);
    val GIGANTIC_BACKPACK = registerBackpack("gigantic_backpack", BackpackType.GIGANTIC);
}