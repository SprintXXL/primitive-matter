package com.SprintXXL.primitivematter.library.substances.states.solid.forms.industrial;

import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidForm;

public enum IndustrialForm implements SolidForm {

    PLATE("%s_plate"),
    DENSE_PLATE("dense_%s_plate"),
    ROD("%s_rod"),
    LONG_ROD("long_%s_rod"),
    BOLT("%s_bolt"),
    SCREW("%s_screw"),
    RING("%s_ring"),
    FINE_WIRE("fine_%s_wire"),
    GEAR("%s_gear"),
    SMALL_GEAR("small_%s_gear");

    private final String pattern;

    IndustrialForm(
            String pattern
    ) {
        this.pattern = pattern;
    }

    public String getName(Substance matter) {
        return String.format(pattern, matter.getID());
    }
}
