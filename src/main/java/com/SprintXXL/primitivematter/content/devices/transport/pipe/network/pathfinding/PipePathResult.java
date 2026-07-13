package com.SprintXXL.primitivematter.content.devices.transport.pipe.network.pathfinding;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery.PipeEndpoint;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PipePathResult {

    private final List<BlockPos> path;
    private final PipeEndpoint destination;

    public PipePathResult(
            List<BlockPos> path,
            PipeEndpoint destination
    ) {
        this.path = new ArrayList<>(path);
        this.destination = destination;
    }

    public List<BlockPos> getPath() {
        return Collections.unmodifiableList(path);
    }

    public PipeEndpoint getDestination() {
        return destination;
    }
}
