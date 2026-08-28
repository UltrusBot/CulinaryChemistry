package me.ultrusmods.culinarychemistry.recipe;

import me.ultrusmods.culinarychemistry.register.ItemRegistry;
import me.ultrusmods.culinarychemistry.register.RecipeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JuicingRecipe extends CustomRecipe {
    public JuicingRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 3) return false;
        boolean juicer = false, bottle = false, food = false;
        for (ItemStack stack : input.items()) {
            if (stack.isEmpty()) continue;
            if (stack.is(ItemRegistry.JUICER) && !juicer) juicer = true;
            else if (stack.is(Items.GLASS_BOTTLE) && !bottle) bottle = true;
            else if (hasFoodEffects(stack) && !food) food = true;
            else return false;
        }
        return juicer && bottle && food;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        List<net.minecraft.world.effect.MobEffectInstance> effects = new ArrayList<>();
        for (ItemStack stack : input.items()) {
            FoodProperties food = stack.get(DataComponents.FOOD);
            if (food != null) food.effects().forEach(possible ->
                    effects.add(new net.minecraft.world.effect.MobEffectInstance(possible.effect())));
        }
        if (effects.isEmpty()) return ItemStack.EMPTY;
        int color = PotionContents.getColor(effects);
        ItemStack potion = new ItemStack(Items.POTION);
        potion.set(DataComponents.ITEM_NAME, Component.translatable("item.culinarychemistry.extracted_potion"));
        potion.set(DataComponents.POTION_CONTENTS,
                new PotionContents(Optional.empty(), Optional.of(color), effects));
        return potion;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            if (input.getItem(i).is(ItemRegistry.JUICER)) remaining.set(i, new ItemStack(ItemRegistry.JUICER));
        }
        return remaining;
    }

    private static boolean hasFoodEffects(ItemStack stack) {
        FoodProperties food = stack.get(DataComponents.FOOD);
        return food != null && !food.effects().isEmpty();
    }

    @Override public boolean canCraftInDimensions(int width, int height) { return width * height >= 3; }
    @Override public RecipeSerializer<?> getSerializer() { return RecipeRegistry.JUICING; }
}
