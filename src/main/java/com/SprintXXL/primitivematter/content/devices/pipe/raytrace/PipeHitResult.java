package com.SprintXXL.primitivematter.content.devices.pipe.raytrace;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;

public class PipeHitResult {

    private final RayTraceResult hit;
    private final AxisAlignedBB box;
    private final EnumFacing connectorSide;

    public PipeHitResult(
            RayTraceResult hit,
            AxisAlignedBB box,
            EnumFacing connectorSide
    ) {
        this.hit = hit;
        this.box = box;
        this.connectorSide = connectorSide;
    }

    public RayTraceResult getHit() {
        return hit;
    }

    public AxisAlignedBB getBox() {
        return box;
    }

    public EnumFacing getConnectorSide() {
        return connectorSide;
    }

    public boolean isConnector() {
        return connectorSide != null;
    }
}
