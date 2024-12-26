package com.rhseung.backpack.datagen

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.init.ModBlocks
import com.rhseung.backpack.init.ModComponents
import com.rhseung.backpack.init.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class LanguageProvider(
    dataOutput: FabricDataOutput,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricLanguageProvider(dataOutput, registryLookup) {

    override fun generateTranslations(lookUp: RegistryWrapper.WrapperLookup, translationBuilder: TranslationBuilder) {
        translationBuilder.add(ModBlocks.CRATE, "Crate");
        translationBuilder.add(ModItems.BACKPACK, "Backpack");
        translationBuilder.add(ModItems.LARGE_BACKPACK, "Large Backpack");

        translationBuilder.add(BackpackItem.KEY_OPEN, "Open Backpack");
        translationBuilder.add(BackpackItem.KEY_CATEGORY, "Backpack");
        translationBuilder.add(BackpackItem.SCREEN_NAME, "Backpack");
    }
}