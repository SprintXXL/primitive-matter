package com.SprintXXL.primitivematter.content.base.substances.global;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBlockBase extends BlockFluidClassic {

    public FluidBlockBase(Fluid fluid) {
        super(fluid, Material.WATER);

        setRegistryName(fluid.getName());
        setTranslationKey(fluid.getName());
    }
}
