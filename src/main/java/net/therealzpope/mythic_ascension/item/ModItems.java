package net.therealzpope.mythic_ascension.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.item.custom.*;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MythicAscension.MOD_ID);

    public static final DeferredItem<Item> MITHRAL_INGOT = ITEMS.registerSimpleItem("mithral_ingot");
    public static final DeferredItem<Item> RAW_MITHRAL = ITEMS.registerSimpleItem("raw_mithral");

    public static final DeferredItem<Item> CHALK_DUST = ITEMS.registerSimpleItem("chalk_dust");

    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerSimpleItem("silver_ingot");

    public static final DeferredItem<Item> CHALK = ITEMS.register("chalk",
            () -> new Chalk(new Item.Properties().durability(64)));

    public static final DeferredItem<Item> BLASTING_ROD = ITEMS.register("blasting_rod",
            () -> new BlastingRodItem(new Item.Properties().durability(64)));

    public static final DeferredItem<Item> SPELLBOOK = ITEMS.register("spellbook",
            () -> new SpellbookItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> SILVER_BELL = ITEMS.register("silver_bell",
            () -> new SilverBellItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> PORTAL_PLACER = ITEMS.register("portal_placer",
            () -> new PortalPlacerItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
