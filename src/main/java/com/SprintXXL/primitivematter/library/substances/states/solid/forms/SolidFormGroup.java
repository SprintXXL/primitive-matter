package com.SprintXXL.primitivematter.library.substances.states.solid.forms;

import com.SprintXXL.primitivematter.library.substances.shared.ColorRule;
import com.SprintXXL.primitivematter.library.substances.shared.FormEntry;

import java.util.Set;

public interface SolidFormGroup {
    
    Set<? extends FormEntry<? extends SolidForm>> getForms();

    ColorRule getColorRule();
}
