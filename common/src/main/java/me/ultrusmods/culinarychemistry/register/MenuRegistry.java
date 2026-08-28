package me.ultrusmods.culinarychemistry.register;

import me.ultrusmods.culinarychemistry.Constants;
import me.ultrusmods.culinarychemistry.menu.BlenderMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public final class MenuRegistry {
    public static final MenuType<BlenderMenu> BLENDER = register("blender", new MenuType<>(BlenderMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void register() {
        
    }

    private static <T extends MenuType<?>> T register(String id, T type) {
        return Registry.register(BuiltInRegistries.MENU, Constants.id(id), type);
    }
}
