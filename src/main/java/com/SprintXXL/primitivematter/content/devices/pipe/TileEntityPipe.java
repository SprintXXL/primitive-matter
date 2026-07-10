package com.SprintXXL.primitivematter.content.devices.pipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

import static com.SprintXXL.primitivematter.PrimitiveMatter.printInfo;
import static net.minecraftforge.items.ItemHandlerHelper.insertItem;

public class TileEntityPipe extends TileEntity implements ITickable {

    // -------- \\
    // TICKABLE \\
    // -------- \\
    @Override
    public void update() {

        if (world == null || world.isRemote) {
            return;
        }

        if (hasCarriedItem()) {
            tryOutputCarriedItem();
            return;
        }

        EnumFacing pullSide = getPullSide();

        if (pullSide == null) {
            return;
        }

        BlockPos neighborPos = pos.offset(pullSide);
        TileEntity neighborTile = world.getTileEntity(neighborPos);

        if (neighborTile == null) {
            return;
        }

        IItemHandler handler = neighborTile.getCapability(
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                pullSide.getOpposite()
        );

        if (handler == null) {
            handler = neighborTile.getCapability(
                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    null
            );
        }

        if (handler == null) {
            return;
        }

        for (int slot = 0; slot < handler.getSlots(); slot++) {
            ItemStack extracted = handler.extractItem(slot, 1, false);

            if (extracted.isEmpty()) {
                continue;
            }

            setCarriedItem(extracted);

            break;
        }
    }

    private void tryOutputCarriedItem() {

        EnumFacing pullSide = getPullSide();

        if (pullSide == null) {
            return;
        }

        EnumFacing outputSide = pullSide.getOpposite();
        BlockPos outputPos = pos.offset(outputSide);

        TileEntity outputTile = world.getTileEntity(outputPos);

        if (outputTile == null) {
            return;
        }

        IItemHandler handler = outputTile.getCapability(
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                pullSide.getOpposite()
        );

        if (handler == null) {
            handler = outputTile.getCapability(
                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    null
            );
        }

        if (handler == null) {
            return;
        }

        ItemStack remainder = insertItem(handler, carriedItem, false);

        setCarriedItem(remainder);
    }

    // --- \\
    // NBT \\
    // --- \\
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        // SIDES \\
        compound.setString("up", up.getName());
        compound.setString("down", down.getName());
        compound.setString("north", north.getName());
        compound.setString("south", south.getName());
        compound.setString("east", east.getName());
        compound.setString("west", west.getName());

        // ITEMS \\
        NBTTagCompound itemTag = new NBTTagCompound();

        carriedItem.writeToNBT(itemTag);

        compound.setTag("carriedItem", itemTag);

        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        // SIDES \\
        up = readMode(compound, "up");
        down = readMode(compound, "down");
        north = readMode(compound, "north");
        south = readMode(compound, "south");
        east = readMode(compound, "east");
        west = readMode(compound, "west");

        // ITEMS \\
        if (compound.hasKey("carriedItem")) {

            NBTTagCompound itemTag = compound.getCompoundTag("carriedItem");

            carriedItem = new ItemStack(itemTag);

            if (!carriedItem.isEmpty()) {
                printInfo("Loaded carried item: " + carriedItem.getCount() + " " + carriedItem.getItem().getRegistryName());
            }

        } else {

            carriedItem = ItemStack.EMPTY;
        }
    }

    // ----- \\
    // SIDES \\
    // ----- \\
    private PipeSideMode up = PipeSideMode.DISCONNECTED;
    private PipeSideMode down = PipeSideMode.DISCONNECTED;
    private PipeSideMode north = PipeSideMode.DISCONNECTED;
    private PipeSideMode south = PipeSideMode.DISCONNECTED;
    private PipeSideMode east = PipeSideMode.DISCONNECTED;
    private PipeSideMode west = PipeSideMode.DISCONNECTED;

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {

        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        handleUpdateTag(pkt.getNbtCompound());

        if (world != null) {
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound) {
        readFromNBT(compound);
    }

    @Nullable
    public EnumFacing getPullSide() {

        for (EnumFacing side : EnumFacing.values()) {
            if (getMode(side) == PipeSideMode.PULL) {
                return side;
            }
        }

        return null;
    }

    public PipeSideMode getMode(EnumFacing side) {

        switch(side) {
            case UP:
                return up;
            case DOWN:
                return down;
            case NORTH:
                return north;
            case SOUTH:
                return south;
            case EAST:
                return east;
            case WEST:
                return west;
            default:
                return PipeSideMode.DISCONNECTED;
        }
    }

    public void setMode(EnumFacing side, PipeSideMode mode) {

        switch(side) {
            case UP:
                up = mode;
                break;
            case DOWN:
                down = mode;
                break;
            case NORTH:
                north = mode;
                break;
            case SOUTH:
                south = mode;
                break;
            case EAST:
                east = mode;
                break;
            case WEST:
                west = mode;
                break;
        }

        markDirty();

        if (world != null) {
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }

    private PipeSideMode readMode(NBTTagCompound compound, String key) {

        if (!compound.hasKey(key)) {
            return PipeSideMode.DISCONNECTED;
        }

        String value = compound.getString(key);

        for (PipeSideMode mode : PipeSideMode.values()) {
            if (mode.getName().equals(value)) {
                return mode;
            }
        }

        return PipeSideMode.DISCONNECTED;
    }

    // ----- \\
    // ITEMS \\
    // ----- \\
    private ItemStack carriedItem = ItemStack.EMPTY;

    public ItemStack getCarriedItem() {
        return carriedItem.copy();
    }

    public boolean hasCarriedItem() {
        return !carriedItem.isEmpty();
    }

    public void setCarriedItem(ItemStack stack) {
        carriedItem = stack.isEmpty() ? ItemStack.EMPTY : stack.copy();
        markDirty();
    }
}
