package com.rhseung.backpack.init

import com.rhseung.backpack.backpack.BackpackDyeRecipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.SpecialCraftingRecipe

object ModRecipeSerializers : IModInit {
    val BACKPACK_DYE: RecipeSerializer<BackpackDyeRecipe> = RecipeSerializer
        .register("crafting_special_backpack_dye", SpecialCraftingRecipe.SpecialRecipeSerializer(::BackpackDyeRecipe));
}