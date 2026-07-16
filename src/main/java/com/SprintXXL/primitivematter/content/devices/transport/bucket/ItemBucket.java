package com.SprintXXL.primitivematter.content.devices.transport.bucket;

import com.SprintXXL.primitivematter.content.client.ResourceMode;
import com.SprintXXL.primitivematter.content.client.ResourceModeProvider;
import com.SprintXXL.primitivematter.content.substances.global.FluidBlockBase;
import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.nbt.BucketNBT;
import com.SprintXXL.primitivematter.library.devices.registry.DeviceRegistry;
import com.SprintXXL.primitivematter.library.devices.category.transport.bucket.BucketDevice;
import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.registry.SubstanceRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.List;

import static com.SprintXXL.primitivematter.Reference.MODID;
import static com.SprintXXL.primitivematter.library.devices.nbt.NBTHelper.UNKNOWN;

public class ItemBucket extends net.minecraft.item.ItemBucket implements ResourceModeProvider {

    private static final int LITER = 1000;

    public ItemBucket(String name) {
        super(Blocks.AIR);

        setRegistryName(MODID, name);
        setTranslationKey(MODID + "." + name);
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(1);
    }

    @Override
    public ResourceMode getResourceMode() {
        return ResourceMode.GENERATED;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        String liquid = BucketNBT.getLiquid(stack);

        tooltip.add("Contains: " + toDisplayName(liquid));
    }

    private static String toDisplayName(String id) {

        String[] words = id.split("_");

        StringBuilder builder = new StringBuilder();

        for (String word : words) {

            builder.append(Character.toUpperCase(word.charAt(0)));
            builder.append(word.substring(1));
        }

        return builder.toString();
    }

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

        if (isTooHotForBucket(stack, liquid)) {
            stack.shrink(1);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }

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

        BucketDevice bucket = (BucketDevice) device;

        if (BucketNBT.getAmount(stack) + LITER > bucket.getCapacity()) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        BucketNBT.setLiquid(stack, liquid);
        BucketNBT.addAmount(stack, LITER);

        world.setBlockToAir(pos);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private static boolean isTooHotForBucket(ItemStack stack, String liquid) {

        Substance substance = SubstanceRegistry.getSubstanceFromLiquid(liquid);
        Device device = DeviceRegistry.getDeviceFromStack(stack);

        if (substance == null || device == null) {
            return false;
        }

        if (!substance.hasLiquidState()) {
            return false;
        }

        if (!(stack.getItem() instanceof ItemBucket)) {
            return false;
        }

        BucketDevice bucket = (BucketDevice) device;

        int liquidTemp = substance.getLiquidState().getProperties().getMinTemperature();
        int bucketMaxTemp = bucket.getMaxTemperature();

        if (liquidTemp > bucketMaxTemp) {
            return true;
        }

        return false;
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
            return FluidRegistry.WATER.getName();
        }

        if (block == Blocks.LAVA) {
            return FluidRegistry.LAVA.getName();
        }

        if (block instanceof FluidBlockBase) {
            return block.getRegistryName().getPath();
        }

        return UNKNOWN;
    }
}
