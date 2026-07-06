package com.SprintXXL.primitivematter.library.substances.states.plasma;

import com.SprintXXL.primitivematter.library.substances.states.FluidState;
import com.SprintXXL.primitivematter.library.substances.states.SubstanceState;

public class PlasmaState implements SubstanceState, FluidState {

    private final String registryName;

    public PlasmaState(
            String registryName
    ) {
        this.registryName = registryName;
    }

    public String getRegistryName() {
        return registryName;
    }
}
