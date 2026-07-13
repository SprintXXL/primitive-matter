package com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.base.TileEntityPipe;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.util.PipeSideMode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.*;

import static com.SprintXXL.primitivematter.PrimitiveMatter.printInfo;

public class PipeNetworkDiscovery {

    public static PipeNetworkDiscoveryResult discoverNetwork(World world, BlockPos startPos) {

        Set<BlockPos> members = new HashSet<>();
        List<PipeEndpoint> extractPoints = new ArrayList<>();
        List<PipeEndpoint> insertPoints = new ArrayList<>();
        Queue<BlockPos> toVisit = new ArrayDeque<>();

        toVisit.add(startPos);

        while (!toVisit.isEmpty()) {

            BlockPos currentPos = toVisit.remove();

            if (!members.add(currentPos)) {
                continue;
            }

            for (EnumFacing side : EnumFacing.values()) {

                BlockPos neighborPos = currentPos.offset(side);

                if (arePipesConnected(world, currentPos, side)) {
                    toVisit.add(neighborPos);
                    continue;
                }

                PipeEndpoint endpoint = findInventoryEndpoint(world, currentPos, side);

                if (endpoint == null) {
                    continue;
                }

                TileEntity currentTile = world.getTileEntity(currentPos);

                if (!(currentTile instanceof TileEntityPipe)) {
                    continue;
                }

                TileEntityPipe pipe = (TileEntityPipe) currentTile;
                PipeSideMode mode = pipe.getMode(side);

                if (mode == PipeSideMode.PULL) {
                    printInfo("Found extract point: " + endpoint.getInventoryPos());
                    extractPoints.add(endpoint);
                }
                else if (mode == PipeSideMode.CONNECTED) {
                    printInfo("Found insert point: " + endpoint.getInventoryPos());
                    insertPoints.add(endpoint);
                }
            }
        }

        return new PipeNetworkDiscoveryResult(
                members,
                extractPoints,
                insertPoints
        );
    }

    private static boolean arePipesConnected(World world, BlockPos currentPos, EnumFacing side) {

        TileEntity currentTile = world.getTileEntity(currentPos);
        TileEntity neighborTile = world.getTileEntity(currentPos.offset(side));

        if (!(currentTile instanceof TileEntityPipe) || !(neighborTile instanceof TileEntityPipe)) {
            return false;
        }

        TileEntityPipe currentPipe = (TileEntityPipe) currentTile;
        TileEntityPipe neighborPipe = (TileEntityPipe) neighborTile;

        return currentPipe.getMode(side) != PipeSideMode.DISCONNECTED &&
                neighborPipe.getMode(side.getOpposite()) != PipeSideMode.DISCONNECTED;
    }

    @Nullable
    private static PipeEndpoint findInventoryEndpoint(World world, BlockPos pipePos, EnumFacing side) {

        BlockPos inventoryPos = pipePos.offset(side);
        TileEntity tile = world.getTileEntity(inventoryPos);

        if (tile == null || tile instanceof TileEntityPipe) {
            return null;
        }

        EnumFacing inventorySide = side.getOpposite();

        if (tile.hasCapability(
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                inventorySide
        )) {
            return new PipeEndpoint(pipePos, inventoryPos, inventorySide);
        }

        if (tile.hasCapability(
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                null
        )) {
            return new PipeEndpoint(pipePos, inventoryPos, inventorySide);
        }

        return null;
    }
}
