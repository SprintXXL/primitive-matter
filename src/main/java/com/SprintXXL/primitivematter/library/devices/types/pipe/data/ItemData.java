package com.SprintXXL.primitivematter.library.devices.types.pipe.data;

public class ItemData implements PipeData {

    private final int capacity;
    private final int throughput;

    public ItemData(
            int capacity,
            int throughput
    ) {
        this.capacity = capacity;
        this.throughput = throughput;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getThroughput() {
        return throughput;
    }
}
