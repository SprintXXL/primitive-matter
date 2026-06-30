package com.SprintXXL.primitivematter.library.shared;

import com.SprintXXL.primitivematter.library.states.MatterForm;
import net.minecraft.util.ResourceLocation;

public class FormEntry<T extends MatterForm> {

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
