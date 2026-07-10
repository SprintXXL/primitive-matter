package com.SprintXXL.primitivematter.content.client.helpers;

import com.SprintXXL.primitivematter.library.substances.Substance;
import net.minecraft.util.ResourceLocation;

import static com.SprintXXL.primitivematter.Reference.MODID;

public final class ForgeFluidTextureResolver {

    private ForgeFluidTextureResolver() {}

    public static ResourceLocation getLiquidStillTexture(Substance matter) {

        return new ResourceLocation(
                MODID,
                "generated/" + getTextureName(matter, "liquid", "still")
        );
    }

    public static ResourceLocation getLiquidFlowTexture(Substance matter) {

        return new ResourceLocation(
                MODID,
                "generated/" + getTextureName(matter, "liquid", "flow")
        );
    }

    private static String getTextureName(Substance matter, String state, String form) {
        return state + "_" + matter.getID() + "_" + form;
    }
}
