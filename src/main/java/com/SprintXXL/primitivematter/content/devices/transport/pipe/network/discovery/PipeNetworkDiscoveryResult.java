package com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery;

import net.minecraft.util.math.BlockPos;

import java.util.*;

public class PipeNetworkDiscoveryResult {

    private final Set<BlockPos> members;
    private final List<PipeEndpoint> extractPoints;
    private final List<PipeEndpoint> insertPoints;

    public PipeNetworkDiscoveryResult(
            Set<BlockPos> members,
            List<PipeEndpoint> extractPoints,
            List<PipeEndpoint> insertPoints
    ) {
        this.members = new HashSet<>(members);
        this.extractPoints = new ArrayList<>(extractPoints);
        this.insertPoints = new ArrayList<>(insertPoints);
    }

    public Set<BlockPos> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public List<PipeEndpoint> getExtractPoints() {
        return Collections.unmodifiableList(extractPoints);
    }

    public List<PipeEndpoint> getInsertPoints() {
        return Collections.unmodifiableList(insertPoints);
    }
}
