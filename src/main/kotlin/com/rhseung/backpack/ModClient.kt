package com.rhseung.backpack

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.BackpackScreen
import com.rhseung.backpack.init.ModItems
import com.rhseung.backpack.init.ModKeyBindings
import com.rhseung.backpack.init.ModPayloadsS2C
import com.rhseung.backpack.init.ModScreenHandlerTypes
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens

object ModClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModKeyBindings.load();
        ModPayloadsS2C.load();

        BackpackItem.onClient(ModItems.BACKPACK);
        BackpackItem.onClient(ModItems.LARGE_BACKPACK);

//        EntityModelLayerRegistry.registerModelLayer {  }

        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X1, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X2, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X3, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X4, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X5, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X6, ::BackpackScreen);
    }
}