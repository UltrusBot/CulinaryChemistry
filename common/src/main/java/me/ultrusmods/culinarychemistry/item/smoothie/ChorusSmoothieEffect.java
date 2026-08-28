package me.ultrusmods.culinarychemistry.item.smoothie;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ChorusSmoothieEffect extends SmoothieEffect {

    @Override
    public void applyEffect(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            for(int i = 0; i < 16; ++i) {
                double d0 = consumer.getX() + (consumer.getRandom().nextDouble() - (double)0.5F) * (double)16.0F;
                double d1 = Mth.clamp(consumer.getY() + (double)(consumer.getRandom().nextInt(16) - 8), level.getMinBuildHeight(), level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1);
                double d2 = consumer.getZ() + (consumer.getRandom().nextDouble() - (double)0.5F) * (double)16.0F;
                if (consumer.isPassenger()) {
                    consumer.stopRiding();
                }

                Vec3 vec3 = consumer.position();
                if (consumer.randomTeleport(d0, d1, d2, true)) {
                    level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(consumer));
                    SoundSource soundsource;
                    SoundEvent soundevent;
                    if (consumer instanceof Fox) {
                        soundevent = SoundEvents.FOX_TELEPORT;
                        soundsource = SoundSource.NEUTRAL;
                    } else {
                        soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        soundsource = SoundSource.PLAYERS;
                    }

                    level.playSound(null, consumer.getX(), consumer.getY(), consumer.getZ(), soundevent, soundsource);
                    consumer.resetFallDistance();
                    break;
                }
            }

            if (consumer instanceof Player player) {
                player.resetCurrentImpulseContext();
                player.getCooldowns().addCooldown(stack.getItem(), 20);
            }
        }
    }

    @Override
    public boolean isValidIngredient(ItemStack stack) {
        return stack.is(Items.CHORUS_FRUIT);
    }
}
