package net.therealzpope.mythic_ascension.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.block.ModBlocks;
import net.therealzpope.mythic_ascension.item.ModItems;
import net.therealzpope.mythic_ascension.util.ModTags;
import net.therealzpope.mythic_ascension.villager.ModVillagers;

import java.util.List;

@EventBusSubscriber(modid = MythicAscension.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (event.getSource().getDirectEntity() instanceof Player aggressor) {
            if (isSharpImplement(event.getSource().getWeaponItem())) {
                LivingEntity victim = event.getEntity();
                System.out.println(victim.getName().getString() + " Was struck by: " + aggressor.getDisplayName().getString() + " using: " + event.getSource().getWeaponItem().getDisplayName().getString());
            }

        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.OCCULTIST.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(ModItems.CHALK_DUST.get(), 8), 4, 1, 0.05f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 8),
                    new ItemStack(ModItems.SILVER_BELL.get(), 1), 1, 4, 0.05f
            ));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 1),
                    new ItemStack(Items.BOOK, 2), 8, 4, 0.05f
            ));
            /*trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ModItems.CHALK, 2), 8, 4, 0.05f
            ));*/
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 16),
                    new ItemStack(ModBlocks.RITUAL_BOWL, 1), 3, 6, 0.05f
            ));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemCost(Items.DIAMOND, 32),
                    new ItemStack(ModItems.SPELLBOOK.get(), 1), 4, 16, 0.05f
            ));
        }
    }

    private static boolean isSharpImplement(ItemStack weaponItem) {
        return weaponItem.is(ModTags.Items.SHARP_IMPLEMENTS);
    }

}
