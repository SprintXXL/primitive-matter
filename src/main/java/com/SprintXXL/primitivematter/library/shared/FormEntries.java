package com.SprintXXL.primitivematter.library.shared;

import com.SprintXXL.primitivematter.library.states.MatterForm;
import net.minecraft.util.ResourceLocation;

public final class FormEntries {

    private FormEntries() {}

    public static <T extends MatterForm> FormEntry<T> custom(T form) {
        return new FormEntry<>(form, null);
    }

    public static <T extends MatterForm> FormEntry<T> vanilla(
            T form,
            String path
    ) {
        return new FormEntry<>(form, new ResourceLocation("minecraft", path));
    }

    public static <T extends MatterForm> FormEntry<T> existing(
            T form,
            String nameSpace,
            String path
    ) {
        return new FormEntry<>(
                form,
                new ResourceLocation(nameSpace, path)
        );
    }
}
