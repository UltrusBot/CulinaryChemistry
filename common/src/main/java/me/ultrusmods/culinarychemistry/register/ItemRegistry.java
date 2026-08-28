package me.ultrusmods.culinarychemistry.register;

import me.ultrusmods.culinarychemistry.Constants;
import me.ultrusmods.culinarychemistry.item.BlenderBlockItem;
import me.ultrusmods.culinarychemistry.item.SmoothieItem;
import me.ultrusmods.culinarychemistry.item.SmoothieItemColors;
import me.ultrusmods.culinarychemistry.item.StatusBasterItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public final class ItemRegistry {
    public static final Item STATUS_BASTER = register("status_baster", new StatusBasterItem(new Item.Properties().durability(1)));
    public static final Item SMOOTHIE = register("smoothie", new SmoothieItem(new Item.Properties().stacksTo(16)));
    public static final Item BLENDER = register("blender", new BlenderBlockItem(BlockRegistry.BLENDER, new Item.Properties().stacksTo(1)));
    public static final Item JUICER = register("juicer", new Item(new Item.Properties().stacksTo(1)));

    public static void register() {
        SmoothieItemColors.init();
    }

    private static <T extends Item> T register(String id, T item) {
        return Registry.register(BuiltInRegistries.ITEM, Constants.id(id), item);
    }
}
