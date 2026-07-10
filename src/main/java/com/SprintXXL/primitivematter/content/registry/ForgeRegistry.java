package com.SprintXXL.primitivematter.content.registry;

import com.SprintXXL.primitivematter.PrimitiveMatter;
import com.SprintXXL.primitivematter.content.devices.pipe.BlockPipe;
import com.SprintXXL.primitivematter.content.devices.pipe.ItemPipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeRegistry {

    public static void registerCustomFluids() {

        for (Fluid fluid : ContentRegistry.getCustomFluids()) {
            FluidRegistry.registerFluid(fluid);

            PrimitiveMatter.LOGGER.info("Registered custom fluid: " + fluid.getName());
        }
    }

    @SubscribeEvent
    public void registerCustomItems(RegistryEvent.Register<Item> event) {

        for (Item item : ContentRegistry.getCustomItems()) {
            event.getRegistry().register(item);
        }

        for (Block block : ContentRegistry.getCustomBlocks()) {
            event.getRegistry().register(createItemBlock(block));
        }
    }

    @SubscribeEvent
    public void registerCustomBlocks(RegistryEvent.Register<Block> event) {

        for (Block block : ContentRegistry.getCustomBlocks()) {
            event.getRegistry().register(block);
        }
    }

    private static ItemBlock createItemBlock(Block block) {

        if (block instanceof BlockPipe) {
            return (ItemBlock) new ItemPipe(block)
                    .setRegistryName(block.getRegistryName());
        }

        return (ItemBlock) new ItemBlock(block)
                .setRegistryName(block.getRegistryName());
    }

}
