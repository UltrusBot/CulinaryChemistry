package me.ultrusmods.culinarychemistry.data;

 import me.ultrusmods.culinarychemistry.recipe.JuicingRecipe;
import me.ultrusmods.culinarychemistry.register.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CulinaryChemistryRecipeProvider extends FabricRecipeProvider {
    public CulinaryChemistryRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.STATUS_BASTER)
                .pattern("  S")
                .pattern(" G ")
                .pattern("I  ")
                .define('S', Items.STICK)
                .define('G', Items.GLASS_PANE)
                .define('I', Items.IRON_NUGGET)
                .unlockedBy(getHasName(Items.GLASS_PANE), has(Items.GLASS_PANE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.BLENDER)
                .pattern("IGI")
                .pattern("RCR")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GLASS)
                .define('R', Items.REDSTONE)
                .define('C', Items.CAULDRON)
                .unlockedBy(getHasName(Items.CAULDRON), has(Items.CAULDRON))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ItemRegistry.JUICER)
                .pattern("P")
                .pattern("G")
                .pattern("P")
                .define('P', Items.PISTON)
                .define('G', Items.GLASS)
                .unlockedBy(getHasName(Items.PISTON), has(Items.PISTON))
                .save(output);

        SpecialRecipeBuilder.special(JuicingRecipe::new).save(output, "juicing");
    }
}
