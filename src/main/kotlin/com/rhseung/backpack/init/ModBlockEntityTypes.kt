package com.rhseung.backpack.init

import com.rhseung.backpack.blockentity.CrateBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.datafixer.TypeReferences
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Util

object ModBlockEntityTypes {
    fun load() {}

    fun <T : BlockEntity> register(id: String, blockEntityType: BlockEntityType<T>): BlockEntityType<T> {
        Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType);
    }

    val CRATE = register("crate", FabricBlockEntityTypeBuilder.create(::CrateBlockEntity, ModBlocks.CRATE).build());
}