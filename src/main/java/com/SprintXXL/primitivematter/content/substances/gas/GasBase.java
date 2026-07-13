package com.SprintXXL.primitivematter.content.substances.gas;

import com.SprintXXL.primitivematter.content.client.ResourceMode;
import com.SprintXXL.primitivematter.content.client.ResourceModeProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class GasBase extends Fluid implements ResourceModeProvider {

    public GasBase(
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
