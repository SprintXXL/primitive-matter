package com.SprintXXL.primitivematter.library.devices;

import com.SprintXXL.primitivematter.library.devices.category.DeviceCategory;

public interface DeviceDefinition {

    String getID();
    DeviceCategory getDeviceCategory();
}
