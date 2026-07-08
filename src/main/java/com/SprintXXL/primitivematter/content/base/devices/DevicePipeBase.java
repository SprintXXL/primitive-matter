package com.SprintXXL.primitivematter.content.base.devices;

import com.SprintXXL.primitivematter.content.base.ResourceMode;
import com.SprintXXL.primitivematter.content.base.ResourceModeProvider;
import com.SprintXXL.primitivematter.library.devices.types.pipe.PipeDevice;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import static com.SprintXXL.primitivematter.Reference.MODID;

public class DevicePipeBase extends Block implements ResourceModeProvider {

    private final PipeDevice definition;

    public DevicePipeBase(PipeDevice definition) {
        super(Material.ANVIL);

        setRegistryName(MODID, definition.getID());
        setTranslationKey(MODID + "." + definition.getID());
        setCreativeTab(CreativeTabs.MISC);

        this.definition = definition;
    }

    @Override
    public ResourceMode getResourceMode() {
        return ResourceMode.HANDMADE;
    }

    public PipeDevice getDefinition() {
        return definition;
    }

    private static final AxisAlignedBB CENTER_AABB =
            new AxisAlignedBB(
                    4.0 / 16.0, 4.0 / 16.0, 4.0 / 16.0,
                    12.0 / 16.0, 12.0 / 16.0, 12.0 / 16.0
            );

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CENTER_AABB;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
}
