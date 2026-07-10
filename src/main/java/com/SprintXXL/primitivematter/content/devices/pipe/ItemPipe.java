package com.SprintXXL.primitivematter.content.devices.pipe;

import com.SprintXXL.primitivematter.content.ResourceMode;
import com.SprintXXL.primitivematter.content.ResourceModeProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPipe extends ItemBlock implements ResourceModeProvider {

    public ItemPipe(Block block) {
        super(block);

        setTranslationKey(block.getTranslationKey());
    }

    @Override
    public ResourceMode getResourceMode() {
        return ResourceMode.HANDMADE;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        EnumActionResult result = super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);

        if (result != EnumActionResult.SUCCESS) {
            return result;
        }

        BlockPos newPipePos = pos.offset(facing);
        TileEntity tile = world.getTileEntity(newPipePos);

        if (!(tile instanceof TileEntityPipe)) {
            return result;
        }

        PipeUtil.tryAutoConnect(world, newPipePos, facing.getOpposite());

        TileEntity neighborTile = world.getTileEntity(pos);

        if (neighborTile instanceof TileEntityPipe) {

            PipeUtil.tryAutoConnect(world, pos, facing);
        }

        return result;
    }
}
