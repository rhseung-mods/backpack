package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.screen.BackpackScreenHandler
import com.rhseung.backpack.backpack.network.BackpackScreenS2CPayload
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.resource.featuretoggle.FeatureFlags
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
object ModScreenHandlerTypesClient : IModInit {
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

    val BACKPACK_SCREEN_HANDLER: ExtendedScreenHandlerType<BackpackScreenHandler, BackpackScreenS2CPayload> =
        register(ModMain.id("backpack"), ::BackpackScreenHandler, BackpackScreenS2CPayload.PACKET_CODEC);
}