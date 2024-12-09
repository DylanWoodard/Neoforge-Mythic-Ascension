package net.therealzpope.mythic_ascension.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.block.entity.custom.TestChalkCircleBlockEntity;
import org.joml.Matrix4f;

public class TestChalkCircleBlockEntityRenderer implements BlockEntityRenderer<TestChalkCircleBlockEntity> {
    private static final ResourceLocation PENTAGRAM_TEXTURE = ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, "textures/entity/pentagram.png");
    private static final ResourceLocation CHAIN_TEXTURE = ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, "textures/entity/chains.png");

    public TestChalkCircleBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TestChalkCircleBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        // Define the orbit radius (1 block)
        float orbitRadius = 1.0f;

        // Iterate over the three items
        for (int i = 0; i < 3; i++) {
            ItemStack stack = pBlockEntity.getItem(i); // Get the item for each slot
            if (stack.isEmpty()) continue; // Skip empty slots

            pPoseStack.pushPose();

            // Calculate the equidistant angle for each item (120 degrees apart)
            float baseRotation = pBlockEntity.getRenderingOrbit(); // Shared rotation for all items
            float angle = baseRotation + (i * 120.0f); // Offset by 120 degrees for each item

            // Convert angle to radians for trigonometric functions
            double radians = Math.toRadians(angle);

            // Calculate X and Z positions for the circular orbit
            double offsetX = orbitRadius * Math.cos(radians);
            double offsetZ = orbitRadius * Math.sin(radians);

            // Calculate vertical bobbing offset
            float bobOffset = pBlockEntity.getRenderingBobOffset(i);

            // Translate to the block center, apply orbit and bobbing
            pPoseStack.translate(0.5 + offsetX, 1.0 + bobOffset, 0.5 + offsetZ);

            // Rotate the item to make it spin around its own axis
            float spinRotation = pBlockEntity.getRenderingItemSpinRotation(i);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(spinRotation));

            // Scale down the item for rendering
            pPoseStack.scale(0.5f, 0.5f, 0.5f);

            // Render the item
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(),
                    pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), i);

            pPoseStack.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}