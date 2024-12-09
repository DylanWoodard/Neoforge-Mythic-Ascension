package net.therealzpope.mythic_ascension.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.block.ModBlocks;
import net.therealzpope.mythic_ascension.block.entity.custom.RitualBowlBlockEntity;
import net.therealzpope.mythic_ascension.block.entity.custom.TestChalkCircleBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MythicAscension.MOD_ID);

    public static final Supplier<BlockEntityType<RitualBowlBlockEntity>> RITUAL_BOWL_BE =
            BLOCK_ENTITIES.register("ritual_bowl_be", () -> BlockEntityType.Builder.of(
                    RitualBowlBlockEntity::new, ModBlocks.RITUAL_BOWL.get()).build(null));

    public static final Supplier<BlockEntityType<TestChalkCircleBlockEntity>> TEST_CHALK_CIRCLE_BE =
            BLOCK_ENTITIES.register("test_chalk_circle_be", () -> BlockEntityType.Builder.of(
                    TestChalkCircleBlockEntity::new, ModBlocks.TEST_CHALK_CIRCLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
