package net.therealzpope.mythic_ascension.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MythicAscension.MOD_ID);

    public static final Supplier<CreativeModeTab> MAGIC_REAGENTS_TAB =
            CREATIVE_MOD_TAB.register("magic_reagents_tab",
                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.FISHING_ROD))
                            .title(Component.translatable("creativetab.mythic_ascension.magic_reagents"))
                            .displayItems((itemDisplayParameters, output) -> {
                                output.accept(Items.FLOWERING_AZALEA);
                                output.accept(Items.PITCHER_PLANT);
                                output.accept(ModItems.CHALK_DUST);
                            }).build());

    public static final Supplier<CreativeModeTab> MAGIC_IMPLEMENTS_TAB =
            CREATIVE_MOD_TAB.register("magic_implements_tab",
                    () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.ACACIA_BOAT))
                            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, "magic_reagents_tab"))
                            .title(Component.translatable("creativetab.mythic_ascension.magic_implements"))
                            .displayItems((itemDisplayParameters, output) -> {
                                output.accept(Items.BOOK);
                                output.accept(Items.STICK);
                                output.accept(Items.CHARCOAL);
                                output.accept(ModBlocks.RITUAL_BOWL.get());
                                output.accept(ModItems.SILVER_BELL.get());
                            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TAB.register(eventBus);
    }
}
