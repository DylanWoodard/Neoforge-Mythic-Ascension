package net.therealzpope.mythic_ascension.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.block.ModBlocks;
import net.therealzpope.mythic_ascension.sound.ModSounds;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, MythicAscension.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, MythicAscension.MOD_ID);

    public static final Holder<PoiType> OCCULT_POI = POI_TYPES.register("occult_poi",
            () -> new PoiType(ImmutableSet.copyOf(Blocks.CHISELED_BOOKSHELF.getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final Holder<VillagerProfession> OCCULTIST = VILLAGER_PROFESSIONS.register("occultist",
            () -> new VillagerProfession("occultist", holder -> holder.value() == OCCULT_POI.value(),
                     holder -> holder.value() == OCCULT_POI.value(), ImmutableSet.of(), ImmutableSet.of(),
                    ModSounds.SILVER_BELL_FINALITY_RING.get()));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
