package com.SprintXXL.primitivematter.library.substances.states.solid;

import com.SprintXXL.primitivematter.library.substances.states.StateProperties;
import com.SprintXXL.primitivematter.library.substances.states.SubstanceState;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidFormGroup;

import java.util.Arrays;
import java.util.List;

public class SolidState implements SubstanceState {

    private final StateProperties properties;
    private final List<SolidFormGroup> formGroups;

    public SolidState(
            StateProperties properties,
            SolidFormGroup... formGroups
    ) {
        this.properties = properties;
        this.formGroups = Arrays.asList(formGroups);
    }

    public StateProperties getProperties() {
        return properties;
    }

    public List<SolidFormGroup> getFormGroups() {
        return formGroups;
    }
}
