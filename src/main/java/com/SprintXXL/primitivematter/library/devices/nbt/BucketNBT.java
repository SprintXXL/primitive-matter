package com.SprintXXL.primitivematter.library.devices.nbt;

import net.minecraft.item.ItemStack;

import static com.SprintXXL.primitivematter.library.devices.nbt.NBTHelper.*;

public final class BucketNBT {

    private BucketNBT() {}

    private static final String COMPOUND = "BucketData";

    private static final String LIQUID = "Liquid";
    private static final String AMOUNT = "Amount";

    public static void setLiquid(ItemStack stack, String liquid) {
        setString(stack, COMPOUND, LIQUID, liquid);
    }

    public static String getLiquid(ItemStack stack) {
        return getString(stack, COMPOUND, LIQUID);
    }

    public static void setAmount(ItemStack stack, int amount) {
        setInteger(stack, COMPOUND, AMOUNT, amount);
    }

    public static int getAmount(ItemStack stack) {
        return getInteger(stack, COMPOUND, AMOUNT);
    }

    public static void addAmount(ItemStack stack, int amount) {
        setAmount(stack, getAmount(stack) + amount);
    }

    public static void removeAmount(ItemStack stack, int amount) {
        int newAmount = getAmount(stack) - amount;

        if (newAmount <= 0) {
            clearContent(stack);
            return;
        }

        setAmount(stack, newAmount);
    }

    public static void clearContent(ItemStack stack) {
        setLiquid(stack, UNKNOWN);
        setAmount(stack, 0);
    }

    public static boolean isEmpty(ItemStack stack) {
        return getAmount(stack) <= 0 || getLiquid(stack).equals(UNKNOWN);
    }
}
