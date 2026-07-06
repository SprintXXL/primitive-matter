package com.SprintXXL.primitivematter.library.devices.registry;

import com.SprintXXL.primitivematter.library.devices.Device;
import net.minecraft.item.ItemStack;

import java.util.*;

import static com.SprintXXL.primitivematter.library.devices.definitions.ModDevices.initModDevices;

public final class DeviceRegistry {

    private DeviceRegistry() {}

    private static final List<Device> ALL_DEVICES = new ArrayList<>();

    private static final Map<String, Device> DEVICES = new HashMap<>();

    public static List<Device> getAllDevices() {
        return Collections.unmodifiableList(ALL_DEVICES);
    }

    public static Device getDevice(String id) {
        return DEVICES.get(id);
    }

    public static Device getDeviceFromStack(ItemStack stack) {

        if (stack.isEmpty()) {
            return null;
        }

        if (stack.getItem().getRegistryName() == null) {
            return null;
        }

        return getDevice(stack.getItem().getRegistryName().getPath());
    }

    public static void register(Device device) {
        ALL_DEVICES.add(device);
        DEVICES.put(device.getID(), device);
    }

    public static void initDeviceRegistry() {

        initModDevices();
    }
}
