package me.ultrusmods.culinarychemistry.register;

import me.ultrusmods.culinarychemistry.Constants;
import me.ultrusmods.culinarychemistry.item.smoothie.ChorusSmoothieEffect;
import me.ultrusmods.culinarychemistry.item.smoothie.FireSmoothieEffect;
import me.ultrusmods.culinarychemistry.item.smoothie.GunpowderSmoothieEffect;
import me.ultrusmods.culinarychemistry.item.smoothie.SmoothieEffect;
import me.ultrusmods.culinarychemistry.platform.Services;
import net.minecraft.core.Registry;

public class CulinaryChemistryRegistries {
    public static final Registry<SmoothieEffect> SMOOTHIE_EFFECTS = Services.PLATFORM.createSimpleRegistry(CulinaryChemistryRegistryKeys.SMOOTHIE_EFFECTS);
    public static final SmoothieEffect CHORUS = register("chorus", new ChorusSmoothieEffect());
    public static final SmoothieEffect GUNPOWDER = register("gunpowder", new GunpowderSmoothieEffect());
    public static final SmoothieEffect FLAMING = register("flaming", new FireSmoothieEffect());


    public static void init() {

    }

    private static SmoothieEffect register(String id, SmoothieEffect effect) {
        return Registry.register(SMOOTHIE_EFFECTS, Constants.id(id), effect);
    }
}
