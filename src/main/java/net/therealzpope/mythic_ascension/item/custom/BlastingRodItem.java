package net.therealzpope.mythic_ascension.item.custom;

import net.minecraft.world.item.Item;

public class BlastingRodItem extends Item {

    private static final int COOLDOWN_TICKS = 60; // 3 seconds of cooldown
    private static final double RANGE = 10.0; // Range of the spell
    private static final int DAMAGE = 8; // Damage dealt by the spell

    public BlastingRodItem(Properties properties) {
        super(properties);
    }
}