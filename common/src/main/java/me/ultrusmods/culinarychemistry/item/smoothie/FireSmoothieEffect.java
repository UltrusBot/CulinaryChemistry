package me.ultrusmods.culinarychemistry.item.smoothie;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FireSmoothieEffect extends SmoothieEffect {

    @Override
    public void applyEffect(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            consumer.setRemainingFireTicks(20);
        }
    }

    @Override
    public boolean isValidIngredient(ItemStack stack) {
        return stack.is(Items.FIRE_CHARGE) || stack.is(Items.BLAZE_POWDER);
    }
}
