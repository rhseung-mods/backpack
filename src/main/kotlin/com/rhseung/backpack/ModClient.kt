package com.rhseung.backpack

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.init.ModComponents
import com.rhseung.backpack.init.ModItems
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.item.ItemColorProvider
import net.minecraft.client.item.ModelPredicateProviderRegistry
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.util.Hand

object ModClient : ClientModInitializer {
    override fun onInitializeClient() {
        ColorProviderRegistry.ITEM.register(ItemColorProvider { stack, tintIndex ->
            if (tintIndex == 1) DyedColorComponent.getColor(stack, 0) else -1
        }, ModItems.BACKPACK);

        ModelPredicateProviderRegistry.register(ModItems.BACKPACK, ModMain.of("open")) { stack, world, entity, seed ->
            if (entity == null) return@register 0f;
            val player = entity as? PlayerEntity ?: return@register 0f;
            if (player.getStackInHand(Hand.MAIN_HAND).getOrDefault(ModComponents.BACKPACK_OPEN, false) == true)
                1f;
            else
                0f;
        }
    }
}