package net.therealzpope.mythic_ascension.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import net.therealzpope.mythic_ascension.block.entity.ModBlockEntities;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public class TestChalkCircleBlockEntity extends BlockEntity implements Container {
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    public boolean isActive = false;
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

    public void toggleTornado(Level level, BlockPos blockPos) {
        if (level.isClientSide) {
            isActive = !isActive;

            if (isActive) {
                // Start a new thread to continuously spawn the vortex
                new Thread(() -> {
                    while (isActive) {
                        createTornadoParticles(level, blockPos);
                        try {
                            Thread.sleep(50); // Controls the update rate of the particles
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }).start();

                // Play continuous sound
                level.playLocalSound(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.5F, 1.2F, false);
            }
        }
    }

    private void createTornadoParticles(Level level, BlockPos blockPos) {
        // Define the center and parameters of the tornado
        Vec3 center = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 2, blockPos.getZ() + 0.5);
        double radius = 1.5;
        double height = 2.0;
        int particleCount = 200; // Increased particle count for a denser effect
        double angularVelocity = Math.PI * 2; // Full rotation per step

        for (int i = 0; i < particleCount; i++) {
            // Calculate the particle's vertical position and time-based rotation
            double progress = (double) i / particleCount; // Ranges from 0 to 1
            double y = center.y - (progress * height);

            // Introduce violent spinning by using time-based animation
            double time = System.currentTimeMillis() / 1000.0;
            double angle = angularVelocity * time + progress * Math.PI * 8; // Rapid rotation
            double xOffset = Math.cos(angle) * radius * (1 - progress);
            double zOffset = Math.sin(angle) * radius * (1 - progress);

            // Randomize velocity for a chaotic look
            double velocityX = (Math.random() - 0.5) * 0.2;
            double velocityY = 0.1 + (Math.random() * 0.1);
            double velocityZ = (Math.random() - 0.5) * 0.2;

            // Spawn the particle with velocity for chaotic movement
            Vec3 particlePosition = new Vec3(center.x + xOffset, y, center.z + zOffset);
            level.addParticle(ParticleTypes.END_ROD, particlePosition.x, particlePosition.y, particlePosition.z, velocityX, velocityY, velocityZ);
        }
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
