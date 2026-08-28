package me.ultrusmods.culinarychemistry.item;

import me.ultrusmods.culinarychemistry.component.SmoothieIngredients;
import me.ultrusmods.culinarychemistry.register.DataComponentRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class SmoothieItem extends Item {
    public SmoothieItem(Properties properties) {
        super(properties);
    }

    public static int getColor(ItemStack stack, int tintIndex) {
        if (tintIndex != 0) return 0xFFFFFFFF;
        SmoothieIngredients ingredients = stack.get(DataComponentRegistry.SMOOTHIE_INGREDIENTS);
        int color = ingredients == null ? SmoothieItemColors.DEFAULT_COLOR : ingredients.color();
        return 0xFF000000 | color;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        Player player = consumer instanceof Player consumingPlayer ? consumingPlayer : null;
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (!level.isClientSide && contents != null) {
            contents.forEachEffect(effect -> consumer.addEffect(new MobEffectInstance(effect)));
        }
        SmoothieIngredients ingredients = stack.get(DataComponentRegistry.SMOOTHIE_INGREDIENTS);
        if (ingredients != null) {
            ingredients.effects().forEach(effect -> effect.value().applyEffect(stack, level, consumer));
        }

        ItemStack remainingStack = super.finishUsingItem(stack, level, consumer);
        if (player == null || !player.hasInfiniteMaterials()) {
            if (remainingStack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
            if (player != null) {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        consumer.gameEvent(GameEvent.DRINK);
        return remainingStack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (contents != null && contents.hasEffects()) {
            contents.addPotionTooltip(tooltip::add, 1.0F, context.tickRate());
        }
    }
}
