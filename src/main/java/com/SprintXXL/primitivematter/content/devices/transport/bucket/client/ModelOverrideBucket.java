package com.SprintXXL.primitivematter.content.devices.transport.bucket.client;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Collections;

public class ModelOverrideBucket extends ItemOverrideList {

    private final IBakedModel model;

    public ModelOverrideBucket(IBakedModel model) {
        super(Collections.emptyList());

        this.model = model;
    }

    @Override
    public IBakedModel handleItemState(IBakedModel model, ItemStack stack, World world, EntityLivingBase entity) {

        BucketRenderData data = BucketRenderResolver.resolve(stack);

        return new ModelBucket(this.model, data);
    }
}
