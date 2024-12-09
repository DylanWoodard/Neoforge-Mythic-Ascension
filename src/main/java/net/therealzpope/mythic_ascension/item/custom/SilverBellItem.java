package net.therealzpope.mythic_ascension.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.therealzpope.mythic_ascension.sound.ModSounds;

import java.util.Random;

public class SilverBellItem extends Item {
    public SilverBellItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide){
            Random random = new Random();
            if (random.nextBoolean()) {
                level.playSound(null, player.blockPosition(), ModSounds.SILVER_BELL_STANDARD_RING.get(), SoundSource.PLAYERS, 16.0f, 1.0f);
            } else {
                level.playSound(null, player.blockPosition(), ModSounds.SILVER_BELL_FINALITY_RING.get(), SoundSource.PLAYERS, 10000.0f, 1.0f);
            }
        }
        return InteractionResultHolder.success(itemStack);
    }
}
