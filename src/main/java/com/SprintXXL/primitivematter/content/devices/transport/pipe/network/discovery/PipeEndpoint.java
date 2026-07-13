package com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.persistence.SavedPipeEndpoint;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class PipeEndpoint {

    private final BlockPos pipePos;
    private final BlockPos inventoryPos;
    private final EnumFacing inventorySide;

    public PipeEndpoint(
            BlockPos pipePos,
            BlockPos inventoryPos,
            EnumFacing inventorySide
    ) {
        this.pipePos = pipePos;
        this.inventoryPos = inventoryPos;
        this.inventorySide = inventorySide;
    }

    public BlockPos getPipePos() {
        return pipePos;
    }

    public BlockPos getInventoryPos() {
        return inventoryPos;
    }

    public EnumFacing getInventorySide() {
        return inventorySide;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PipeEndpoint)) {
            return false;
        }

        PipeEndpoint other = (PipeEndpoint) obj;

        return pipePos.equals(other.pipePos)
                && inventoryPos.equals(other.inventoryPos)
                && inventorySide == other.inventorySide;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pipePos, inventoryPos, inventorySide);
    }
}
