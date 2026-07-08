package com.SprintXXL.primitivematter.content.base.substances.plasma;

import com.SprintXXL.primitivematter.content.base.ResourceMode;
import com.SprintXXL.primitivematter.content.base.ResourceModeProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class PlasmaBase extends Fluid implements ResourceModeProvider {

    public PlasmaBase(
            String fluidName,
            ResourceLocation still,
            ResourceLocation flowing
    ) {
        super(fluidName, still, flowing);
    }

    @Override
    public ResourceMode getResourceMode() {
        return ResourceMode.GENERATED;
    }
}
