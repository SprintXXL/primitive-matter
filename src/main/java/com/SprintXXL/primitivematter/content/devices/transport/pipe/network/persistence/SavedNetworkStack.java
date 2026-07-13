package com.SprintXXL.primitivematter.content.devices.transport.pipe.network.persistence;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SavedNetworkStack {

    private final ItemStack stack;
    private final List<BlockPos> path;
    private final BlockPos destinationPos;
    private final EnumFacing destinationSide;
    private final int pathIndex;
    private final int progressTicks;

    public SavedNetworkStack(
            ItemStack stack,
            List<BlockPos> path,
            BlockPos destinationPos,
            EnumFacing destinationSide,
            int pathIndex,
            int progressTicks
    ) {
        this.stack = stack.isEmpty() ? ItemStack.EMPTY : stack.copy();
        this.path = new ArrayList<>(path);
        this.destinationPos = destinationPos;
        this.destinationSide = destinationSide;
        this.pathIndex = pathIndex;
        this.progressTicks = progressTicks;
    }

    public ItemStack getStack() {
        return stack.copy();
    }

    public List<BlockPos> getPath() {
        return Collections.unmodifiableList(path);
    }

    public BlockPos getDestinationPos() {
        return destinationPos;
    }

    public EnumFacing getDestinationSide() {
        return destinationSide;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public int getProgressTicks() {
        return progressTicks;
    }
}
