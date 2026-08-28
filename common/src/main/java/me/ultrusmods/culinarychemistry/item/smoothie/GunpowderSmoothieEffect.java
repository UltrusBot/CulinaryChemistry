package me.ultrusmods.culinarychemistry.item.smoothie;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class GunpowderSmoothieEffect extends SmoothieEffect {

    @Override
    public void applyEffect(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            level.explode(consumer, consumer.getX(), consumer.getY(), consumer.getZ(), 0.5F, Level.ExplosionInteraction.MOB);
        }
    }

    @Override
    public boolean isValidIngredient(ItemStack stack) {
        return stack.is(Items.GUNPOWDER);
    }
}
