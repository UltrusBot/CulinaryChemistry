package me.ultrusmods.culinarychemistry.block.entity;

import me.ultrusmods.culinarychemistry.item.SmoothieMaker;
import me.ultrusmods.culinarychemistry.menu.BlenderMenu;
import me.ultrusmods.culinarychemistry.register.BlockEntityRegistry;
import me.ultrusmods.culinarychemistry.register.ItemRegistry;
import me.ultrusmods.culinarychemistry.tag.CulinaryChemistryTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class BlenderBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    public static final int BLEND_TIME = 160;
    public static final int[] TOP_SLOTS = {0, 1, 2};
    public static final int[] SIDE_SLOTS = {3, 4};
    public static final int[] BOTTOM_SLOTS = {5};
    private NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
    private final NonNullList<ItemStack> previousInputs = NonNullList.withSize(5, ItemStack.EMPTY);
    private int blendProgress;
    private final ContainerData blendData = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> blendProgress;
                case 1 -> BLEND_TIME;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) blendProgress = value;
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public BlenderBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.BLENDER, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BlenderBlockEntity blender) {
        if (blender.inputsChanged()) {
            blender.blendProgress = 0;
            blender.rememberInputs();
        }

        if (!blender.canBlend()) {
            blender.blendProgress = 0;
            return;
        }

        if (++blender.blendProgress >= BLEND_TIME) {
            blender.blend();
            blender.blendProgress = 0;
            blender.rememberInputs();
        }
    }

    private boolean canBlend() {
        List<ItemStack> ingredients = getIngredients();
        if (ingredients.isEmpty() || ingredients.stream().anyMatch(stack -> !isIngredient(stack))) return false;
        if (!items.get(3).isEmpty() && !isExtra(items.get(3))) return false;

        int potionBottles = (int) ingredients.stream().filter(BlenderBlockEntity::isBottledPotion).count();
        if (potionBottles == 0 && !items.get(4).is(Items.GLASS_BOTTLE)) return false;
        int leftoverBottles = Math.max(0, potionBottles - 1);
        ItemStack bottleSlot = items.get(4);
        if (leftoverBottles > 0 && !bottleSlot.isEmpty() && !bottleSlot.is(Items.GLASS_BOTTLE)) return false;
        if (leftoverBottles > 0 && bottleSlot.getCount() + leftoverBottles > Items.GLASS_BOTTLE.getDefaultMaxStackSize()) {
            return false;
        }

        ItemStack output = items.get(5);
        if (output.isEmpty()) return true;
        ItemStack result = makeResult(ingredients);
        return ItemStack.isSameItemSameComponents(output, result)
                && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void blend() {
        if (!canBlend()) return;
        List<ItemStack> ingredients = getIngredients();
        int potionBottles = (int) ingredients.stream().filter(BlenderBlockEntity::isBottledPotion).count();
        int leftoverBottles = Math.max(0, potionBottles - 1);

        ItemStack result = makeResult(ingredients);

        for (int i = 0; i < 3; i++) if (!items.get(i).isEmpty()) items.get(i).shrink(1);
        if (potionBottles == 0) {
            items.get(4).shrink(1);
        } else if (leftoverBottles > 0) {
            if (items.get(4).isEmpty()) items.set(4, new ItemStack(Items.GLASS_BOTTLE, leftoverBottles));
            else items.get(4).grow(leftoverBottles);
        }
        if (!items.get(3).isEmpty()) items.get(3).shrink(1);
        if (items.get(5).isEmpty()) {
            items.set(5, result);
        } else {
            items.get(5).grow(result.getCount());
        }
        setChanged();
    }

    private ItemStack makeResult(List<ItemStack> ingredients) {
        return SmoothieMaker.make(ingredients, items.get(3), level);
    }

    private List<ItemStack> getIngredients() {
        List<ItemStack> ingredients = new ArrayList<>();
        for (int i = 0; i < 3; i++) if (!items.get(i).isEmpty()) ingredients.add(items.get(i));
        return ingredients;
    }

    private boolean inputsChanged() {
        for (int i = 0; i < previousInputs.size(); i++) {
            ItemStack current = items.get(i);
            ItemStack previous = previousInputs.get(i);
            if (current.getCount() != previous.getCount() || !ItemStack.isSameItemSameComponents(current, previous))
                return true;
        }
        return false;
    }

    private void rememberInputs() {
        for (int i = 0; i < previousInputs.size(); i++) previousInputs.set(i, items.get(i).copy());
    }

    public static boolean isIngredient(ItemStack stack) {
        return !stack.is(ItemRegistry.SMOOTHIE) && (stack.has(DataComponents.FOOD) || isBottledPotion(stack));
    }

    public static boolean isExtra(ItemStack stack) {
        return stack.getItem() instanceof DyeItem || stack.is(CulinaryChemistryTags.CC_SPECIAL_INGREDIENTS);
    }

    private static boolean isBottledPotion(ItemStack stack) {
        return stack.is(Items.POTION) || stack.is(Items.SPLASH_POTION) || stack.is(Items.LINGERING_POTION);
    }

    public void dropContents(Level level, BlockPos pos) {
        Containers.dropContents(level, pos, this);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.culinarychemistry.blender");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int getContainerSize() {
        return 6;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new BlenderMenu(id, inventory, this, blendData);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(6, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot < 3) return isIngredient(stack);
        if (slot == 3) return isExtra(stack);
        return slot == 4 && stack.is(Items.GLASS_BOTTLE);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return side == Direction.UP ? TOP_SLOTS : side == Direction.DOWN ? BOTTOM_SLOTS : SIDE_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction side) {
        return canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction side) {
        return side == Direction.DOWN && slot == 5;
    }
}
