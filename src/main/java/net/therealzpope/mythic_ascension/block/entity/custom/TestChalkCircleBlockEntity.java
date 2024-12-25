package net.therealzpope.mythic_ascension.block.entity.custom;

import mod.chloeprime.aaaparticles.api.common.AAALevel;
import mod.chloeprime.aaaparticles.api.common.ParticleEmitterInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.block.entity.ModBlockEntities;
import org.intellij.lang.annotations.Identifier;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.sql.Time;
import java.util.List;

public class TestChalkCircleBlockEntity extends BlockEntity implements Container {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    private static final ParticleEmitterInfo SUMMONING = new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, "flame_pillar_test"));
    private static final ParticleEmitterInfo OUTSIDER_SUMMONING_1 = new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, "outsider_black_hole"));
    private static final ParticleEmitterInfo OUTSIDER_SUMMONING_2 = new ParticleEmitterInfo(ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, "outsider_mist_explosion"));
    private float rotation = 0f;
    private float[] itemSpins = new float[3]; // Spin rotation for each item
    private float[] bobOffsets = new float[3]; // Store bobbing phases for three items
    private float[] bobHeights = new float[3]; // Current heights

    public TestChalkCircleBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TEST_CHALK_CIRCLE_BE.get(), pos, blockState);
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

    public float getRenderingOrbit() {
        rotation += 0.4f; // Adjust speed if needed
        if (rotation >= 360f) {
            rotation = 0f;
        }
        return rotation;
    }

    public float getRenderingItemSpinRotation(int itemIndex) {
        itemSpins[itemIndex] += 1.5f; // Adjust spin speed here (2 degrees per tick)
        if (itemSpins[itemIndex] >= 360f) {
            itemSpins[itemIndex] = 0f;
        }
        return itemSpins[itemIndex];
    }

    public float getRenderingBobOffset(int itemIndex) {
        bobOffsets[itemIndex] += 0.08f; // Adjust bobbing speed here
        if (bobOffsets[itemIndex] >= Math.PI * 2) {
            bobOffsets[itemIndex] = 0f;
        }
        bobHeights[itemIndex] = 0.1f * (float) Math.sin(bobOffsets[itemIndex]); // Bob by 0.1 blocks
        return bobHeights[itemIndex];
    }

    public Entity getEntityOnBlock() {
        if (this.level == null) {
            return null;
        }

        // Define the bounding box above the block (3x3 area)
        AABB boundingBox = new AABB(
                this.worldPosition.getX() - 1.5,
                this.worldPosition.getY(),
                this.worldPosition.getZ() - 1.5,
                this.worldPosition.getX() + 1.5,
                this.worldPosition.getY() + 1.1, // Slightly above the block's surface
                this.worldPosition.getZ() + 1.5
        );

        // Get entities within the bounding box
        List<Entity> entities = this.level.getEntities(null, boundingBox);

        // Return the first entity found, or null if no entities are present
        return entities.isEmpty() ? null : entities.get(0);
    }

    // Summoning animation here
    public void animateSummoning(Level pLevel, BlockPos pPos) {
//        AAALevel.addParticle(pLevel, SUMMONING.clone().position(pPos.getX() + 0.5d, pPos.getY() - 0.1, pPos.getZ() + 0.5d).scale(0.8f));
        AAALevel.addParticle(pLevel, OUTSIDER_SUMMONING_1.clone().position(pPos.getX() + 0.5d, pPos.getY() + 1.5, pPos.getZ() + 0.5d).scale(0.6f));
        AAALevel.addParticle(pLevel, OUTSIDER_SUMMONING_2.clone().position(pPos.getX() + 0.5d, pPos.getY() + 1.5, pPos.getZ() + 0.5d).scale(0.75f));
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
