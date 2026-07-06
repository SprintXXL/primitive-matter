package com.SprintXXL.primitivematter.library.devices.definitions;

import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.types.BucketData;
import com.SprintXXL.primitivematter.library.devices.types.DeviceType;

import static com.SprintXXL.primitivematter.library.devices.registry.DeviceRegistry.register;

public final class ModDevices {

    private ModDevices() {}

    public static void initModDevices() {

        // ------- \\
        // BUCKETS \\
        // ------- \\
        register(IRON_BUCKET);
    }

    // ------- \\
    // BUCKETS \\
    // ------- \\
    public static final Device IRON_BUCKET =
            new Device(
                    DeviceIDs.IRON_BUCKET,
                    DeviceType.BUCKET,
                    new BucketData(
                            1000
                    )
            );
}
