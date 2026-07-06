package com.SprintXXL.primitivematter.library.substances.shared;

import com.SprintXXL.primitivematter.library.substances.states.SubstanceForm;
import net.minecraft.util.ResourceLocation;

public class FormEntry<T extends SubstanceForm> {

    private final T form;
    private final ResourceLocation backingRegistryName;

    public FormEntry(
            T form,
            ResourceLocation backingRegistryName
    ) {
        this.form = form;
        this.backingRegistryName = backingRegistryName;
    }

    public T getForm() {
        return form;
    }

    public ResourceLocation getBackingRegistryName() {
        return backingRegistryName;
    }

    public boolean hasBackingRegistryName() {
        return backingRegistryName != null;
    }
}
