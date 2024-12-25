package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.resource.featuretoggle.FeatureFlags
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier

object ModScreenHandlerTypes : IModInit {
    fun <T : ScreenHandler> register(id: Identifier, factory: ScreenHandlerType.Factory<T>): ScreenHandlerType<T> {
        return Registry.register(Registries.SCREEN_HANDLER, id, ScreenHandlerType(factory, FeatureFlags.VANILLA_FEATURES));
    }

    fun <T : ScreenHandler, D : Any> register(id: Identifier, packetCodec: PacketCodec<in RegistryByteBuf, D>, factory: ExtendedScreenHandlerType.ExtendedFactory<T, D>): ExtendedScreenHandlerType<T, D> {
        return Registry.register(Registries.SCREEN_HANDLER, id, ExtendedScreenHandlerType(factory, packetCodec));
    }

    val BACKPACK_9X1 = register(ModMain.of("backpack_9x1"), BackpackScreenHandler::create9x1);
    val BACKPACK_9X2 = register(ModMain.of("backpack_9x2"), BackpackScreenHandler::create9x2);
    val BACKPACK_9X3 = register(ModMain.of("backpack_9x3"), BackpackScreenHandler::create9x3);
    val BACKPACK_9X4 = register(ModMain.of("backpack_9x4"), BackpackScreenHandler::create9x4);
    val BACKPACK_9X5 = register(ModMain.of("backpack_9x5"), BackpackScreenHandler::create9x5);
    val BACKPACK_9X6 = register(ModMain.of("backpack_9x6"), BackpackScreenHandler::create9x6);
}