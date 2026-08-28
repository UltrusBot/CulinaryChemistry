package me.ultrusmods.culinarychemistry.register;

import me.ultrusmods.culinarychemistry.Constants;
import me.ultrusmods.culinarychemistry.recipe.JuicingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public final class RecipeRegistry {
    public static final RecipeSerializer<JuicingRecipe> JUICING = register("juicing",
            new SimpleCraftingRecipeSerializer<>(JuicingRecipe::new));

    public static void register() {}

    private static <T extends RecipeSerializer<?>> T register(String id, T serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Constants.id(id), serializer);
    }
}
