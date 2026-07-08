package com.SprintXXL.primitivematter.library.substances.registry;

import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.states.liquid.LiquidState;

import java.util.*;

import static com.SprintXXL.primitivematter.library.substances.definitions.ModSubstances.initModSubstances;

public final class SubstanceRegistry {

    private SubstanceRegistry() {}

    private static final List<Substance> ALL_SUBSTANCES = new ArrayList<>();
    private static final Map<String, Substance> SUBSTANCES = new HashMap<>();

    public static List<Substance> getAllSubstances() {
        return Collections.unmodifiableList(ALL_SUBSTANCES);
    }

    public static Substance getSubstance(String id) {
        return SUBSTANCES.get(id);
    }

    public static Substance getSubstanceFromLiquid(String liquidID) {

        for (Substance substance : ALL_SUBSTANCES) {

            LiquidState state = substance.getLiquidState();

            if (state == null) {
                continue;
            }

            if (state.getRegistryName().equals(liquidID)) {
                return substance;
            }
        }

        return null;
    }

    public static void register(Substance substance) {
        ALL_SUBSTANCES.add(substance);
        SUBSTANCES.put(substance.getID(), substance);
    }

    public static void initSubstanceRegistry() {

        initModSubstances();
    }
}