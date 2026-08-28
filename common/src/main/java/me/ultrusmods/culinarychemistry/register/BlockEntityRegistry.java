package me.ultrusmods.culinarychemistry.register;

import me.ultrusmods.culinarychemistry.Constants;
import me.ultrusmods.culinarychemistry.block.entity.BlenderBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class BlockEntityRegistry {
    public static final BlockEntityType<BlenderBlockEntity> BLENDER = register("blender",
            BlockEntityType.Builder.of(BlenderBlockEntity::new, BlockRegistry.BLENDER).build(null));

    public static void register() {}

    private static <T extends BlockEntityType<?>> T register(String id, T type) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.id(id), type);
    }
}
