package com.SprintXXL.primitivematter.library.devices.category.transport.module.data;

public class ItemData implements ModuleData {

    private final int amount;
    private final int interval;

    public ItemData(
            int amount,
            int interval
    ) {
        this.amount = amount;
        this.interval = interval;
    }

    public int getAmount() {
        return amount;
    }

    public int getInterval() {
        return interval;
    }
}
