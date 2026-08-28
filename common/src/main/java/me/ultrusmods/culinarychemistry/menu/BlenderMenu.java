package me.ultrusmods.culinarychemistry.menu;

import me.ultrusmods.culinarychemistry.block.entity.BlenderBlockEntity;
import me.ultrusmods.culinarychemistry.register.MenuRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BlenderMenu extends AbstractContainerMenu {
    private final Container blender;
    private final ContainerData blendData;

    public BlenderMenu(int id, Inventory inventory) {
        this(id, inventory, new SimpleContainer(6), new SimpleContainerData(2));
    }

    public BlenderMenu(int id, Inventory inventory, Container blender, ContainerData blendData) {
        super(MenuRegistry.BLENDER, id);
        checkContainerSize(blender, 6);
        checkContainerDataCount(blendData, 2);
        this.blender = blender;
        this.blendData = blendData;
        blender.startOpen(inventory.player);

        addSlot(new IngredientSlot(blender, 0, 23, 31));
        addSlot(new IngredientSlot(blender, 1, 41, 31));
        addSlot(new IngredientSlot(blender, 2, 59, 31));
        addSlot(new BottleSlot(blender, 4, 89, 31));
        addSlot(new ExtraSlot(blender, 3, 107, 31));
        addSlot(new OutputSlot(blender, 5, 137, 31));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
        addDataSlots(blendData);
    }

    public int getBlendProgress(int width) {
        int progress = blendData.get(0);
        int total = blendData.get(1);
        return progress > 0 && total > 0 ? progress * width / total : 0;
    }

    @Override public boolean stillValid(Player player) { return blender.stillValid(player); }
    @Override public void removed(Player player) { super.removed(player); blender.stopOpen(player); }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        if (index < 6) {
            if (!moveItemStackTo(stack, 6, slots.size(), true)) return ItemStack.EMPTY;
        } else if (BlenderBlockEntity.isIngredient(stack)) {
            if (!moveItemStackTo(stack, 0, 3, false)) return ItemStack.EMPTY;
        } else if (BlenderBlockEntity.isExtra(stack)) {
            if (!moveItemStackTo(stack, 3, 4, false)) return ItemStack.EMPTY;
        } else if (stack.is(Items.GLASS_BOTTLE)) {
            if (!moveItemStackTo(stack, 4, 5, false)) return ItemStack.EMPTY;
        } else return ItemStack.EMPTY;
        if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY); else slot.setChanged();
        return copy;
    }

    private static class IngredientSlot extends Slot {
        IngredientSlot(Container c, int i, int x, int y) { super(c, i, x, y); }
        @Override public boolean mayPlace(ItemStack stack) {
            return BlenderBlockEntity.isIngredient(stack);
        }
     }
    private static class ExtraSlot extends Slot {
        ExtraSlot(Container c, int i, int x, int y) { super(c, i, x, y); }
        @Override public boolean mayPlace(ItemStack stack) {
            return BlenderBlockEntity.isExtra(stack);
        }
    }
    private static class BottleSlot extends Slot {
        BottleSlot(Container c, int i, int x, int y) { super(c, i, x, y); }
        @Override public boolean mayPlace(ItemStack stack) { return stack.is(Items.GLASS_BOTTLE); }
    }
    private static class OutputSlot extends Slot {
        OutputSlot(Container c, int i, int x, int y) { super(c, i, x, y); }
        @Override public boolean mayPlace(ItemStack stack) { return false; }
    }
}
