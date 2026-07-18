package com.SprintXXL.primitivematter.library.substances;

import com.SprintXXL.primitivematter.library.substances.shared.SubstanceCategory;
import com.SprintXXL.primitivematter.library.substances.states.SubstanceState;
import com.SprintXXL.primitivematter.library.substances.states.gas.GasState;
import com.SprintXXL.primitivematter.library.substances.states.liquid.LiquidState;
import com.SprintXXL.primitivematter.library.substances.states.plasma.PlasmaState;
import com.SprintXXL.primitivematter.library.substances.states.solid.SolidState;
import com.sprintxxl.ascenthub.definitions.AscentDefinition;

import java.util.Arrays;
import java.util.List;

public class Substance implements AscentDefinition {

    private final String id;
    private final String color;
    private final SubstanceCategory category;
    private final List<SubstanceState> substanceStates;

    public Substance(
            String id,
            String color,
            SubstanceCategory category,
            SubstanceState... substanceStates
    ) {
        this.id = id;
        this.color = color;
        this.category = category;
        this.substanceStates = Arrays.asList(substanceStates);
    }

    @Override
    public String getID() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public SubstanceCategory getSubstanceCategory() {
        return category;
    }

    public List<SubstanceState> getSubstanceStates() {
        return substanceStates;
    }

    // API METHODS \\
    public boolean hasSolidState() {
        return getSolidState() != null;
    }
    public boolean hasLiquidState() {
        return getLiquidState() != null;
    }
    public boolean hasGasState() {
        return getGasState() != null;
    }
    public boolean hasPlasmaState() {
        return getPlasmaState() != null;
    }

    public SolidState getSolidState() {

        for (SubstanceState state : substanceStates) {
            if (state instanceof SolidState) {
                return (SolidState) state;
            }
        }
        return null;
    }
    public LiquidState getLiquidState() {

        for (SubstanceState state : substanceStates) {
            if (state instanceof LiquidState) {
                return (LiquidState) state;
            }
        }
        return null;
    }
    public GasState getGasState() {

        for (SubstanceState state : substanceStates) {
            if (state instanceof GasState) {
                return (GasState) state;
            }
        }
        return null;
    }
    public PlasmaState getPlasmaState() {

        for (SubstanceState state : substanceStates) {
            if (state instanceof PlasmaState) {
                return (PlasmaState) state;
            }
        }
        return null;
    }
}
