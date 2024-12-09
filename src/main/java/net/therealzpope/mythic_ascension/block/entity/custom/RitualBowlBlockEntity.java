package net.therealzpope.mythic_ascension.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.therealzpope.mythic_ascension.block.entity.ModBlockEntities;

import javax.annotation.Nullable;

public class RitualBowlBlockEntity extends BlockEntity implements Container {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
    private float rotation;
    private float bobOffset; // Store bobbing phase
    private float bobHeight; // Current height

    public RitualBowlBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.RITUAL_BOWL_BE.get(), pos, blockState);
    }

    @Override
    public int getContainerSize() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0 ; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        setChanged();
        return inventory.get(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        setChanged();
        ItemStack stack = inventory.get(pSlot);
        stack.shrink(pAmount);
        return inventory.set(pSlot, stack);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        setChanged();
        return ContainerHelper.takeItem(inventory, pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        setChanged();
        inventory.set(pSlot, pStack.copyWithCount(1));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        ContainerHelper.saveAllItems(pTag, inventory, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        ContainerHelper.loadAllItems(pTag, inventory, pRegistries);
    }

    public float getRenderingRotation() {
        rotation += 1.5f; // Adjust the item's spin
        if (rotation >= 360f) {
            rotation = 0f;
        }
        return rotation;
    }

    public float getRenderingBobOffset() {
        bobOffset += 0.065f; // Adjust bobbing speed here
        if (bobOffset >= Math.PI * 2) {
            bobOffset = 0f;
        }
        bobHeight = 0.1f * (float) Math.sin(bobOffset); // Bob by 0.1 blocks
        return bobHeight;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}
