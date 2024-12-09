package net.therealzpope.mythic_ascension.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MythicAscension.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.RAW_MITHRAL.get());
        basicItem(ModItems.MITHRAL_INGOT.get());

        basicItem(ModItems.CHALK_DUST.get());

        basicItem(ModItems.SILVER_INGOT.get());

        basicItem(ModItems.SILVER_BELL.get());
    }
}