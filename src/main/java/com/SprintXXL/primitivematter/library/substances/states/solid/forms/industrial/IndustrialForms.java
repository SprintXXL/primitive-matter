package com.SprintXXL.primitivematter.library.substances.states.solid.forms.industrial;

import com.SprintXXL.primitivematter.library.substances.shared.ColorRule;
import com.SprintXXL.primitivematter.library.substances.shared.FormEntries;
import com.SprintXXL.primitivematter.library.substances.shared.FormEntry;
import com.SprintXXL.primitivematter.library.substances.shared.FormSelection;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidFormGroup;

import java.util.HashSet;
import java.util.Set;

public class IndustrialForms implements SolidFormGroup {

    private final Set<FormEntry<IndustrialForm>> forms;

    public IndustrialForms(Object... entries) {

        this.forms = new HashSet<>();

        for (Object entry : entries) {

            if (entry instanceof IndustrialForm) {
                this.forms.add(FormEntries.custom((IndustrialForm) entry));
            }

            else if (entry instanceof FormEntry) {
                this.forms.add((FormEntry<IndustrialForm>) entry);
            }

            else {
                throw new IllegalArgumentException(
                        "Invalid IndustrialForms entry: " + entry
                );
            }
        }
    }

    public IndustrialForms(FormSelection all) {

        this.forms = new HashSet<>();

        for (IndustrialForm form : IndustrialForm.values()) {
            this.forms.add(FormEntries.custom(form));
        }
    }

    public Set<FormEntry<IndustrialForm>> getForms() {
        return forms;
    }

    public boolean hasForm(IndustrialForm form) {

        for (FormEntry<IndustrialForm> entry : forms) {
            if (entry.getForm() == form) {
                return true;
            }
        }

        return false;
    }

    private ColorRule colorRule = ColorRule.DEFAULT;

    public IndustrialForms overrideColorRule(ColorRule colorRule) {
        this.colorRule = colorRule;
        return this;
    }

    @Override
    public ColorRule getColorRule() {
        return colorRule;
    }
}
