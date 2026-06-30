package com.SprintXXL.primitivematter.content.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import static com.SprintXXL.primitivematter.Reference.MODID;

public class BlockBase extends Block {

    public BlockBase(String name) {
        super(Material.ANVIL);

        setRegistryName(MODID, name);
        setTranslationKey(MODID + "." + name);
        setCreativeTab(CreativeTabs.MISC);
    }
}
