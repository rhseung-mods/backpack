package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.storage.BackpackContentsComponent
import net.minecraft.component.ComponentType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModComponents : IModInit {
    private fun <T> register(path: String, builder: (ComponentType.Builder<T>) -> ComponentType.Builder<T>): ComponentType<T> {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, ModMain.id(path), builder(ComponentType.builder()).build());
    }

    val BACKPACK_CONTENTS = register("backpack_contents") { it
        .codec(BackpackContentsComponent.CODEC)
        .packetCodec(BackpackContentsComponent.PACKET_CODEC)
    };
}