package com.SprintXXL.primitivematter.library.devices.category.transport.module;

import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.category.DeviceCategory;
import com.SprintXXL.primitivematter.library.devices.category.transport.module.data.ModuleData;

public class ModuleDevice extends Device {

    private final ModuleData data;

    public ModuleDevice(
            String id,
            ModuleData data
    ) {
        super(id, DeviceCategory.TRANSPORT);

        this.data = data;
    }

    @Override
    public boolean createsBlock() {
        return false;
    }

    @Override
    public boolean createsItem() {
        return true;
    }

    public ModuleData getData() {
        return data;
    }
}
