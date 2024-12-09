package net.therealzpope.mythic_ascension.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider{
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MythicAscension.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.MITHRAL_BLOCK.get())
                .add(ModBlocks.MITHRAL_DEEPSLATE_ORE.get())
                .add(ModBlocks.MITHRAL_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.MITHRAL_ORE.get())
                .add(ModBlocks.MITHRAL_DEEPSLATE_ORE.get());
    }
}
