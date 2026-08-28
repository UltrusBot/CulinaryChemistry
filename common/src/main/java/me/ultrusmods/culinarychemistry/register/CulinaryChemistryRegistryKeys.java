package me.ultrusmods.culinarychemistry.register;

import me.ultrusmods.culinarychemistry.Constants;
import me.ultrusmods.culinarychemistry.item.smoothie.SmoothieEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class CulinaryChemistryRegistryKeys {

    public static final ResourceKey<Registry<SmoothieEffect>> SMOOTHIE_EFFECTS =
            ResourceKey.createRegistryKey(Constants.id("smoothie_effects"));
}
