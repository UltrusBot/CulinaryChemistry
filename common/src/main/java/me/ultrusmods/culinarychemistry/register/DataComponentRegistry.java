package me.ultrusmods.culinarychemistry.register;

import me.ultrusmods.culinarychemistry.Constants;
import me.ultrusmods.culinarychemistry.component.SmoothieIngredients;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

public final class DataComponentRegistry {
    public static final DataComponentType<SmoothieIngredients> SMOOTHIE_INGREDIENTS = register("smoothie_ingredients",
            DataComponentType.<SmoothieIngredients>builder()
                    .persistent(SmoothieIngredients.CODEC)
                    .networkSynchronized(ByteBufCodecs.fromCodecWithRegistries(SmoothieIngredients.CODEC))
                    .cacheEncoding()
                    .build());

    public static void register() {

    }

    private static <T> DataComponentType<T> register(String id, DataComponentType<T> component) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id(id), component);
    }
}
