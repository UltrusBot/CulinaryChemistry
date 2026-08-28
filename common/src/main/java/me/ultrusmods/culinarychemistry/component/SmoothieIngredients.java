package me.ultrusmods.culinarychemistry.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.ultrusmods.culinarychemistry.item.smoothie.SmoothieEffect;
import net.minecraft.core.Holder;

import java.util.List;

public record SmoothieIngredients(List<Holder<SmoothieEffect>> effects, int color) {
    public static final Codec<SmoothieIngredients> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SmoothieEffect.CODEC.listOf().optionalFieldOf("effects", List.of()).forGetter(SmoothieIngredients::effects),
            Codec.INT.fieldOf("color").forGetter(SmoothieIngredients::color)
    ).apply(instance, SmoothieIngredients::new));

    public SmoothieIngredients {
        effects = List.copyOf(effects);
    }
}
