package com.SprintXXL.primitivematter.content.devices.transport.pipe.base;

import com.SprintXXL.primitivematter.content.client.ResourceMode;
import com.SprintXXL.primitivematter.content.client.ResourceModeProvider;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.PipeNetworkManager;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.PipeNetworkTickHandler;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.util.PipeSideMode;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.raytrace.PipeHitResult;
import com.SprintXXL.primitivematter.library.devices.transport.pipe.PipeDevice;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

import javax.annotation.Nullable;

import static com.SprintXXL.primitivematter.PrimitiveMatter.printInfo;
import static com.SprintXXL.primitivematter.Reference.MODID;
import static com.SprintXXL.primitivematter.content.devices.transport.pipe.debug.DebugPipeNetwork.testPathFinding;
import static com.SprintXXL.primitivematter.content.devices.transport.pipe.raytrace.PipeAABB.*;
import static com.SprintXXL.primitivematter.content.devices.transport.pipe.util.PipeSideMode.*;
import static com.SprintXXL.primitivematter.content.devices.transport.pipe.util.PipeUtil.*;
import static com.SprintXXL.primitivematter.content.devices.transport.pipe.raytrace.PipeRayTracer.tracePipeParts;

public class BlockPipe extends Block implements ResourceModeProvider {

    public static final PropertyEnum<PipeSideMode> UP = PropertyEnum.create("up", PipeSideMode.class);
    public static final PropertyEnum<PipeSideMode> DOWN = PropertyEnum.create("down", PipeSideMode.class);
    public static final PropertyEnum<PipeSideMode> NORTH = PropertyEnum.create("north", PipeSideMode.class);
    public static final PropertyEnum<PipeSideMode> SOUTH = PropertyEnum.create("south", PipeSideMode.class);
    public static final PropertyEnum<PipeSideMode> EAST = PropertyEnum.create("east", PipeSideMode.class);
    public static final PropertyEnum<PipeSideMode> WEST = PropertyEnum.create("west", PipeSideMode.class);

    private final PipeDevice definition;

    public BlockPipe(PipeDevice definition) {
        super(Material.ANVIL);

        setRegistryName(MODID, definition.getID());
        setTranslationKey(MODID + "." + definition.getID());
        setCreativeTab(CreativeTabs.MISC);
        setDefaultState(
                blockState.getBaseState()
                        .withProperty(UP, DISCONNECTED)
                        .withProperty(DOWN, DISCONNECTED)
                        .withProperty(NORTH, DISCONNECTED)
                        .withProperty(SOUTH, DISCONNECTED)
                        .withProperty(EAST, DISCONNECTED)
                        .withProperty(WEST, DISCONNECTED)
        );

        this.definition = definition;
    }

    @Override
    public ResourceMode getResourceMode() {
        return ResourceMode.HANDMADE;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityPipe();
    }

    public PipeDevice getDefinition() {
        return definition;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

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

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state
                .withProperty(UP, getSideState(world, pos, EnumFacing.UP))
                .withProperty(DOWN, getSideState(world, pos, EnumFacing.DOWN))
                .withProperty(NORTH, getSideState(world, pos, EnumFacing.NORTH))
                .withProperty(SOUTH, getSideState(world, pos, EnumFacing.SOUTH))
                .withProperty(EAST, getSideState(world, pos, EnumFacing.EAST))
                .withProperty(WEST, getSideState(world, pos, EnumFacing.WEST));

    }

    private PipeSideMode getSideState(IBlockAccess world, BlockPos pos, EnumFacing side) {

        if (!canRenderConnection(world, pos, side)) {
            return PipeSideMode.DISCONNECTED;
        }

        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityPipe) {
            return ((TileEntityPipe) tile).getMode(side);
        }

        return PipeSideMode.DISCONNECTED;
    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(
            IBlockState state,
            World world,
            BlockPos pos,
            Vec3d start,
            Vec3d end
    ) {

        PipeHitResult result = tracePipeParts(world, pos, start, end);

        return result == null ? null : result.getHit();
    }

    @Override
    public boolean onBlockActivated(
            World world,
            BlockPos pos,
            IBlockState state,
            EntityPlayer player,
            EnumHand hand,
            EnumFacing facing,
            float hitX,
            float hitY,
            float hitZ
    ) {

        if (hand != EnumHand.MAIN_HAND) {
            return true;
        }

        ItemStack stack = player.getHeldItem(hand);

        if (isWrench(stack)) {

            if (!world.isRemote) {
                PipeNetworkManager manager = PipeNetworkTickHandler.getManager(world);
                if (player.isSneaking()) {
                    togglePullMode(world, pos, facing);
                    manager.rebuildNetwork(world, pos);
                    printInfo("Pull mode changed: " + pos);
                } else {
                    toggleConnection(world, pos, facing);
                    manager.rebuildNetwork(world, pos);
                    printInfo("Connection changed: " + pos);
                }
            }

            return true;
        }

        if (!(stack.getItem() instanceof ItemBlock)) {
            return false;
        }

        if (stack.getItem() instanceof ItemPipe) {
            return false;
        }

        EnumActionResult result = stack.getItem().onItemUse(
                player,
                world,
                pos,
                hand,
                facing,
                hitX,
                hitY,
                hitZ
        );

        if (result != EnumActionResult.SUCCESS) {
            return false;
        }

        tryAutoConnect(world, pos, facing);

        return true;
    }

    private static boolean isWrench(ItemStack stack) {

        if (stack.isEmpty()) {
            return false;
        }

        return new ResourceLocation("primitiveutilitytools", "wrench")
                .equals(stack.getItem().getRegistryName());
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);

        if (!world.isRemote) {
            PipeNetworkManager manager = PipeNetworkTickHandler.getManager(world);
            manager.rebuildNetwork(world, pos);
            printInfo("Pipe destroyed: " + pos);
        }
    }
}
