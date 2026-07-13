package com.SprintXXL.primitivematter.content.devices.transport.bucket.client;

import net.minecraft.util.ResourceLocation;

import static com.SprintXXL.primitivematter.Reference.MODID;

public class BucketTextureResolver {

    public static ResourceLocation getBucketTexture(BucketRenderData data, String layerType) {

        if (layerType.equals("base")) {
            return new ResourceLocation(
                    MODID,
                    "generated/" + data.deviceID
            );
        }

        if (layerType.equals("overlay")) {
            return new ResourceLocation(
                    MODID,
                    "generated/bucket_overlay_" + data.liquid
            );
        }

        return null;
    }
}
