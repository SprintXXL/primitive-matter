package com.SprintXXL.primitivematter.library.devices.category.transport.bucket;

import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.category.DeviceCategory;
import com.SprintXXL.primitivematter.library.substances.Substance;

public class BucketDevice extends Device {

    private final Substance material;
    private final int capacity;

    public BucketDevice(
            String id,
            Substance material,
            int capacity
    ) {
        super(id, DeviceCategory.TRANSPORT);

        this.material = material;
        this.capacity = capacity;
    }

    @Override
    public boolean createsBlock() {
        return false;
    }

    @Override
    public boolean createsItem() {
        return true;
    }

    public Substance getMaterial() {
        return material;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxTemperature() {

        if (material == null || material.getSolidState() == null) {
            return Integer.MAX_VALUE;
        }

        return material.getSolidState().getProperties().getMaxTemperature();
    }
}
