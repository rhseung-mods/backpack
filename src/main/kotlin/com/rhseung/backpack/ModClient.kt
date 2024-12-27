package com.rhseung.backpack

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.BackpackScreen
import com.rhseung.backpack.init.ModItems
import com.rhseung.backpack.init.ModKeyBindings
import com.rhseung.backpack.init.ModPayloadsS2C
import com.rhseung.backpack.init.ModScreenHandlerTypesClient
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.gui.screen.ingame.HandledScreens

object ModClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModKeyBindings.load();
        ModPayloadsS2C.load();
        ModScreenHandlerTypesClient.load();

        BackpackItem.onClient(ModItems.BACKPACK);
        BackpackItem.onClient(ModItems.LARGE_BACKPACK);

//        EntityModelLayerRegistry.registerModelLayer {  }

        HandledScreens.register(ModScreenHandlerTypesClient.BACKPACK_SCREEN_HANDLER, ::BackpackScreen);
    }
}