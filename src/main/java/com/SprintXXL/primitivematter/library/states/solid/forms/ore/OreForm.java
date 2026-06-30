package com.SprintXXL.primitivematter.library.states.solid.forms.ore;

import com.SprintXXL.primitivematter.library.Matter;
import com.SprintXXL.primitivematter.library.states.solid.forms.SolidForm;

public enum OreForm implements SolidForm {
    
    ORE_BLOCK("%s_ore"),
    RAW_ORE("raw_%s"),
    CRUSHED_ORE("crushed_%s"),
    DIRTY_DUST("dirty_%s_dust");

    private final String pattern;

    OreForm(
            String pattern
    ) {
        this.pattern = pattern;
    }

    public String getName(Matter matter) {
        return String.format(pattern, matter.getID());
    }
}
