package com.SprintXXL.primitivematter.library.substances.states.solid.forms.basic;

import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidForm;

public enum BasicForm implements SolidForm {

    SUBSTANCE_BLOCK("%s_block"),
    SUBSTANCE_ITEM("%s"),
    INGOT("%s_ingot"),
    NUGGET("%s_nugget"),
    DUST("%s_dust");

    private final String pattern;

    BasicForm(
            String pattern
    ) {
        this.pattern = pattern;
    }

    public String getName(Substance substance) {
        return String.format(pattern, substance.getID());
    }
}
