package me.ultrusmods.culinarychemistry.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CulinaryChemistryDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(CulinaryChemistryRecipeProvider::new);
        pack.addProvider(CulinaryChemistryEffectTagProvider::new);
        pack.addProvider(CulinaryChemistryModelProvider::new);
        pack.addProvider(CulinaryChemistryTagProvider::new);
    }
}
