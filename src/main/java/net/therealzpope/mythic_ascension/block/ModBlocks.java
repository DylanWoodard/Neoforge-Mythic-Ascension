package net.therealzpope.mythic_ascension.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.block.custom.RitualBowlBlock;
import net.therealzpope.mythic_ascension.block.custom.TestChalkCircleBlock;
import net.therealzpope.mythic_ascension.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MythicAscension.MOD_ID);

    public static final DeferredBlock<Block> MITHRAL_BLOCK = registerBlock("mithral_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> MITHRAL_ORE = registerBlock("mithral_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MITHRAL_DEEPSLATE_ORE = registerBlock("mithral_deepslate_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of()
                            .strength(4f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> RITUAL_BOWL = registerBlock("ritual_bowl",
            () -> new RitualBowlBlock(BlockBehaviour.Properties.of()
                    .strength(4f)
                    .noOcclusion()));

    public static final DeferredBlock<Block> TEST_CHALK_CIRCLE = registerBlock("test_chalk_circle",
            () -> new TestChalkCircleBlock(BlockBehaviour.Properties.of()
                    .strength(4f)
                    .noLootTable()
                    .noCollission()
                    .noOcclusion()));

    public static final DeferredBlock<Block> TRANSCRIPTION_TABLE = registerBlock("transcription_table",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f)
                    .noOcclusion()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
