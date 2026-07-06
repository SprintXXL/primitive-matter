package com.SprintXXL.primitivematter.library.substances.states.liquid;

import com.SprintXXL.primitivematter.library.substances.states.FluidState;
import com.SprintXXL.primitivematter.library.substances.states.SubstanceState;

public class LiquidState implements SubstanceState, FluidState {

    private final String registryName;
    private boolean vanilla = false;

    public LiquidState(
            String registryName
    ) {
        this.registryName = registryName;
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
