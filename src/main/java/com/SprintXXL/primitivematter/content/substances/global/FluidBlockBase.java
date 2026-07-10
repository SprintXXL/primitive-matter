package com.SprintXXL.primitivematter.content.substances.global;

import com.SprintXXL.primitivematter.content.ResourceMode;
import com.SprintXXL.primitivematter.content.ResourceModeProvider;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidBlockBase extends BlockFluidClassic implements ResourceModeProvider {

    public FluidBlockBase(Fluid fluid) {
        super(fluid, Material.WATER);

        setRegistryName(fluid.getName());
        setTranslationKey(fluid.getName());
    }

    @Override
    public ResourceMode getResourceMode() {
        return ResourceMode.GENERATED;
    }
}
