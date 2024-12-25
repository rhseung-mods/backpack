package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.crate.CrateBlock
import com.rhseung.backpack.init.ModItems.register
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys

object ModBlocks : IModInit {
    fun <T: Block> register(path: String, block: T, group: RegistryKey<ItemGroup>? = null): T {
        val id = ModMain.of(path);
        val registryKey = RegistryKey.of(RegistryKeys.ITEM, id);
        val ret = Registry.register(Registries.BLOCK, id, block);
        register(path, BlockItem(ret, Item.Settings().registryKey(registryKey)), group);
        return ret;
    }

    val CRATE = register("crate", CrateBlock(Settings.copy(Blocks.BARREL).registryKey(RegistryKey.of(RegistryKeys.BLOCK, ModMain.of("crate")))), ItemGroups.FUNCTIONAL);
}