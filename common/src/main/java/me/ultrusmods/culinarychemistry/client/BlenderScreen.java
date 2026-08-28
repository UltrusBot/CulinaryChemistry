package me.ultrusmods.culinarychemistry.client;

import me.ultrusmods.culinarychemistry.Constants;
import me.ultrusmods.culinarychemistry.menu.BlenderMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BlenderScreen extends AbstractContainerScreen<BlenderMenu> {
    private static final ResourceLocation BLENDER = Constants.id("textures/gui/container/blender.png");
    private static final ResourceLocation BLENDER_FILLED_BAR =  Constants.id("container/blender_filled_bar");

    public BlenderScreen(BlenderMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int xSize = (this.width - this.imageWidth) / 2;
        int ySize = (this.height - this.imageHeight) / 2;

        graphics.blit(BLENDER, xSize, ySize, 0, 0, this.imageWidth, this.imageHeight);
        int progress = menu.getBlendProgress(76);
        if (progress > 0) {
            graphics.blitSprite(BLENDER_FILLED_BAR, 76, 5, 0, 0, xSize + 51, ySize + 52, progress, 5);

        }
    }

    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
