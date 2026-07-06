package com.SprintXXL.primitivematter.content.base.devices;

import com.SprintXXL.primitivematter.content.base.substances.global.FluidBlockBase;
import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.nbt.BucketNBT;
import com.SprintXXL.primitivematter.library.devices.registry.DeviceRegistry;
import com.SprintXXL.primitivematter.library.devices.types.BucketData;
import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.registry.SubstanceRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import static com.SprintXXL.primitivematter.PrimitiveMatter.printInfo;
import static com.SprintXXL.primitivematter.Reference.MODID;
import static com.SprintXXL.primitivematter.library.devices.nbt.NBTHelper.UNKNOWN;

public class DeviceBucketBase extends ItemBucket {

    public DeviceBucketBase(String name) {
        super(Blocks.AIR);

        setRegistryName(MODID, name);
        setTranslationKey(MODID + "." + name);
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(1);
    }

    private static final int LITER = 1000;

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);

        RayTraceResult result = this.rayTrace(world, player, BucketNBT.isEmpty(stack));

        if (result == null || result.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        if (!BucketNBT.isEmpty(stack)) {
            return tryEmptyBucket(world, stack, result);
        }

        return tryFillBucket(world, stack, result);
    }

    private ActionResult<ItemStack> tryEmptyBucket(World world, ItemStack stack, RayTraceResult result) {

        BlockPos placePos = result.getBlockPos().offset(result.sideHit);
        String liquid = BucketNBT.getLiquid(stack);

        Fluid fluid = FluidRegistry.getFluid(liquid);

        if (fluid == null) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        Block liquidBlock = fluid.getBlock();

        if (liquidBlock == null) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        if (liquidBlock == Blocks.WATER) {
            liquidBlock = Blocks.FLOWING_WATER;
        }

        if (liquidBlock == Blocks.LAVA) {
            liquidBlock = Blocks.FLOWING_LAVA;
        }

        world.setBlockState(placePos, liquidBlock.getDefaultState());
        BucketNBT.removeAmount(stack, LITER);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private ActionResult<ItemStack> tryFillBucket(World world, ItemStack stack, RayTraceResult result) {

        BlockPos pos = result.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();

        if (!isValidLiquid(block)) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        String liquid = getLiquidID(block);

        if (liquid.equals(UNKNOWN)) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        if (!BucketNBT.isEmpty(stack) && !BucketNBT.getLiquid(stack).equals(liquid)) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        Device device = DeviceRegistry.getDeviceFromStack(stack);

        if (device == null) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        if (BucketNBT.getAmount(stack) + LITER > device.getCapacity()) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        BucketNBT.setLiquid(stack, liquid);
        BucketNBT.addAmount(stack, LITER);

        world.setBlockToAir(pos);

        printInfo("Filled bucket with: " + liquid);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private static boolean isValidLiquid(Block block) {

        if (block instanceof FluidBlockBase) {
            return true;
        }
        if (block == Blocks.WATER || block == Blocks.LAVA) {
            return true;
        }
        else {
            return false;
        }
    }

    private static String getLiquidID(Block block) {

        if (block == Blocks.WATER) {
            return "water";
        }

        if (block == Blocks.LAVA) {
            return "lava";
        }

        if (block instanceof FluidBlockBase) {
            return block.getRegistryName().getPath();
        }

        return UNKNOWN;
    }
}
