package com.SprintXXL.primitivematter.library.states.solid;

import com.SprintXXL.primitivematter.library.states.MatterState;
import com.SprintXXL.primitivematter.library.states.solid.forms.SolidFormGroup;

import java.util.Arrays;
import java.util.List;

public class SolidState implements MatterState {

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
