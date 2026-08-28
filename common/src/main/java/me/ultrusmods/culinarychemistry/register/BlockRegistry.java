package me.ultrusmods.culinarychemistry.register;

import me.ultrusmods.culinarychemistry.Constants;
import me.ultrusmods.culinarychemistry.block.BlenderBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public final class BlockRegistry {
    public static final Block BLENDER = register("blender",
            new BlenderBlock(Block.Properties.of().strength(2.5F).sound(SoundType.METAL).noOcclusion()));
    
    public static void register() {

    }

    private static <T extends Block> T register(String id, T block) {
        return Registry.register(BuiltInRegistries.BLOCK, Constants.id(id), block);
    }
}
