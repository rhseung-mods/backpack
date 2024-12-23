package com.rhseung.backpack.init

import com.mojang.serialization.Codec
import com.rhseung.backpack.ModMain
import net.minecraft.component.ComponentType
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModComponents {
    fun load() {}

    private fun <T> register(path: String, builder: (ComponentType.Builder<T>) -> ComponentType.Builder<T>): ComponentType<T> {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, ModMain.of(path), builder(ComponentType.builder()).build());
    }

    val BACKPACK_OPEN = register("backpack_open") { builder ->
        builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL);
    }
}