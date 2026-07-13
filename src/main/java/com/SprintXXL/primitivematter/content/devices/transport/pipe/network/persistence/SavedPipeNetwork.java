package com.SprintXXL.primitivematter.content.devices.transport.pipe.network.persistence;

import net.minecraft.util.math.BlockPos;

import java.util.*;

public class SavedPipeNetwork {

    private final Set<BlockPos> members;
    private final List<SavedPipeEndpoint> extractPoints;
    private final List<SavedPipeEndpoint> insertPoints;
    private final List<SavedNetworkStack> stacksInTransit;
    private final Map<SavedPipeEndpoint, Integer> extractProgress;

    public SavedPipeNetwork(
            Set<BlockPos> members,
            List<SavedPipeEndpoint> extractPoints,
            List<SavedPipeEndpoint> insertPoints,
            List<SavedNetworkStack> stacksInTransit,
            Map<SavedPipeEndpoint, Integer> extractProgress
    ) {
        this.members = new HashSet<>(members);
        this.extractPoints = new ArrayList<>(extractPoints);
        this.insertPoints = new ArrayList<>(insertPoints);
        this.stacksInTransit = new ArrayList<>(stacksInTransit);
        this.extractProgress = new HashMap<>(extractProgress);
    }

    public Set<BlockPos> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public List<SavedPipeEndpoint> getExtractPoints() {
        return Collections.unmodifiableList(extractPoints);
    }

    public List<SavedPipeEndpoint> getInsertPoints() {
        return Collections.unmodifiableList(insertPoints);
    }

    public List<SavedNetworkStack> getStacksInTransit() {
        return Collections.unmodifiableList(stacksInTransit);
    }

    public Map<SavedPipeEndpoint, Integer> getExtractProgress() {
        return Collections.unmodifiableMap(extractProgress);
    }
}
