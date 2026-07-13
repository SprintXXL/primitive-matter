package com.SprintXXL.primitivematter.content.devices.transport.bucket.client;

import com.SprintXXL.primitivematter.library.devices.nbt.BucketNBT;
import net.minecraft.item.ItemStack;

public class BucketRenderResolver {

    public static BucketRenderData resolve(ItemStack stack) {

        String liquid = BucketNBT.getLiquid(stack);
        boolean hasLiquid = !BucketNBT.isEmpty(stack);
        String deviceID = stack.getItem().getRegistryName().getPath();

        return new BucketRenderData(
                deviceID,
                liquid,
                hasLiquid
        );
    }
}
