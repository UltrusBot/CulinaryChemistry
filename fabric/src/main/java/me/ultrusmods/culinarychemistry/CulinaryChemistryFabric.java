package me.ultrusmods.culinarychemistry;

import me.ultrusmods.culinarychemistry.register.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class CulinaryChemistryFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        CulinaryChemistryRegistries.init();
        DataComponentRegistry.register();
        BlockRegistry.register();
        ItemRegistry.register();
        RecipeRegistry.register();
        BlockEntityRegistry.register();
        MenuRegistry.register();
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register(entries -> {
                    entries.accept(ItemRegistry.STATUS_BASTER);
                    entries.accept(ItemRegistry.BLENDER);
                    entries.accept(ItemRegistry.JUICER);
                });
    }
}
