package me.ultrusmods.culinarychemistry.data;

import me.ultrusmods.culinarychemistry.register.BlockRegistry;
import me.ultrusmods.culinarychemistry.register.ItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class CulinaryChemistryModelProvider extends FabricModelProvider {

    public CulinaryChemistryModelProvider(FabricDataOutput output) {
        super(output);
    }


    public final void createBlenders(BlockModelGenerators generators, Block... blocks) {
        for(Block block : blocks) {
            ResourceLocation resourceLocation = CulinaryChemistryModelTemplates.BLENDER_MODEL_TEMPLATE.create(block, TextureMapping.cube(block), generators.modelOutput);
            generators.createSimpleFlatItemModel(block.asItem());
            generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, resourceLocation)));
        }

    }
    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        createBlenders(blockStateModelGenerator, BlockRegistry.BLENDER);

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ItemRegistry.STATUS_BASTER, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.JUICER, ModelTemplates.FLAT_ITEM);

    }
}
