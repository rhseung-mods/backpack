package com.rhseung.backpack.datagen

import com.rhseung.backpack.backpack.recipe.BackpackDyeRecipe
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.ComplexRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.RecipeGenerator
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class RecipeProvider(
    output: FabricDataOutput,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricRecipeProvider(output, registryLookup) {

    override fun getRecipeGenerator(
        lookUp: RegistryWrapper.WrapperLookup,
        exporter: RecipeExporter
    ): RecipeGenerator {
        return object : RecipeGenerator(lookUp, exporter) {
            override fun generate() {
                ComplexRecipeJsonBuilder.create(::BackpackDyeRecipe).offerTo(exporter, "backpack_dye");
            }
        }
    }

    override fun getName(): String {
        return "Backpack Recipe Provider";
    }
}