package com.rhseung.backpack.datagen

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.datagen.helper.ItemModelHelper
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

    private fun backpackOpened(item: BackpackItem): ItemModelHelper.ItemModelBuilder {
        return ItemModelHelper.itemModel("_opened")
            .item(item)
            .parent("generated")
            .textures(mapOf(
                TextureKey.LAYER0 to ModMain.of("backpack_opened"),
                TextureKey.LAYER1 to ModMain.of("backpack_color_panel")
            ));
    }

    fun backpack(item: BackpackItem): ItemModelHelper.ItemModelBuilder {
        return ItemModelHelper.itemModel()
            .item(item)
            .parent("generated")
//            .overrides(listOf(
//                mapOf(
//                    BackpackItem.PREDICATE_OPEN to 1
//                ) to backpackOpened(item)
//            ))
            .textures(mapOf(
                TextureKey.LAYER0 to ModMain.of("backpack"),
                TextureKey.LAYER1 to ModMain.of("backpack_cover_${item.type.ordinal}")
            ));
    }

    override fun generateItemModels(itemModel: ItemModelGenerator) {
        backpack(ModItems.SMALL_BACKPACK).upload(itemModel.writer);
        backpack(ModItems.MEDIUM_BACKPACK).upload(itemModel.writer);
        backpack(ModItems.LARGE_BACKPACK).upload(itemModel.writer);
        backpack(ModItems.HUGE_BACKPACK).upload(itemModel.writer);
        backpack(ModItems.GIGANTIC_BACKPACK).upload(itemModel.writer);
    }
}