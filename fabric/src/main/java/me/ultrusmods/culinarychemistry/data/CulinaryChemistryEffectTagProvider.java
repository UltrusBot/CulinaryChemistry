package me.ultrusmods.culinarychemistry.data;

import me.ultrusmods.culinarychemistry.tag.CulinaryChemistryTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.concurrent.CompletableFuture;

public class CulinaryChemistryEffectTagProvider extends FabricTagProvider<MobEffect> {
    public CulinaryChemistryEffectTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.MOB_EFFECT, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateTagBuilder(CulinaryChemistryTags.BLACKLISTED_EFFECTS)
                .add(MobEffects.BAD_OMEN.value())
                .add(MobEffects.RAID_OMEN.value())
                .add(MobEffects.TRIAL_OMEN.value());
    }
}
