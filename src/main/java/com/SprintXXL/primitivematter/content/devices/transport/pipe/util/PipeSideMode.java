package com.SprintXXL.primitivematter.content.devices.transport.pipe.util;

import net.minecraft.util.IStringSerializable;

public enum PipeSideMode implements IStringSerializable {

    CONNECTED("connected"),
    DISCONNECTED("disconnected"),
    PULL("pull");

    private final String name;

    PipeSideMode(
            String name
    ) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public PipeSideMode toggleConnected() {

        switch(this) {
            case DISCONNECTED:
                return CONNECTED;
            case CONNECTED:
            case PULL:
                return DISCONNECTED;
            default:
                return DISCONNECTED;
        }
    }

    public PipeSideMode togglePull() {

        switch(this) {
            case CONNECTED:
                return PULL;
            case PULL:
                return CONNECTED;
            case DISCONNECTED:
            default:
                return DISCONNECTED;
        }
    }
}
