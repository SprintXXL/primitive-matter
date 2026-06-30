package com.SprintXXL.primitivematter.library.states.solid.forms;

import com.SprintXXL.primitivematter.library.shared.ColorRule;
import com.SprintXXL.primitivematter.library.shared.FormEntry;

import java.util.Set;

public interface SolidFormGroup {
    
    Set<? extends FormEntry<? extends SolidForm>> getForms();

    ColorRule getColorRule();
}
