package com.SprintXXL.primitivematter.library.substances.states.liquid;

import com.SprintXXL.primitivematter.library.substances.states.FluidState;
import com.SprintXXL.primitivematter.library.substances.states.StateProperties;
import com.SprintXXL.primitivematter.library.substances.states.SubstanceState;

public class LiquidState implements SubstanceState, FluidState {

    private final StateProperties properties;
    private final String registryName;
    private boolean vanilla = false;

    public LiquidState(
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

    public LiquidState vanilla() {
        this.vanilla = true;
        return this;
    }

    public boolean isVanilla() {
        return vanilla;
    }
}
