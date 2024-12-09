package net.therealzpope.mythic_ascension.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.therealzpope.mythic_ascension.block.entity.custom.RitualBowlBlockEntity;
import org.jetbrains.annotations.Nullable;

public class RitualBowlBlock extends BaseEntityBlock {

    // Defines the shape of the wireframe outline around the block
    public static final VoxelShape SHAPE = Shapes.or(box(5.5, 0, 5.5, 10.5, 1.25, 10.5), box(6.5, 1.25, 6.5, 9.5, 2.5, 9.5), box(4.25, 2.25, 4.25, 11.75, 2.75, 11.75), box(3.5, 2.75, 3.5, 12.5, 4.5, 4.25), box(11.75, 2.75, 4.25, 12.5, 4.5, 11.75),
            box(3.5, 2.75, 11.75, 12.5, 4.5, 12.5), box(3.5, 2.75, 4.25, 4.25, 4.5, 11.75), box(3.5, 4.5, 2.75, 12.5, 5.25, 4.25), box(11.75, 4.5, 3.5, 13.25, 5.25, 12.5), box(3.5, 4.5, 11.75, 12.5, 5.25, 13.25),
            box(2.75, 4.5, 3.5, 4.25, 5.25, 12.5));

    public static final MapCodec<RitualBowlBlock> CODEC = simpleCodec(RitualBowlBlock::new);

    public RitualBowlBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RitualBowlBlockEntity(pPos, pState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            if (pLevel.getBlockEntity(pPos) instanceof RitualBowlBlockEntity ritualBowlBlockEntity) {
                Containers.dropContents(pLevel, pPos, ritualBowlBlockEntity);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos,
                                              Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if(pLevel.getBlockEntity(pPos) instanceof RitualBowlBlockEntity ritualBowlBlockEntity) {
            if(ritualBowlBlockEntity.isEmpty() && !pStack.isEmpty()) {
                ritualBowlBlockEntity.setItem(0, pStack);
                pStack.shrink(1);
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            } else if(pStack.isEmpty()) {
                ItemStack stackOnPedestal = ritualBowlBlockEntity.getItem(0);
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, stackOnPedestal);
                ritualBowlBlockEntity.clearContent();
                pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }
}
