package com.SprintXXL.primitivematter.library.states.solid.forms.ore;

import com.SprintXXL.primitivematter.library.shared.ColorRule;
import com.SprintXXL.primitivematter.library.shared.FormEntries;
import com.SprintXXL.primitivematter.library.shared.FormEntry;
import com.SprintXXL.primitivematter.library.states.solid.forms.SolidFormGroup;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.SprintXXL.primitivematter.library.states.solid.forms.ore.OreForm.*;

public class OreVariant implements SolidFormGroup {

    private final Set<FormEntry<OreForm>> forms = new LinkedHashSet<>();

    private OreForm drop = RAW_ORE;
    private int harvestLevel = 0;
    private ColorRule colorRule = ColorRule.ORE;

    public OreVariant() {
        forms.add(FormEntries.custom(RAW_ORE));
        forms.add(FormEntries.custom(ORE_BLOCK));
        forms.add(FormEntries.custom(CRUSHED_ORE));
        forms.add(FormEntries.custom(DIRTY_DUST));
    }

    public OreVariant drop(OreForm drop) {
        this.drop = drop;
        return this;
    }

    public OreVariant harvestLevel(int harvestLevel) {
        this.harvestLevel = harvestLevel;
        return this;
    }

    public OreVariant colorRule(ColorRule colorRule) {
        this.colorRule = colorRule;
        return this;
    }

    public OreForm getDrop() {
        return drop;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public ColorRule getColorRule() {
        return colorRule;
    }

    @Override
    public Set<FormEntry<OreForm>> getForms() {
        return forms;
    }

    public boolean hasForm(OreForm form) {
        for (FormEntry<OreForm> entry : forms) {
            if (entry.getForm() == form) {
                return true;
            }
        }

        return false;
    }
}
