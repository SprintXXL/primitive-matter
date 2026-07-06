package com.SprintXXL.primitivematter.library.substances.states.solid.forms.basic;

import com.SprintXXL.primitivematter.library.substances.shared.ColorRule;
import com.SprintXXL.primitivematter.library.substances.shared.FormEntries;
import com.SprintXXL.primitivematter.library.substances.shared.FormEntry;
import com.SprintXXL.primitivematter.library.substances.shared.FormSelection;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidFormGroup;

import java.util.HashSet;
import java.util.Set;

public class BasicForms implements SolidFormGroup {

    private final Set<FormEntry<BasicForm>> forms;

    public BasicForms(Object... entries) {

        this.forms = new HashSet<>();

        for (Object entry : entries) {

            if (entry instanceof BasicForm) {
                this.forms.add(FormEntries.custom((BasicForm) entry));
            }

            else if (entry instanceof FormEntry) {
                this.forms.add((FormEntry<BasicForm>) entry);
            }

            else {
                throw new IllegalArgumentException(
                        "Invalid BasicForms entry: " + entry
                );
            }
        }
    }

    public BasicForms(FormSelection all) {

        this.forms = new HashSet<>();

        for (BasicForm form : BasicForm.values()) {
            this.forms.add(FormEntries.custom(form));
        }
    }

    public Set<FormEntry<BasicForm>> getForms() {
        return forms;
    }

    public boolean hasForm(BasicForm form) {

        for (FormEntry<BasicForm> entry : forms) {
            if (entry.getForm() == form) {
                return true;
            }
        }

        return false;
    }

    private ColorRule colorRule = ColorRule.DEFAULT;

    public BasicForms overrideColorRule(ColorRule colorRule) {
        this.colorRule = colorRule;
        return this;
    }

    @Override
    public ColorRule getColorRule() {
        return colorRule;
    }
}
