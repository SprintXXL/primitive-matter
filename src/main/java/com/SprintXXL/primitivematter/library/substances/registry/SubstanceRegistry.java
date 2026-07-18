package com.SprintXXL.primitivematter.library.substances.registry;

import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.shared.FormEntry;
import com.SprintXXL.primitivematter.library.substances.states.liquid.LiquidState;
import com.SprintXXL.primitivematter.library.substances.states.solid.SolidState;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidForm;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidFormGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.*;

import static com.SprintXXL.primitivematter.Reference.MODID;
import static com.SprintXXL.primitivematter.library.substances.definitions.ModSubstances.initSubstanceDefinitions;

public final class SubstanceRegistry {

    private SubstanceRegistry() {}

    private static boolean initialized = false;

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

    public static Substance getSubstanceFromItem(ItemStack stack, SolidForm expectedForm) {

        if (stack.isEmpty()) {
            return null;
        }

        Item item = stack.getItem();

        for (Substance substance : ALL_SUBSTANCES) {

            SolidState solidState = substance.getSolidState();

            if (solidState == null) {
                continue;
            }

            for (SolidFormGroup group : solidState.getFormGroups()) {

                for (FormEntry<? extends SolidForm> entry : group.getForms()) {

                    if (entry.getForm() != expectedForm) {
                        continue;
                    }

                    ResourceLocation expectedRegistryName;

                    if (entry.hasBackingRegistryName()) {
                        expectedRegistryName = entry.getBackingRegistryName();
                    }
                    else {
                        expectedRegistryName = new ResourceLocation(
                                MODID,
                                entry.getForm().getName(substance)
                        );
                    }

                    ResourceLocation actualRegistryName = item.getRegistryName();

                    if (expectedRegistryName.equals(actualRegistryName)) {
                        return substance;
                    }
                }
            }
        }

        return null;
    }

    public static void register(Substance substance) {
        ALL_SUBSTANCES.add(substance);
        SUBSTANCES.put(substance.getID(), substance);
    }

    public static void initSubstanceRegistry() {

        if (initialized) {
            return;
        }

        initialized = true;

        initSubstanceDefinitions(SubstanceRegistry::register);
    }
}