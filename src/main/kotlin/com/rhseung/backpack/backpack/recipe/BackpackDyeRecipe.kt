package com.rhseung.backpack.backpack.recipe

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.init.ModRecipeSerializers
import net.minecraft.component.type.DyedColorComponent
import net.minecraft.item.DyeItem
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.SpecialCraftingRecipe
import net.minecraft.recipe.book.CraftingRecipeCategory
import net.minecraft.recipe.input.CraftingRecipeInput
import net.minecraft.registry.RegistryWrapper
import net.minecraft.world.World

class BackpackDyeRecipe(craftingRecipeCategory: CraftingRecipeCategory) : SpecialCraftingRecipe(craftingRecipeCategory) {
    companion object {
        val SERIALIZER = ModRecipeSerializers.BACKPACK_DYE;
    }

    override fun getSerializer(): RecipeSerializer<BackpackDyeRecipe> {
        return SERIALIZER;
    }

    override fun matches(input: CraftingRecipeInput, world: World): Boolean {
        if (input.stackCount <= 1)
            return false;

        var hasDyeItem = false;
        var hasBackpack = false;

        for (i in 0..<input.size()) {
            val itemStack = input.getStackInSlot(i);
            if (itemStack.isEmpty)
                continue;

            if (itemStack.item is DyeItem)
                hasDyeItem = true;
            else if (itemStack.item is BackpackItem)
                hasBackpack = true;
            else
                return false;
        }

        return hasDyeItem && hasBackpack;
    }

    override fun craft(input: CraftingRecipeInput, registries: RegistryWrapper.WrapperLookup): ItemStack {
        val dyeItems = mutableListOf<DyeItem>();
        var backpack = ItemStack.EMPTY;

        for (i in 0..<input.size()) {
            val itemStack = input.getStackInSlot(i);
            if (itemStack.isEmpty)
                continue;

            if (itemStack.item is DyeItem)
                dyeItems.add(itemStack.item as DyeItem);
            else if (itemStack.item is BackpackItem)
                backpack = itemStack;
        }

        return if (!backpack.isEmpty && dyeItems.isNotEmpty())
            DyedColorComponent.setColor(backpack, dyeItems);
        else
            ItemStack.EMPTY;
    }
}