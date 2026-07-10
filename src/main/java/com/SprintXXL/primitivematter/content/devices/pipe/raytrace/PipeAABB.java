package com.SprintXXL.primitivematter.content.devices.pipe.raytrace;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class PipeAABB {

    public static final AxisAlignedBB CENTER_AABB =
            new AxisAlignedBB(
                    4.0 / 16.0, 4.0 / 16.0, 4.0 / 16.0,
                    12.0 / 16.0, 12.0 / 16.0, 12.0 / 16.0
            );
    public static final AxisAlignedBB UP_AABB =
            new AxisAlignedBB(
                    4.0 / 16.0, 12.0 / 16.0, 4.0 / 16.0,
                    12.0 / 16.0, 1.0, 12.0 / 16.0
            );
    public static final AxisAlignedBB DOWN_AABB =
            new AxisAlignedBB(
                    4.0 / 16.0, 0.0, 4.0 / 16.0,
                    12.0 / 16.0, 4.0 / 16.0, 12.0 / 16.0
            );
    public static final AxisAlignedBB NORTH_AABB =
            new AxisAlignedBB(
                    4.0 / 16.0, 4.0 / 16.0, 0.0,
                    12.0 / 16.0, 12.0 / 16.0, 4.0 / 16.0
            );
    public static final AxisAlignedBB SOUTH_AABB =
            new AxisAlignedBB(
                    4.0 / 16.0, 4.0 / 16.0, 12.0 / 16.0,
                    12.0 / 16.0, 12.0 / 16.0, 1.0
            );
    public static final AxisAlignedBB EAST_AABB =
            new AxisAlignedBB(
                    12.0 / 16.0, 4.0 / 16.0, 4.0 / 16.0,
                    1.0, 12.0 / 16.0, 12.0 / 16.0
            );
    public static final AxisAlignedBB WEST_AABB =
            new AxisAlignedBB(
                    0.0, 4.0 / 16.0, 4.0 / 16.0,
                    4.0 / 16.0, 12.0 / 16.0, 12.0 / 16.0
            );

    public static AxisAlignedBB getConnectorAABB(EnumFacing side) {

        switch(side) {
            case UP: return UP_AABB;
            case DOWN: return DOWN_AABB;
            case NORTH: return NORTH_AABB;
            case SOUTH: return SOUTH_AABB;
            case EAST: return EAST_AABB;
            case WEST: return WEST_AABB;
            default: return CENTER_AABB;
        }
    }
}
