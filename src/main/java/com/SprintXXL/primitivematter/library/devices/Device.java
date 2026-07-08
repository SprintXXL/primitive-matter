package com.SprintXXL.primitivematter.library.devices;

import com.SprintXXL.primitivematter.library.devices.types.DeviceCategory;

public abstract class Device implements DeviceDefinition {

    private final String id;
    private final DeviceCategory category;

    public Device(
            String id,
            DeviceCategory category
    ) {
        this.id = id;
        this.category = category;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public DeviceCategory getCategory() {
        return category;
    }

    public boolean createsBlock() {
        return false;
    }

    public boolean createsItem() {
        return false;
    }
}
