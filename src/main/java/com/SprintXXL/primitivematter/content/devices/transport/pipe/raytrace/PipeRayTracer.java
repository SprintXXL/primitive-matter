package com.SprintXXL.primitivematter.content.devices.transport.pipe.raytrace;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.SprintXXL.primitivematter.content.devices.transport.pipe.raytrace.PipeAABB.CENTER_AABB;
import static com.SprintXXL.primitivematter.content.devices.transport.pipe.raytrace.PipeAABB.getConnectorAABB;
import static com.SprintXXL.primitivematter.content.devices.transport.pipe.util.PipeUtil.canRenderConnection;

public class PipeRayTracer {

    @Nullable
    public static PipeHitResult tracePipeParts(World world, BlockPos pos, Vec3d start, Vec3d end) {

        PipeHitResult closest = null;

        RayTraceResult centerHit = rayTraceAABB(pos, start, end, CENTER_AABB, null);

        if (centerHit != null) {
            closest = new PipeHitResult(centerHit, CENTER_AABB, null);
        }

        for (EnumFacing side : EnumFacing.values()) {

            if (!canRenderConnection(world, pos, side)) {
                continue;
            }

            AxisAlignedBB box = getConnectorAABB(side);
            RayTraceResult hit = rayTraceAABB(pos, start, end, box, side);

            if (hit == null) {
                continue;
            }

            PipeHitResult candidate = new PipeHitResult(hit, box, side);
            closest = getCloserHit(start, closest, candidate);
        }

        return closest;
    }

    @Nullable
    private static RayTraceResult rayTraceAABB(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB box, @Nullable EnumFacing connectorSide) {

        AxisAlignedBB worldBox = box.offset(pos);
        RayTraceResult hit = worldBox.calculateIntercept(start, end);

        if (hit == null) {
            return null;
        }

        EnumFacing reportedSide = connectorSide != null ? connectorSide : hit.sideHit;

        return new RayTraceResult(hit.hitVec, reportedSide, pos);
    }

    @Nullable
    private static PipeHitResult getCloserHit(Vec3d start, @Nullable PipeHitResult current, @Nullable PipeHitResult candidate) {

        if (current == null) {
            return candidate;
        }

        if (candidate == null) {
            return current;
        }

        double currentDistance = start.squareDistanceTo(current.getHit().hitVec);
        double candidateDistance = start.squareDistanceTo(candidate.getHit().hitVec);

        return candidateDistance < currentDistance ? candidate : current;
    }
}
