package com.SprintXXL.primitivematter.library.devices.transport.pipe.data;

public class ItemPipeData implements PipeData {

    private final int capacity;
    private final int transferAmount;
    private final int transferInterval;

    public ItemPipeData(
            int capacity,
            int transferAmount,
            int transferInterval
    ) {
        this.capacity = capacity;
        this.transferAmount = transferAmount;
        this.transferInterval = transferInterval;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public int getTransferInterval() {
        return transferInterval;
    }
}
