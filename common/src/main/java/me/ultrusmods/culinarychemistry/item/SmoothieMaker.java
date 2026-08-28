package me.ultrusmods.culinarychemistry.item;

import me.ultrusmods.culinarychemistry.component.SmoothieIngredients;
import me.ultrusmods.culinarychemistry.item.smoothie.SmoothieEffect;
import me.ultrusmods.culinarychemistry.register.CulinaryChemistryRegistries;
import me.ultrusmods.culinarychemistry.register.DataComponentRegistry;
import me.ultrusmods.culinarychemistry.register.ItemRegistry;
import me.ultrusmods.culinarychemistry.tag.CulinaryChemistryTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;

import java.util.*;

public final class SmoothieMaker {
    public static final LinkedHashMap<TagKey<Item>, String> INGREDIENT_TO_SMOOTHIE_NAMES = new LinkedHashMap<>();

    static {
        INGREDIENT_TO_SMOOTHIE_NAMES.put(CulinaryChemistryTags.CC_FOUL, "item.culinarychemistry.foul_smoothie");
        INGREDIENT_TO_SMOOTHIE_NAMES.put(ItemTags.FISHES, "item.culinarychemistry.fish_smoothie");
        INGREDIENT_TO_SMOOTHIE_NAMES.put(ItemTags.MEAT, "item.culinarychemistry.meat_smoothie");
        INGREDIENT_TO_SMOOTHIE_NAMES.put(CulinaryChemistryTags.C_GOLDEN, "item.culinarychemistry.golden_smoothie");
        INGREDIENT_TO_SMOOTHIE_NAMES.put(CulinaryChemistryTags.C_BERRY, "item.culinarychemistry.berry_smoothie");
        INGREDIENT_TO_SMOOTHIE_NAMES.put(CulinaryChemistryTags.C_FRUIT, "item.culinarychemistry.fruit_smoothie");
        INGREDIENT_TO_SMOOTHIE_NAMES.put(CulinaryChemistryTags.C_VEGETABLE, "item.culinarychemistry.vegetable_smoothie");
    }


    public static ItemStack make(List<ItemStack> ingredients, ItemStack extra, Level level) {
        DyeItem dye = extra.getItem() instanceof DyeItem dyeItem ? dyeItem : null;
        boolean hasSpecialIngredient = extra.is(CulinaryChemistryTags.CC_SPECIAL_INGREDIENTS);
        boolean potionOnly = !hasSpecialIngredient && ingredients.stream().allMatch(SmoothieMaker::isBottledPotion);
        int nutrition = 0;
        float saturation = 0;
        List<FoodProperties.PossibleEffect> foodEffects = new ArrayList<>();
        List<MobEffectInstance> potionEffects = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> potionNames = new ArrayList<>();
        boolean enchanted = false;
        int red = 0, green = 0, blue = 0, colors = 0;

        for (ItemStack ingredient : ingredients) {
            FoodProperties food = ingredient.get(DataComponents.FOOD);
            if (food != null) {
                nutrition += food.nutrition();
                saturation += food.saturation();
                foodEffects.addAll(food.effects());
                names.add(ingredient.getHoverName().getString());
                int foodColor = SmoothieItemColors.getColor(ingredient);
                red += foodColor >> 16 & 255;
                green += foodColor >> 8 & 255;
                blue += foodColor & 255;
                colors++;
            }
            if (ingredient.getOrDefault(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false)) {
                enchanted = true;
            }
            PotionContents potion = ingredient.get(DataComponents.POTION_CONTENTS);
            if (potion != null) {
                potion.forEachEffect(effect -> {
                    potionEffects.add(new MobEffectInstance(effect));
                    potionNames.add(effect.getEffect().value().getDisplayName().getString() + formatAmplifier(effect.getAmplifier()));
                });
                if (potion.hasEffects()) {
                    int color = potion.getColor();
                    red += color >> 16 & 255;
                    green += color >> 8 & 255;
                    blue += color & 255;
                    colors++;
                }
            }
        }

        int color = dye != null ? dye.getDyeColor().getTextureDiffuseColor()
                : colors > 0 ? (red / colors << 16) | (green / colors << 8) | blue / colors
                : SmoothieItemColors.DEFAULT_COLOR;

        if (potionOnly) {
            ItemStack result = new ItemStack(Items.POTION);
            result.set(DataComponents.POTION_CONTENTS,
                    new PotionContents(Optional.empty(), Optional.of(color), potionEffects));
            String name = potionNames.isEmpty() ? "Combined Potion" : "Potion of " + String.join(" & ", potionNames);
            result.set(DataComponents.CUSTOM_NAME, Component.literal(name));
            return result;
        }

        ItemStack result = new ItemStack(ItemRegistry.SMOOTHIE);

        for (Map.Entry<TagKey<Item>, String> entry : INGREDIENT_TO_SMOOTHIE_NAMES.entrySet()) {
            long matchingIngredients = ingredients.stream().filter(ingredient -> ingredient.is(entry.getKey())).count();
            if (matchingIngredients >= 2) {
                result.set(DataComponents.ITEM_NAME, Component.translatable(entry.getValue()));
                break;
            }
        }

        if (enchanted) {
            result.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        }
        result.set(DataComponents.FOOD, new FoodProperties(nutrition, saturation, true, 1.6F,
                Optional.empty(), foodEffects));
        if (!potionEffects.isEmpty()) {
            result.set(DataComponents.POTION_CONTENTS,
                    new PotionContents(Optional.empty(), Optional.of(color), potionEffects));
        }
        List<Holder<SmoothieEffect>> smoothieEffects = new ArrayList<>();
        List<ItemStack> effectIngredients = new ArrayList<>(ingredients);
        if (hasSpecialIngredient) effectIngredients.add(extra);
        CulinaryChemistryRegistries.SMOOTHIE_EFFECTS.holders().forEach(effect -> {
            if (effectIngredients.stream().anyMatch(effect.value()::isValidIngredient)) {
                smoothieEffects.add(effect);
            }
        });
        result.set(DataComponentRegistry.SMOOTHIE_INGREDIENTS, new SmoothieIngredients(smoothieEffects, color));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.literal("Ingredients:").withStyle(ChatFormatting.GRAY));
        ingredients.forEach(ingredient -> lore.add(Component.literal("- ").withStyle(ChatFormatting.GRAY).append(ingredient.getHoverName()).withStyle(ChatFormatting.GRAY)));
        if (hasSpecialIngredient) {
            lore.add(Component.literal("- ").withStyle(ChatFormatting.GRAY).append(extra.getHoverName()).withStyle(ChatFormatting.GRAY));
        }
        result.set(DataComponents.LORE, new ItemLore(lore));
        return result;
    }

    private static boolean isBottledPotion(ItemStack stack) {
        return stack.is(Items.POTION) || stack.is(Items.SPLASH_POTION) || stack.is(Items.LINGERING_POTION);
    }

    private static String formatAmplifier(int amplifier) {
        return switch (amplifier) {
            case 0 -> "";
            case 1 -> " II";
            case 2 -> " III";
            case 3 -> " IV";
            case 4 -> " V";
            default -> " " + (amplifier + 1);
        };
    }
}
