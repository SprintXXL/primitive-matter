package com.SprintXXL.primitivematter.library.substances.states.solid.forms.ore;

import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidForm;

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

    public String getName(Substance matter) {
        return String.format(pattern, matter.getID());
    }
}
