package com.SprintXXL.primitivematter.content;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeRegistry {

    @SubscribeEvent
    public void registerCustomItems(RegistryEvent.Register<Item> event) {

        for (Item item : MatterContent.getCustomSolidItems()) {
            event.getRegistry().register(item);
        }

        for (Block block : MatterContent.getCustomSolidBlocks()) {
            event.getRegistry().register(createItemBlock(block));
        }
    }

    @SubscribeEvent
    public void registerCustomBlocks(RegistryEvent.Register<Block> event) {

        for (Block block : MatterContent.getCustomSolidBlocks()) {
            event.getRegistry().register(block);
        }
    }

    private static ItemBlock createItemBlock(Block block) {
        return (ItemBlock) new ItemBlock(block)
                .setRegistryName(block.getRegistryName());
    }
}
