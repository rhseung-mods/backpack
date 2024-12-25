package com.rhseung.backpack

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.BackpackScreen
import com.rhseung.backpack.init.ModItems
import com.rhseung.backpack.init.ModScreenHandlerTypes
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.client.item.ModelPredicateProviderRegistry
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.util.Hand

object ModClient : ClientModInitializer {
    override fun onInitializeClient() {
        ModelPredicateProviderRegistry.register(ModItems.BACKPACK, BackpackItem.OPEN_PREDICATE) { stack, world, entity, seed ->
            if (entity is PlayerEntity &&
                stack.item is BackpackItem &&
                entity.currentScreenHandler is GenericContainerScreenHandler &&
                ItemStack.areEqual(stack, entity.getStackInHand(Hand.MAIN_HAND))
            ) 1f else 0f;
        }

        ColorProviderRegistry.ITEM.register(ItemColorProvider { stack, tintIndex ->
            if (tintIndex == 1) DyedColorComponent.getColor(stack, 0) else -1
        }, ModItems.BACKPACK);

        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X1, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X2, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X3, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X4, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X5, ::BackpackScreen);
        HandledScreens.register(ModScreenHandlerTypes.BACKPACK_9X6, ::BackpackScreen);
    }
}