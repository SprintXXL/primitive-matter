package com.SprintXXL.primitivematter.library.substances.states.gas;

import com.SprintXXL.primitivematter.library.substances.states.FluidState;
import com.SprintXXL.primitivematter.library.substances.states.SubstanceState;

public class GasState implements SubstanceState, FluidState {

    private final String registryName;

    public GasState(
            String registryName
    ) {
        this.registryName = registryName;
    }

    public String getRegistryName() {
        return registryName;
    }
}
