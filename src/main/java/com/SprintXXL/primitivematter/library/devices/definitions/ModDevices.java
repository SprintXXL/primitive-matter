package com.SprintXXL.primitivematter.library.devices.definitions;

import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.category.transport.bucket.BucketDevice;
import com.SprintXXL.primitivematter.library.devices.category.transport.module.ModuleDevice;
import com.SprintXXL.primitivematter.library.devices.category.transport.module.data.ItemData;
import com.SprintXXL.primitivematter.library.devices.category.transport.pipe.PipeDevice;
import com.SprintXXL.primitivematter.library.devices.category.transport.pipe.PipeType;
import com.SprintXXL.primitivematter.library.devices.category.transport.pipe.data.ItemPipeData;
import com.SprintXXL.primitivematter.library.substances.definitions.ModSubstances;
import com.sprintxxl.ascenthub.definitions.DefinitionRegistrar;

public final class ModDevices {

    private ModDevices() {}

    public static void initDeviceDefinitions(DefinitionRegistrar<Device> registrar) {

        // --------------- \\
        // STORAGE DEVICES \\
        // --------------- \\

        // ----------------- \\
        // TRANSPORT DEVICES \\
        // ----------------- \\
        // BUCKET \\
        registrar.register(CLAY_BUCKET);
        registrar.register(IRON_BUCKET);
        registrar.register(STEEL_BUCKET);

        // MODULE \\
        registrar.register(PRIMITIVE_CONVEYOR);

        // PIPE \\
        registrar.register(BASIC_ITEM_PIPE);

    }

    // --------------- \\
    // STORAGE DEVICES \\
    // --------------- \\

    // ----------------- \\
    // TRANSPORT DEVICES \\
    // ----------------- \\
    // BUCKET \\
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

    // MODULE \\
    public static final ModuleDevice PRIMITIVE_CONVEYOR =
            new ModuleDevice(
                    DeviceIDs.PRIMITIVE_CONVEYOR,
                    new ItemData(
                            1,
                            5
                    )
            );

    // PIPE \\
    public static final PipeDevice BASIC_ITEM_PIPE =
            new PipeDevice(
                    DeviceIDs.BASIC_ITEM_PIPE,
                    PipeType.ITEM,
                    new ItemPipeData(
                            64,
                            1,
                            100
                    )
            );
}
