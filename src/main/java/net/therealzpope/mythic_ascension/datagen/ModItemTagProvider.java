package net.therealzpope.mythic_ascension.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.item.ModItems;
import net.therealzpope.mythic_ascension.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, MythicAscension.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.BOOKSHELF_BOOKS)
                .add(ModItems.SPELLBOOK.get());

        tag(ModTags.Items.SHARP_IMPLEMENTS)
                .add(Items.WOODEN_SWORD)
                .add(Items.WOODEN_AXE)
                .add(Items.STONE_SWORD)
                .add(Items.STONE_AXE)
                .add(Items.IRON_SWORD)
                .add(Items.IRON_AXE)
                .add(Items.GOLDEN_SWORD)
                .add(Items.GOLDEN_AXE)
                .add(Items.DIAMOND_SWORD)
                .add(Items.DIAMOND_AXE)
                .add(Items.NETHERITE_SWORD)
                .add(Items.NETHERITE_AXE)
                .add(Items.TRIDENT);
    }
}
