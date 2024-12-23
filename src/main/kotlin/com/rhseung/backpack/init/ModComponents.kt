package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.item.BackpackContainer
import net.minecraft.component.ComponentType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModComponents {
    fun load() {}

    private fun <T> register(path: String, builder: (ComponentType.Builder<T>) -> ComponentType.Builder<T>): ComponentType<T> {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, ModMain.of(path), builder(ComponentType.builder()).build());
    }

//    val BACKPACK_CONTENTS: ComponentType<BackpackContainer> = register("backpack_contents") { builder ->
//        builder.codec(BackpackContainer.CODEC).packetCodec(BackpackContainer.PACKET_CODEC).cache();
//    }
}