package me.ultrusmods.culinarychemistry.item;

import me.ultrusmods.culinarychemistry.tag.CulinaryChemistryTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatusBasterItem extends Item {
    public StatusBasterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack baster = player.getItemInHand(hand);
        List<MobEffectInstance> effects = player.getActiveEffects().stream()
                .filter(effect -> !effect.getEffect().is(CulinaryChemistryTags.BLACKLISTED_EFFECTS))
                .map(MobEffectInstance::new)
                .toList();

        if (effects.isEmpty() || !hasBottle(player)) {
            return InteractionResultHolder.fail(baster);
        }

        if (!level.isClientSide) {
            consumeBottle(player);
            ItemStack potion = new ItemStack(Items.POTION);
            potion.set(DataComponents.POTION_CONTENTS, new PotionContents(
                    Optional.empty(), Optional.of(mixColor(effects)), new ArrayList<>(effects)
            ));
            potion.set(DataComponents.ITEM_NAME, Component.translatable("item.culinarychemistry.extracted_potion"));
            if (!player.getInventory().add(potion)) {
                player.drop(potion, false);
            }

            effects.forEach(effect -> player.removeEffect(effect.getEffect()));
            player.hurt(player.damageSources().magic(), player.getMaxHealth() * 0.5F);
            baster.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND
                    ? net.minecraft.world.entity.EquipmentSlot.MAINHAND
                    : net.minecraft.world.entity.EquipmentSlot.OFFHAND);
        }

        return InteractionResultHolder.sidedSuccess(baster, level.isClientSide);
    }

    private static boolean hasBottle(Player player) {
        return player.getAbilities().instabuild || player.getInventory().contains(new ItemStack(Items.GLASS_BOTTLE));
    }

    private static void consumeBottle(Player player) {
        if (player.getAbilities().instabuild) return;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(Items.GLASS_BOTTLE)) {
                stack.shrink(1);
                return;
            }
        }
        for (ItemStack stack : player.getInventory().offhand) {
            if (stack.is(Items.GLASS_BOTTLE)) {
                stack.shrink(1);
                return;
            }
        }
    }

    private static int mixColor(List<MobEffectInstance> effects) {
        int red = 0;
        int green = 0;
        int blue = 0;
        int weight = 0;
        for (MobEffectInstance instance : effects) {
            int color = instance.getEffect().value().getColor();
            int effectWeight = instance.getAmplifier() + 1;
            red += effectWeight * (color >> 16 & 255);
            green += effectWeight * (color >> 8 & 255);
            blue += effectWeight * (color & 255);
            weight += effectWeight;
        }
        return weight == 0 ? 0x385DC6 : (red / weight << 16) | (green / weight << 8) | blue / weight;
    }
}
