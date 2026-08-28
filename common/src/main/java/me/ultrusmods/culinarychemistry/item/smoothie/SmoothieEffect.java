package me.ultrusmods.culinarychemistry.item.smoothie;

import com.mojang.serialization.Codec;
import me.ultrusmods.culinarychemistry.register.CulinaryChemistryRegistries;
import me.ultrusmods.culinarychemistry.register.CulinaryChemistryRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SmoothieEffect {

    public static Codec<Holder<SmoothieEffect>> CODEC = CulinaryChemistryRegistries.SMOOTHIE_EFFECTS.holderByNameCodec();
    public static StreamCodec<RegistryFriendlyByteBuf, Holder<SmoothieEffect>> STREAM_CODEC = ByteBufCodecs.holderRegistry(CulinaryChemistryRegistryKeys.SMOOTHIE_EFFECTS);

    public void applyEffect(ItemStack stack, Level level, LivingEntity consumer) {

    }

    /**
     * Checks if an ingredient will give this effect
     *
     * @param stack The ingredient to check
     * @return True if this effect can come from this item
     */
    public boolean isValidIngredient(ItemStack stack) {
        return false;
    }
}
