package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import net.minecraft.component.ComponentType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModComponents : IModInit {
    private fun <T> register(path: String, builder: (ComponentType.Builder<T>) -> ComponentType.Builder<T>): ComponentType<T> {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, ModMain.of(path), builder(ComponentType.builder()).build());
    }
}