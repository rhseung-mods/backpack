package com.rhseung.backpack.datagen

import com.rhseung.backpack.init.ModItems
import com.rhseung.backpack.init.ModTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.ItemTags
import java.util.concurrent.CompletableFuture

class ItemTagProvider(
    output: FabricDataOutput,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider<Item>(output, RegistryKeys.ITEM, registryLookup) {

    override fun configure(lookUp: RegistryWrapper.WrapperLookup) {
        getOrCreateTagBuilder(ItemTags.DYEABLE)
            .add(ModItems.SMALL_BACKPACK)
            .add(ModItems.MEDIUM_BACKPACK)
            .add(ModItems.LARGE_BACKPACK)
            .add(ModItems.HUGE_BACKPACK)
            .add(ModItems.GIGANTIC_BACKPACK)
        ;

        getOrCreateTagBuilder(ModTags.BACKPACK)
            .add(ModItems.SMALL_BACKPACK)
            .add(ModItems.MEDIUM_BACKPACK)
            .add(ModItems.LARGE_BACKPACK)
            .add(ModItems.HUGE_BACKPACK)
            .add(ModItems.GIGANTIC_BACKPACK)
        ;
    }
}