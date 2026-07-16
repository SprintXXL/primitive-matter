package com.SprintXXL.primitivematter.content.client;

import com.SprintXXL.primitivematter.content.registry.ContentRegistry;
import com.SprintXXL.primitivematter.content.substances.global.FluidBlockBase;
import com.SprintXXL.primitivematter.content.devices.transport.bucket.client.ModelBucket;
import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.registry.DeviceRegistry;
import com.SprintXXL.primitivematter.library.devices.category.transport.bucket.BucketDevice;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import static com.SprintXXL.primitivematter.Reference.MODID;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModelHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

        for (Item item : ContentRegistry.getCustomItems()) {
            registerItemModels(item);
        }

        for (Block block : ContentRegistry.getCustomBlocks()) {

            if (block instanceof FluidBlockBase) {
                continue;
            }

            registerBlockModels(block);
        }
    }

    private static void registerItemModels(Item item) {

        ResourceMode mode = ((ResourceModeProvider) item).getResourceMode();

        String folder = mode.name().toLowerCase();

        ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                new ModelResourceLocation(MODID + ":" + folder + "/" + item.getRegistryName().getPath(), "inventory")
        );
    }

    private static void registerBlockModels(Block block) {

        ResourceMode mode = ((ResourceModeProvider) block).getResourceMode();

        String folder = mode.name().toLowerCase();

        Item item = Item.getItemFromBlock(block);

        ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                new ModelResourceLocation(MODID + ":" + folder + "/" + item.getRegistryName().getPath(), "inventory")
        );
    }



    @SubscribeEvent
    public static void stitchFluidTexture(TextureStitchEvent.Pre event) {

        for (Fluid fluid : ContentRegistry.getCustomFluids()) {

            if (fluid.getStill() != null) {
                event.getMap().registerSprite(fluid.getStill());
            }

            if (fluid.getFlowing() != null) {
                event.getMap().registerSprite(fluid.getFlowing());
            }
        }
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {

        for (Device device : DeviceRegistry.getAllDevices()) {
            event.getMap().registerSprite(new ResourceLocation(MODID, "generated/" + device.getID()));
        }

        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            event.getMap().registerSprite(new ResourceLocation(MODID, "generated/bucket_overlay_" + fluid.getName()));
        }
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {

        for (Device device : DeviceRegistry.getAllDevices()) {

            if (device instanceof BucketDevice) {
                replaceBucketModel(event, device.getID());
            }
        }
    }

    private static void replaceBucketModel(ModelBakeEvent event, String modelName) {

        ModelResourceLocation location = new ModelResourceLocation(MODID + ":" + "generated/" + modelName, "inventory");

        IBakedModel model = event.getModelRegistry().getObject(location);

        if (model != null) {
            event.getModelRegistry().putObject(
                    location,
                    new ModelBucket(model, null)
            );
        }
    }
}
