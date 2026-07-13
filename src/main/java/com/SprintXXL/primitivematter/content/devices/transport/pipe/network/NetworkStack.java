package com.SprintXXL.primitivematter.content.devices.transport.pipe.network;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.SprintXXL.primitivematter.PrimitiveMatter.printInfo;

public class NetworkStack {

    private ItemStack stack;
    private final List<BlockPos> path;
    private final BlockPos destinationPos;
    private final EnumFacing destinationSide;
    private int pathIndex;
    private int progressTicks;

    public NetworkStack(
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

    public void setStack(ItemStack stack) {
        this.stack = stack.isEmpty() ? ItemStack.EMPTY : stack.copy();
    }

    public ItemStack getStack() {
        return stack.copy();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
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

    public BlockPos getCurrentPos() {
        return path.get(pathIndex);
    }

    public void tick() {
        progressTicks++;
    }

    public void resetProgress() {
        progressTicks = 0;
    }

    public boolean advance() {

        if (pathIndex >= path.size() - 1) {

            return false;
        }

        pathIndex++;
        progressTicks = 0;

        printInfo("Advanced to pipe: " + getCurrentPos() + " " + pathIndex);

        return true;
    }
}
