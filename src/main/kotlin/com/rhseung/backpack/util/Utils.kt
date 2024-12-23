package com.rhseung.backpack.util

import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.datafixer.TypeReferences
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Util

object Utils {
    fun <T : BlockEntity> create(id: String, blockEntityType: BlockEntityType<T>): BlockEntityType<T> {
        Util.getChoiceType(TypeReferences.BLOCK_ENTITY, id);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType);
    }
}