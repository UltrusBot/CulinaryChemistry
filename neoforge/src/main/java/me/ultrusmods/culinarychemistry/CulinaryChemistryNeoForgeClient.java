package me.ultrusmods.culinarychemistry;

import me.ultrusmods.culinarychemistry.client.BlenderScreen;
import me.ultrusmods.culinarychemistry.item.SmoothieItem;
import me.ultrusmods.culinarychemistry.register.BlockRegistry;
import me.ultrusmods.culinarychemistry.register.ItemRegistry;
import me.ultrusmods.culinarychemistry.register.MenuRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public final class CulinaryChemistryNeoForgeClient {
    private CulinaryChemistryNeoForgeClient() {
    }

    public static void register(IEventBus eventBus) {
        eventBus.addListener(CulinaryChemistryNeoForgeClient::registerItemColors);
        eventBus.addListener(CulinaryChemistryNeoForgeClient::registerScreens);
        eventBus.addListener(CulinaryChemistryNeoForgeClient::onInitalizeClient);
    }

    private static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(SmoothieItem::getColor, ItemRegistry.SMOOTHIE);
    }

    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuRegistry.BLENDER, BlenderScreen::new);
    }

    private static void onInitalizeClient(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.BLENDER, RenderType.cutoutMipped());
    }
}
