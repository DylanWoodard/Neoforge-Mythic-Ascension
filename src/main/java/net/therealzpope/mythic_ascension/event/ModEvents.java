package net.therealzpope.mythic_ascension.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.villager.ModVillagers;

import java.util.List;

@EventBusSubscriber(modid = MythicAscension.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.OCCULTIST.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        }

    }

    @SubscribeEvent
    public static void addWanderingTrades(WandererTradesEvent event) {

    }

}