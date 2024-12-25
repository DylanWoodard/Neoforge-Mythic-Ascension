package net.therealzpope.mythic_ascension.item.custom;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.shape.PortalShape;

public class PortalPlacerItem extends Item {
    public PortalPlacerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();

        if (!world.isClientSide) {
            Portal portal = Portal.ENTITY_TYPE.create(world);
            portal.setOriginPos(new Vec3(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ()));
            portal.setDestinationDimension(Level.NETHER);
            portal.setDestination(new Vec3(100, 70, 100));
            portal.setOrientationAndSize(
                    new Vec3(1, 0, 0), // axisW
                    new Vec3(0, 1, 0), // axisH
                    4, // width
                    4 // height
            );
            portal.level().addFreshEntity(portal);
        }
        return InteractionResult.SUCCESS;
    }
}
