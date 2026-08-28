package me.ultrusmods.culinarychemistry;


import me.ultrusmods.culinarychemistry.register.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class CulinaryChemistryNeoForge {

    public CulinaryChemistryNeoForge(IEventBus eventBus) {
        eventBus.addListener(this::registerRegistries);
        CulinaryChemistry.init();
        eventBus.addListener(this::addCreativeTabContents);
        eventBus.addListener(this::registerContent);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            CulinaryChemistryNeoForgeClient.register(eventBus);
        }
    }

    private void registerRegistries(NewRegistryEvent event) {
        event.register(CulinaryChemistryRegistries.SMOOTHIE_EFFECTS);
    }

    private void addCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ItemRegistry.STATUS_BASTER);
            event.accept(ItemRegistry.BLENDER);
            event.accept(ItemRegistry.JUICER);
        }

    }

    private void registerContent(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.DATA_COMPONENT_TYPE) {
            DataComponentRegistry.register();
        }
        if (event.getRegistryKey() == Registries.BLOCK) {
            BlockRegistry.register();
        }
        if (event.getRegistryKey() == Registries.ITEM) {
            ItemRegistry.register();
        }
        if (event.getRegistryKey() == Registries.RECIPE_TYPE) {
            RecipeRegistry.register();
        }
        if (event.getRegistryKey() == Registries.BLOCK_ENTITY_TYPE) {
            BlockEntityRegistry.register();
        }
        if (event.getRegistryKey() == Registries.MENU) {
            MenuRegistry.register();
        }
    }
}
