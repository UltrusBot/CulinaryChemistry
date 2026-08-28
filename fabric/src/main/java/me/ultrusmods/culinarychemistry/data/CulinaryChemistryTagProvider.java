package me.ultrusmods.culinarychemistry.data;

import me.ultrusmods.culinarychemistry.tag.CulinaryChemistryTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class CulinaryChemistryTagProvider extends FabricTagProvider<Item> {
    public CulinaryChemistryTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(CulinaryChemistryTags.CC_FOUL)
                .add(Items.ROTTEN_FLESH)
                .add(Items.SPIDER_EYE)
                .add(Items.POISONOUS_POTATO)
                .add(Items.CHICKEN)
                .add(Items.PUFFERFISH);

        getOrCreateTagBuilder(CulinaryChemistryTags.CC_SPECIAL_INGREDIENTS)
                .add(Items.GUNPOWDER)
                .add(Items.BLAZE_POWDER)
                .add(Items.FIRE_CHARGE);

    }
}
