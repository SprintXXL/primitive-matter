package com.SprintXXL.primitivematter.content.substances.solid;

import com.SprintXXL.primitivematter.content.client.ResourceMode;
import com.SprintXXL.primitivematter.content.client.ResourceModeProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import static com.SprintXXL.primitivematter.Reference.MODID;

public class ItemBase extends Item implements ResourceModeProvider {

    public ItemBase(String name) {

        setRegistryName(MODID, name);
        setTranslationKey(MODID + "." + name);
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public ResourceMode getResourceMode() {
        return ResourceMode.GENERATED;
    }
}
