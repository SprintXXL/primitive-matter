package com.SprintXXL.primitivematter.content.base.substances.liquid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class LiquidBase extends Fluid {

    public LiquidBase(
            String fluidName,
            ResourceLocation still,
            ResourceLocation flowing
    ) {
        super(fluidName, still, flowing);
    }
}
