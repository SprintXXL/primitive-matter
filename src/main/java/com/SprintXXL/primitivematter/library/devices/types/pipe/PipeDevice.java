package com.SprintXXL.primitivematter.library.devices.types.pipe;

import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.types.DeviceCategory;
import com.SprintXXL.primitivematter.library.devices.types.pipe.data.PipeData;

public class PipeDevice extends Device {

    private final PipeType type;
    private final PipeData data;

    public PipeDevice(
            String id,
            PipeType type,
            PipeData data
    ) {
        super(id, DeviceCategory.PIPE);

        this.type = type;
        this.data = data;
    }

    @Override
    public boolean createsBlock() {
        return true;
    }

    @Override
    public boolean createsItem() {
        return false;
    }

    public PipeType getType() {
        return type;
    }

    public PipeData getData() {
        return data;
    }
}
