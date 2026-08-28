package me.ultrusmods.culinarychemistry.tag;

import me.ultrusmods.culinarychemistry.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

public class CulinaryChemistryTags {
    public static final TagKey<MobEffect> BLACKLISTED_EFFECTS = TagKey.create(
            Registries.MOB_EFFECT,
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "blacklisted_effects")
    );
    public static TagKey<Item> C_BERRY = registerItem("c", "foods/berry");
    public static TagKey<Item> C_FRUIT = registerItem("c", "foods/fruit");
    public static TagKey<Item> C_VEGETABLE = registerItem("c", "foods/vegetable");
    public static TagKey<Item> C_GOLDEN = registerItem("c", "foods/golden");
    public static TagKey<Item> CC_FOUL = registerItem(Constants.MOD_ID, "foul");

    /**
     * Used for special non food ingredients that are allowed in smoothies (usually for special effects)
     */
    public static TagKey<Item> CC_SPECIAL_INGREDIENTS = registerItem(Constants.MOD_ID, "special_ingredients");




    private static TagKey<Item> registerItem(String namespace, String tagId) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, tagId));
    }
}
