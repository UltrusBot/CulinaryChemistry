package me.ultrusmods.culinarychemistry;

import me.ultrusmods.culinarychemistry.client.BlenderScreen;
import me.ultrusmods.culinarychemistry.item.SmoothieItem;
import me.ultrusmods.culinarychemistry.register.BlockRegistry;
import me.ultrusmods.culinarychemistry.register.ItemRegistry;
import me.ultrusmods.culinarychemistry.register.MenuRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

public class CulinaryChemistryFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register(SmoothieItem::getColor, ItemRegistry.SMOOTHIE);
        MenuScreens.register(MenuRegistry.BLENDER, BlenderScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BLENDER, RenderType.cutoutMipped());

    }
}
