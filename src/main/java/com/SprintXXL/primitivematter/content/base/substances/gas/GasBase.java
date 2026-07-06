package com.SprintXXL.primitivematter.content.base.substances.gas;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class GasBase extends Fluid {

    public GasBase(
            String fluidName,
            ResourceLocation still,
            ResourceLocation flowing
    ) {
        super(fluidName, still, flowing);
    }
}
