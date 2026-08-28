package me.ultrusmods.culinarychemistry.data;

import me.ultrusmods.culinarychemistry.Constants;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class CulinaryChemistryModelTemplates {

    public static ModelTemplate BLENDER_MODEL_TEMPLATE = create("blender_template", TextureSlot.ALL);

    private static ModelTemplate create(String blockModelLocation, TextureSlot... requiredSlots) {
        return new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + blockModelLocation)), Optional.empty(), requiredSlots);
    }
}
