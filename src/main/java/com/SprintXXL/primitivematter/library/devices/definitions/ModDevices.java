package com.SprintXXL.primitivematter.library.devices.definitions;

import com.SprintXXL.primitivematter.library.devices.types.bucket.BucketDevice;
import com.SprintXXL.primitivematter.library.devices.types.pipe.PipeDevice;
import com.SprintXXL.primitivematter.library.devices.types.pipe.PipeType;
import com.SprintXXL.primitivematter.library.devices.types.pipe.data.ItemData;
import com.SprintXXL.primitivematter.library.substances.definitions.ModSubstances;

import static com.SprintXXL.primitivematter.library.devices.registry.DeviceRegistry.register;

public final class ModDevices {

    private ModDevices() {}

    public static void initModDevices() {

        // -------------- \\
        // BUCKET DEVICES \\
        // -------------- \\
        register(CLAY_BUCKET);
        register(IRON_BUCKET);
        register(STEEL_BUCKET);

        // ------------ \\
        // PIPE DEVICES \\
        // ------------ \\
        register(BASIC_ITEM_PIPE);

    }

    // -------------- \\
    // BUCKET DEVICES \\
    // -------------- \\
    public static final BucketDevice CLAY_BUCKET =
            new BucketDevice(
                    DeviceIDs.CLAY_BUCKET,
                    ModSubstances.CLAY,
                    1000
            );
    public static final BucketDevice IRON_BUCKET =
            new BucketDevice(
                    DeviceIDs.IRON_BUCKET,
                    ModSubstances.IRON,
                    1000
            );
    public static final BucketDevice STEEL_BUCKET =
            new BucketDevice(
                    DeviceIDs.STEEL_BUCKET,
                    ModSubstances.STEEL,
                    1000
            );

    // ------------ \\
    // PIPE DEVICES \\
    // ------------ \\
    public static final PipeDevice BASIC_ITEM_PIPE =
            new PipeDevice(
                    DeviceIDs.BASIC_ITEM_PIPE,
                    PipeType.ITEM,
                    new ItemData(
                            64,
                            1
                    )
            );
}
