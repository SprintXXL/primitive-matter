package com.SprintXXL.primitivematter.library.substances.states.plasma;

import com.SprintXXL.primitivematter.library.substances.states.FluidState;
import com.SprintXXL.primitivematter.library.substances.states.StateProperties;
import com.SprintXXL.primitivematter.library.substances.states.SubstanceState;

public class PlasmaState implements SubstanceState, FluidState {

    private final StateProperties properties;
    private final String registryName;

    public PlasmaState(
            StateProperties properties,
            String registryName
    ) {
        this.properties = properties;
        this.registryName = registryName;
    }

    public StateProperties getProperties() {
        return properties;
    }

    public String getRegistryName() {
        return registryName;
    }
}
