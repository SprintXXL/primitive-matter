package com.SprintXXL.primitivematter.content.client.render.devices.buckets;

public class BucketRenderData {

    public final String deviceID;
    public final String liquid;
    public final boolean hasLiquid;

    public BucketRenderData(
            String deviceID,
            String liquid,
            boolean hasLiquid
    ) {
        this.deviceID = deviceID;
        this.liquid = liquid;
        this.hasLiquid = hasLiquid;
    }
}
