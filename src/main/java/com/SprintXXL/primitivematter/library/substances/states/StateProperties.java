package com.SprintXXL.primitivematter.library.substances.states;

public class StateProperties {

    private final int minTemperature;
    private final int maxTemperature;

    public StateProperties(
            int minTemperature,
            int maxTemperature
    ) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }
}
