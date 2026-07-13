package com.SprintXXL.primitivematter.content.devices.transport.pipe.util;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.base.BlockPipe;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.base.TileEntityPipe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

public class PipeUtil {

    public static boolean tryAutoConnect(World world, BlockPos pipePos, EnumFacing side) {

        if (!canConnectTo(world, pipePos, side)) {
            return false;
        }

        TileEntity tile = world.getTileEntity(pipePos);

        if (!(tile instanceof TileEntityPipe)) {
            return false;
        }

        TileEntityPipe pipe = (TileEntityPipe) tile;
        pipe.setMode(side, PipeSideMode.CONNECTED);

        return true;
    }

    public static boolean canRenderConnection(IBlockAccess world, BlockPos pos, EnumFacing side) {

        if (!canConnectTo(world, pos, side)) {
            return false;
        }

        TileEntity tile = world.getTileEntity(pos);

        if (!(tile instanceof TileEntityPipe)) {
            return false;
        }

        PipeSideMode mode = ((TileEntityPipe) tile).getMode(side);

        if (mode == PipeSideMode.DISCONNECTED) {
            return false;
        }

        BlockPos neighborPos = pos.offset(side);
        TileEntity neighborTile = world.getTileEntity(neighborPos);

        if (neighborTile instanceof TileEntityPipe) {
            PipeSideMode neighborMode = ((TileEntityPipe) neighborTile).getMode(side.getOpposite());
            return neighborMode != PipeSideMode.DISCONNECTED;
        }

        return true;
    }

    public static boolean toggleConnection(World world, BlockPos pipePos, EnumFacing side) {

        TileEntity tile = world.getTileEntity(pipePos);

        if (!(tile instanceof TileEntityPipe)) {
            return false;
        }

        TileEntityPipe pipe = (TileEntityPipe) tile;
        PipeSideMode current = pipe.getMode(side);
        PipeSideMode next = current.toggleConnected();

        pipe.setMode(side, next);

        BlockPos neighborPos = pipePos.offset(side);
        TileEntity neighborTile = world.getTileEntity(neighborPos);

        if (neighborTile instanceof TileEntityPipe) {
            TileEntityPipe neighborPipe = (TileEntityPipe) neighborTile;
            neighborPipe.setMode(side.getOpposite(), next);
        }

        return true;
    }

    public static boolean togglePullMode(World world, BlockPos pipePos, EnumFacing side) {

        BlockPos neighborPos = pipePos.offset(side);
        TileEntity neighborTile = world.getTileEntity(neighborPos);

        if (neighborTile instanceof TileEntityPipe) {
            return false;
        }

        TileEntity tile = world.getTileEntity(pipePos);

        if (!(tile instanceof TileEntityPipe)) {
            return false;
        }

        TileEntityPipe pipe = (TileEntityPipe) tile;
        PipeSideMode current = pipe.getMode(side);
        PipeSideMode next = current.togglePull();

        pipe.setMode(side, next);
        
        return true;
    }


    public static boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing side) {

        BlockPos neighborPos = pos.offset(side);
        IBlockState neighborState = world.getBlockState(neighborPos);
        Block block = neighborState.getBlock();

        if (block instanceof BlockPipe) {
            return true;
        }

        TileEntity tile = world.getTileEntity(neighborPos);

        if (tile != null) {
            if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite())) {
                return true;
            }
            if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
                return true;
            }
            if (tile instanceof IInventory) {
                return true;
            }
        }

        return false;
    }
}
