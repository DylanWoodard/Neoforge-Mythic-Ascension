package net.therealzpope.mythic_ascension;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.therealzpope.mythic_ascension.block.ModBlocks;
import net.therealzpope.mythic_ascension.block.entity.ModBlockEntities;
import net.therealzpope.mythic_ascension.block.entity.renderer.RitualBowlBlockEntityRenderer;
import net.therealzpope.mythic_ascension.block.entity.renderer.TestChalkCircleBlockEntityRenderer;
import net.therealzpope.mythic_ascension.item.ModCreativeModTabs;
import net.therealzpope.mythic_ascension.item.ModItems;
import net.therealzpope.mythic_ascension.sound.ModSounds;
import net.therealzpope.mythic_ascension.villager.ModVillagers;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MythicAscension.MOD_ID)
public class MythicAscension {
    public static final String MOD_ID = "mythic_ascension";
    private static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public MythicAscension(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModSounds.register(modEventBus);

        ModVillagers.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.RAW_MITHRAL);
            event.accept(ModItems.MITHRAL_INGOT);
        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.MITHRAL_ORE);
            event.accept(ModBlocks.MITHRAL_DEEPSLATE_ORE);
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.MITHRAL_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.RITUAL_BOWL_BE.get(), RitualBowlBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.TEST_CHALK_CIRCLE_BE.get(), TestChalkCircleBlockEntityRenderer::new);
        }
    }
}
