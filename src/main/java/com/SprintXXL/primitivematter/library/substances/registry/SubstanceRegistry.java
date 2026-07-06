package com.SprintXXL.primitivematter.library.substances.registry;

import com.SprintXXL.primitivematter.library.substances.Substance;

import java.util.*;

import static com.SprintXXL.primitivematter.library.substances.definitions.ModSubstances.initModSubstances;

public final class SubstanceRegistry {

    private SubstanceRegistry() {}

    private static final List<Substance> ALL_SUBSTANCES = new ArrayList<>();
    private static final Map<String, Substance> SUBSTANCES = new HashMap<>();

    private static final List<String> ALL_LIQUID_NAMES = new ArrayList<>();
    private static final List<String> ALL_GAS_NAMES = new ArrayList<>();
    private static final List<String> ALL_PLASMA_NAMES = new ArrayList<>();

    public static List<Substance> getAllSubstances() {
        return Collections.unmodifiableList(ALL_SUBSTANCES);
    }

    public static Substance getSubstance(String id) {
        return SUBSTANCES.get(id);
    }

    public static List<String> getAllLiquidNames() {
        return Collections.unmodifiableList(ALL_LIQUID_NAMES);
    }

    public static List<String> getAllGasNames() {
        return Collections.unmodifiableList(ALL_GAS_NAMES);
    }

    public static List<String> getAllPlasmaNames() {
        return Collections.unmodifiableList(ALL_PLASMA_NAMES);
    }

    public static void register(Substance substance) {
        ALL_SUBSTANCES.add(substance);
        SUBSTANCES.put(substance.getID(), substance);

        if (substance.hasLiquidState()) {
            ALL_LIQUID_NAMES.add(substance.getLiquidState().getRegistryName());
        }
        if (substance.hasGasState()) {
            ALL_GAS_NAMES.add(substance.getGasState().getRegistryName());
        }
        if (substance.hasPlasmaState()) {
            ALL_PLASMA_NAMES.add(substance.getPlasmaState().getRegistryName());
        }
    }

    public static void initSubstanceRegistry() {

        initModSubstances();
    }
}