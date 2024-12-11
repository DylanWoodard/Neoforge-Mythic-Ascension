package net.therealzpope.mythic_ascension.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.therealzpope.mythic_ascension.MythicAscension;

public class ModTags {
    public static class Blocks {
        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> SHARP_IMPLEMENTS = createTag("sharp_implements");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, name));
        }
    }
}
