package com.SprintXXL.primitivematter.content.base.substances.solid;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import static com.SprintXXL.primitivematter.Reference.MODID;

public class ItemBase extends Item {

    public ItemBase(String name) {

        setRegistryName(MODID, name);
        setTranslationKey(MODID + "." + name);
        setCreativeTab(CreativeTabs.MISC);
    }
}
