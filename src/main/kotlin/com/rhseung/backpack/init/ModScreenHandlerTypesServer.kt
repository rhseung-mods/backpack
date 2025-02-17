package com.rhseung.backpack.init

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.resource.featuretoggle.FeatureFlags
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier

object ModScreenHandlerTypesServer : IModInit {
    fun <T : ScreenHandler> register(id: Identifier, factory: ScreenHandlerType.Factory<T>): ScreenHandlerType<T> {
        return Registry.register(Registries.SCREEN_HANDLER, id, ScreenHandlerType(factory, FeatureFlags.VANILLA_FEATURES));
    }

    fun <T : ScreenHandler, D : Any> register(
        id: Identifier,
        factory: ExtendedScreenHandlerType.ExtendedFactory<T, D>,
        packetCodec: PacketCodec<in RegistryByteBuf, D>,
    ): ExtendedScreenHandlerType<T, D> {
        return Registry.register(Registries.SCREEN_HANDLER, id, ExtendedScreenHandlerType(factory, packetCodec));
    }
}