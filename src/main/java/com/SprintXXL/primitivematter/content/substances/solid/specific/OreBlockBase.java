package com.SprintXXL.primitivematter.content.substances.solid.specific;

import com.SprintXXL.primitivematter.content.client.ResourceMode;
import com.SprintXXL.primitivematter.content.client.ResourceModeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Random;

import static com.SprintXXL.primitivematter.Reference.MODID;

public class OreBlockBase extends Block implements ResourceModeProvider {

    private final Item drop;

    public OreBlockBase(String name, Item drop, int harvestLevel) {
        super(Material.ROCK);

        this.drop = drop;

        setRegistryName(MODID, name);
        setTranslationKey(MODID + "." + name);
        setCreativeTab(CreativeTabs.MATERIALS);

        setHardness(3.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", harvestLevel);
    }

    @Override
    public ResourceMode getResourceMode() {
        return ResourceMode.GENERATED;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return drop;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0) {
            return quantityDropped(random) + random.nextInt(fortune + 1);
        }

        return quantityDropped(random);
    }
}
