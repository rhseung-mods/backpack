package com.rhseung.backpack.datagen

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.init.ModBlocks
import com.rhseung.backpack.init.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.*
import net.minecraft.state.property.Properties

class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockModel: BlockStateModelGenerator) {
        val identifier = TextureMap.getSubId(ModBlocks.CRATE, "_top_open");

        blockModel.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.CRATE)
            .coordinate(blockModel.createUpDefaultFacingVariantMap())
            .coordinate(BlockStateVariantMap.create(Properties.OPEN)
                .register(false, BlockStateVariant.create()
                    .put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP.upload(ModBlocks.CRATE, blockModel.modelCollector))
                ).register(true, BlockStateVariant.create()
                    .put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP[ModBlocks.CRATE].textures { it.put(TextureKey.TOP, identifier) }.upload(ModBlocks.CRATE, "_open", blockModel.modelCollector))
                )
            )
        );
    }

    override fun generateItemModels(itemModel: ItemModelGenerator) {
        // TODO: opened texture using override
        Models.GENERATED_TWO_LAYERS.upload(ModelIds.getItemModelId(ModItems.BACKPACK), TextureMap.layered(
            ModMain.of("backpack").withPrefixedPath("item/"),
            ModMain.of("backpack_color_panel").withPrefixedPath("item/")
        ), itemModel.writer);
    }
}