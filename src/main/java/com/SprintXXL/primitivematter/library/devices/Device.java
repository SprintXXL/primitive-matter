package com.SprintXXL.primitivematter.library.devices;

import com.SprintXXL.primitivematter.library.devices.types.BucketData;
import com.SprintXXL.primitivematter.library.devices.types.DeviceType;
import com.SprintXXL.primitivematter.library.devices.types.DeviceData;

public class Device {

    private final String id;
    private final DeviceType type;
    private final DeviceData data;

    public Device(
            String id,
            DeviceType type,
            DeviceData data
    ) {
        this.id = id;
        this.type = type;
        this.data = data;
    }

    public String getID() {
        return id;
    }

    public DeviceType getType() {
        return type;
    }

    public DeviceData getData() {
        return data;
    }

    public int getCapacity() {

        if (data instanceof BucketData) {
            return ((BucketData) data).getCapacity();
        }

        return 0;
    }
}
