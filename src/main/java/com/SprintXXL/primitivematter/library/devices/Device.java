package com.SprintXXL.primitivematter.library.devices;

import com.SprintXXL.primitivematter.library.devices.category.DeviceCategory;
import com.sprintxxl.ascenthub.definitions.AscentDefinition;

public abstract class Device implements DeviceDefinition, AscentDefinition {

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
    public String getCategory() {
        return "device";
    }

    @Override
    public DeviceCategory getDeviceCategory() {
        return category;
    }

    public boolean createsBlock() {
        return false;
    }

    public boolean createsItem() {
        return false;
    }
}
