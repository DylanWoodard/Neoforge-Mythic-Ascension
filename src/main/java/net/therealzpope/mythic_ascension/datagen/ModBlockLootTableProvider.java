package net.therealzpope.mythic_ascension.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.therealzpope.mythic_ascension.block.ModBlocks;
import net.therealzpope.mythic_ascension.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        dropSelf(ModBlocks.RITUAL_BOWL.get());

        dropSelf(ModBlocks.TRANSCRIPTION_TABLE.get());

        dropSelf(ModBlocks.MITHRAL_BLOCK.get());
        add(ModBlocks.MITHRAL_ORE.get(),
                block -> createOreDrop(ModBlocks.MITHRAL_ORE.get(), ModItems.RAW_MITHRAL.get()));
        add(ModBlocks.MITHRAL_DEEPSLATE_ORE.get(),
                block -> createOreDrop(ModBlocks.MITHRAL_DEEPSLATE_ORE.get(), ModItems.RAW_MITHRAL.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
