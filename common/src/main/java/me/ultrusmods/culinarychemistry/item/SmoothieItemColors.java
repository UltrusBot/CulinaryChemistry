package me.ultrusmods.culinarychemistry.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Not the most ideal, but eh, maybe make it data driven(?)
 * TODO (cont): Can't easily do automatic because of color being stored on item.c
 */
public class SmoothieItemColors {
    public static final int DEFAULT_COLOR = 0x596B6B;
    public static Map<Item, Integer> ITEM_COLORS = new HashMap<>();

    public static int getColor(ItemStack stack) {
        return ITEM_COLORS.getOrDefault(stack.getItem(), DEFAULT_COLOR);
    }

    public static void init() {
        ITEM_COLORS.put(Items.APPLE, 0xAB1C19);
        ITEM_COLORS.put(Items.BAKED_POTATO, 0xB08031);
        ITEM_COLORS.put(Items.BEETROOT, 0x852826);
        ITEM_COLORS.put(Items.BEETROOT_SOUP, 0x422E0C);
        ITEM_COLORS.put(Items.BREAD, 0x956D20);
        ITEM_COLORS.put(Items.CAKE, 0xF3E9D5);
        ITEM_COLORS.put(Items.CARROT, 0xD96F13);
        ITEM_COLORS.put(Items.CHORUS_FRUIT, 0x6D496C);
        ITEM_COLORS.put(Items.COOKED_CHICKEN, 0xB86F47);
        ITEM_COLORS.put(Items.COOKED_COD, 0xB79573);
        ITEM_COLORS.put(Items.COOKED_MUTTON, 0x804D3B);
        ITEM_COLORS.put(Items.COOKED_PORKCHOP, 0xBCA370);
        ITEM_COLORS.put(Items.COOKED_RABBIT, 0xD59B76);
        ITEM_COLORS.put(Items.COOKED_SALMON, 0xA6552D);
        ITEM_COLORS.put(Items.COOKIE, 0xCE8141);
        ITEM_COLORS.put(Items.DRIED_KELP, 0x312C24);
        ITEM_COLORS.put(Items.ENCHANTED_GOLDEN_APPLE, 0xE4BD32);
        ITEM_COLORS.put(Items.GOLDEN_APPLE, 0xE4BD32);
        ITEM_COLORS.put(Items.GLOW_BERRIES, 0xC36C26);
        ITEM_COLORS.put(Items.GOLDEN_CARROT, 0xE4BD32);
        ITEM_COLORS.put(Items.HONEY_BOTTLE, 0x8CB0D7);
        ITEM_COLORS.put(Items.MELON_SLICE, 0xB3291C);
        ITEM_COLORS.put(Items.MUSHROOM_STEW, 0x422E0C);
        ITEM_COLORS.put(Items.POISONOUS_POTATO, 0x506D3D);
        ITEM_COLORS.put(Items.POTATO, 0xBE8C3A);
        ITEM_COLORS.put(Items.PUFFERFISH, 0xB57D24);
        ITEM_COLORS.put(Items.PUMPKIN_PIE, 0xD49053);
        ITEM_COLORS.put(Items.RABBIT_STEW, 0x46300D);
        ITEM_COLORS.put(Items.BEEF, 0xD44C3F);
        ITEM_COLORS.put(Items.CHICKEN, 0xC89381);
        ITEM_COLORS.put(Items.COD, 0xA27A52);
        ITEM_COLORS.put(Items.MUTTON, 0xC84A40);
        ITEM_COLORS.put(Items.PORKCHOP, 0xE36F6E);
        ITEM_COLORS.put(Items.RABBIT, 0xEBC0AF);
        ITEM_COLORS.put(Items.SALMON, 0x53443B);
        ITEM_COLORS.put(Items.ROTTEN_FLESH, 0x8C4320);
        ITEM_COLORS.put(Items.SPIDER_EYE, 0x4D051E);
        ITEM_COLORS.put(Items.COOKED_BEEF, 0x5F3525);
        ITEM_COLORS.put(Items.SUSPICIOUS_STEW, 0x402D0C);
        ITEM_COLORS.put(Items.SWEET_BERRIES, 0xa50700);
        ITEM_COLORS.put(Items.TROPICAL_FISH, 0xf58a48);
    }
}
