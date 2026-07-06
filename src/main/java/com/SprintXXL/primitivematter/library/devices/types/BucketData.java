package com.SprintXXL.primitivematter.library.devices.types;

public class BucketData implements DeviceData {

    private final int capacity; // ML

    public BucketData(
            int capacity
    ) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
