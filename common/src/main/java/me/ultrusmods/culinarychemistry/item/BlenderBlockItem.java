package me.ultrusmods.culinarychemistry.item;

import me.ultrusmods.culinarychemistry.block.entity.BlenderBlockEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlenderBlockItem extends BlockItem {
    public BlenderBlockItem(Block block, Properties properties) {
        super(block, properties.component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY));
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack blender, ItemStack carried, Slot slot, ClickAction action,
                                             Player player, SlotAccess carriedAccess) {
        if (action != ClickAction.SECONDARY) return false;
        BundleContents contents = blender.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);

        if (carried.is(Items.GLASS_BOTTLE)) {
            List<ItemStack> ingredients = contents.itemCopyStream().filter(BlenderBlockEntity::isIngredient).toList();
            if (ingredients.isEmpty()) return false;
            ItemStack extra = contents.itemCopyStream().filter(BlenderBlockEntity::isExtra)
                    .findFirst().orElse(ItemStack.EMPTY);
            ItemStack smoothie = SmoothieMaker.make(ingredients, extra, player.level());
            long returnedBottles = ingredients.stream().filter(BlenderBlockItem::isBottledPotion).count();
            blender.set(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
            if (carried.getCount() == 1) carriedAccess.set(smoothie);
            else {
                carried.shrink(1);
                if (!player.getInventory().add(smoothie)) player.drop(smoothie, false);
            }
            if (returnedBottles > 0) {
                ItemStack bottles = new ItemStack(Items.GLASS_BOTTLE, (int) returnedBottles);
                if (!player.getInventory().add(bottles)) player.drop(bottles, false);
            }
            return true;
        }

        boolean ingredient = BlenderBlockEntity.isIngredient(carried);
        boolean extra = BlenderBlockEntity.isExtra(carried);
        if (!ingredient && !extra) return false;
        long ingredientCount = contents.itemCopyStream().filter(BlenderBlockEntity::isIngredient).count();
        boolean hasExtra = contents.itemCopyStream().anyMatch(BlenderBlockEntity::isExtra);
        if ((ingredient && ingredientCount >= 3) || (extra && hasExtra)) return true;

        List<ItemStack> stored = new ArrayList<>();
        contents.itemCopyStream().forEach(stored::add);
        stored.add(carried.copyWithCount(1));
        blender.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(stored));
        carried.shrink(1);
        return true;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack blender, Slot slot, ClickAction action, Player player) {
        if (action != ClickAction.SECONDARY || slot.hasItem()) return false;
        BundleContents contents = blender.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        List<ItemStack> stored = new ArrayList<>();
        contents.itemCopyStream().forEach(stored::add);
        if (stored.isEmpty()) return false;
        ItemStack removed = stored.remove(stored.size() - 1);
        if (!slot.mayPlace(removed)) return false;
        slot.set(removed);
        blender.set(DataComponents.BUNDLE_CONTENTS, stored.isEmpty() ? BundleContents.EMPTY : new BundleContents(stored));
        return true;
    }

    private static boolean isBottledPotion(ItemStack stack) {
        return stack.is(Items.POTION) || stack.is(Items.SPLASH_POTION) || stack.is(Items.LINGERING_POTION);
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        BundleContents contents = stack.get(DataComponents.BUNDLE_CONTENTS);
        return contents == null || contents.isEmpty() ? Optional.empty() : Optional.of(new BundleTooltip(contents));
    }

    @Override public boolean isBarVisible(ItemStack stack) { return !stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY).isEmpty(); }
    @Override public int getBarWidth(ItemStack stack) {
        int size = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY).size();
        return Math.min(13, 1 + size * 3);
    }
    @Override public int getBarColor(ItemStack stack) { return 0xE6A83A; }
}
