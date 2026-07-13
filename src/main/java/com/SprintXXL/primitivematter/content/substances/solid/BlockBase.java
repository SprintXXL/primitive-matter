package com.SprintXXL.primitivematter.content.substances.solid;

import com.SprintXXL.primitivematter.content.client.ResourceMode;
import com.SprintXXL.primitivematter.content.client.ResourceModeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import static com.SprintXXL.primitivematter.Reference.MODID;

public class BlockBase extends Block implements ResourceModeProvider {

    public BlockBase(String name) {
        super(Material.ANVIL);

        setRegistryName(MODID, name);
        setTranslationKey(MODID + "." + name);
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public ResourceMode getResourceMode() {
        return ResourceMode.GENERATED;
    }
}
