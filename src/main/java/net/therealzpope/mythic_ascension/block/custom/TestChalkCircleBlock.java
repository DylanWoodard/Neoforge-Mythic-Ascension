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
import net.therealzpope.mythic_ascension.block.entity.custom.TestChalkCircleBlockEntity;
import org.jetbrains.annotations.Nullable;

public class TestChalkCircleBlock extends BaseEntityBlock {

    // Defines the shape of the wireframe outline around the block
    public static final VoxelShape SHAPE = Shapes.or(box(0, 0, 0, 64, 0.01, 64));


    public static final MapCodec<TestChalkCircleBlock> CODEC = simpleCodec(TestChalkCircleBlock::new);

    public TestChalkCircleBlock(Properties properties) {
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
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TestChalkCircleBlockEntity(pPos, pState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            if (pLevel.getBlockEntity(pPos) instanceof TestChalkCircleBlockEntity testChalkCircleBlockEntity) {
                Containers.dropContents(pLevel, pPos, testChalkCircleBlockEntity);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
                if (testChalkCircleBlockEntity.isActive) {
                    testChalkCircleBlockEntity.toggleTornado(pLevel, pPos);
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos,
                                              Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pLevel.getBlockEntity(pPos) instanceof TestChalkCircleBlockEntity testChalkCircleBlockEntity) {
            // Find the first empty slot or slot with an item to interact with
            for (int slot = 0; slot < 3; slot++) {
                ItemStack stackInSlot = testChalkCircleBlockEntity.getItem(slot);

                // Add item to the container if the slot is empty and the player's stack is not empty
                if (stackInSlot.isEmpty() && !pStack.isEmpty()) {
                    testChalkCircleBlockEntity.setItem(slot, pStack.split(1)); // Split the stack to leave 1 item in the slot
                    pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
                    return ItemInteractionResult.SUCCESS;
                }

                // Remove item from the container if the player's hand is empty
                if (pStack.isEmpty() && !stackInSlot.isEmpty()) {
                    pPlayer.setItemInHand(InteractionHand.MAIN_HAND, stackInSlot);
                    testChalkCircleBlockEntity.setItem(slot, ItemStack.EMPTY); // Clear the slot
                    pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
                    testChalkCircleBlockEntity.toggleTornado(pLevel, pPos);
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }

        return ItemInteractionResult.SUCCESS;
    }
}
