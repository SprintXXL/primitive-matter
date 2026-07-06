package com.SprintXXL.primitivematter.content.base.substances.plasma;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class PlasmaBase extends Fluid {

    public PlasmaBase(
            String fluidName,
            ResourceLocation still,
            ResourceLocation flowing
    ) {
        super(fluidName, still, flowing);
    }
}
