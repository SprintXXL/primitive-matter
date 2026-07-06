package com.SprintXXL.primitivematter.library.substances.states.solid;

import com.SprintXXL.primitivematter.library.substances.states.SubstanceState;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidFormGroup;

import java.util.Arrays;
import java.util.List;

public class SolidState implements SubstanceState {

    private final List<SolidFormGroup> formGroups;

    public SolidState(
            SolidFormGroup... formGroups
    ) {
        this.formGroups = Arrays.asList(formGroups);
    }

    public List<SolidFormGroup> getFormGroups() {
        return formGroups;
    }
}
