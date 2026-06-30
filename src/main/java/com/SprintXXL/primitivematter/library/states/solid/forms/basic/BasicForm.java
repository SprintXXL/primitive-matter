package com.SprintXXL.primitivematter.library.states.solid.forms.basic;

import com.SprintXXL.primitivematter.library.Matter;
import com.SprintXXL.primitivematter.library.states.solid.forms.SolidForm;

public enum BasicForm implements SolidForm {

    MATTER_BLOCK("%s_block"),
    MATTER_ITEM("%s"),
    INGOT("%s_ingot"),
    NUGGET("%s_nugget"),
    DUST("%s_dust");

    private final String pattern;

    BasicForm(
            String pattern
    ) {
        this.pattern = pattern;
    }

    public String getName(Matter matter) {
        return String.format(pattern, matter.getID());
    }
}
