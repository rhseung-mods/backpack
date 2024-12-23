package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.blockentity.CrateBlockEntity
import com.rhseung.backpack.util.Utils
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.datafixer.TypeReferences
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Util

object ModBlockEntityTypes {
    fun load() {}

    fun <T: BlockEntity> register(path: String, blockEntityType: BlockEntityType<T>): BlockEntityType<T> {
        val id = ModMain.of(path);
        Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id.toString());
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType);
    }

    val CRATE = Utils.create("crate", FabricBlockEntityTypeBuilder.create(::CrateBlockEntity, ModBlocks.CRATE).build());
}