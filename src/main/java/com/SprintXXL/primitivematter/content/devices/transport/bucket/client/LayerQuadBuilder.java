package com.SprintXXL.primitivematter.content.devices.transport.bucket.client;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.model.ItemLayerModel;

import java.util.List;
import java.util.Optional;

public class LayerQuadBuilder {

    public static List<BakedQuad> buildLayer(TextureAtlasSprite sprite) {

        return ItemLayerModel.getQuadsForSprite(
                0,
                sprite,
                DefaultVertexFormats.ITEM,
                Optional.empty()
        );
    }
}
