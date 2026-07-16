package com.SprintXXL.primitivematter.content.devices.transport.pipe.network;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.base.BlockPipe;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery.PipeEndpoint;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.pathfinding.PipePathResult;
import com.SprintXXL.primitivematter.library.devices.category.transport.pipe.PipeDevice;
import com.SprintXXL.primitivematter.library.devices.category.transport.pipe.data.ItemPipeData;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

import static com.SprintXXL.primitivematter.PrimitiveMatter.printInfo;
import static com.SprintXXL.primitivematter.content.devices.transport.pipe.network.pathfinding.PipePathFinder.findClosestPath;
import static net.minecraftforge.items.ItemHandlerHelper.insertItem;

public class PipeNetwork {

    private final Set<BlockPos> members;
    private final List<PipeEndpoint> extractPoints;
    private final List<PipeEndpoint> insertPoints;
    private final List<NetworkStack> stacksInTransit = new ArrayList<>();
    private final Map<PipeEndpoint, Integer> extractProgress = new HashMap<>();

    public PipeNetwork(
            Set<BlockPos> members,
            List<PipeEndpoint> extractPoints,
            List<PipeEndpoint> insertPoints
    ) {
        this.members = new HashSet<>(members);
        this.extractPoints = new ArrayList<>(extractPoints);
        this.insertPoints = new ArrayList<>(insertPoints);

        for (PipeEndpoint extractPoint : this.extractPoints) {
            extractProgress.put(extractPoint, 0);
        }
    }

    public PipeNetwork(
            Set<BlockPos> members,
            List<PipeEndpoint> extractPoints,
            List<PipeEndpoint> insertPoints,
            List<NetworkStack> stacksInTransit,
            Map<PipeEndpoint, Integer> extractProgress
    ) {
        this.members = new HashSet<>(members);
        this.extractPoints = new ArrayList<>(extractPoints);
        this.insertPoints = new ArrayList<>(insertPoints);
        this.stacksInTransit.addAll(stacksInTransit);
        this.extractProgress.putAll(extractProgress);
    }

    // NETWORK MEMBERS \\
    public Set<BlockPos> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public int getMemberCount() {
        return members.size();
    }

    public boolean addMember(BlockPos pos) {
        return members.add(pos);
    }

    public boolean removeMember(BlockPos pos) {
        return members.remove(pos);
    }

    public boolean containsMember(BlockPos pos) {
        return members.contains(pos);
    }

    // NETWORK EXTRACT POINTS \\
    public List<PipeEndpoint> getExtractPoints() {
        return Collections.unmodifiableList(extractPoints);
    }

    public int getExtractPointCount() {
        return extractPoints.size();
    }

    public boolean addExtractPoint(PipeEndpoint endpoint) {
        return extractPoints.add(endpoint);
    }

    public boolean removeExtractPoint(PipeEndpoint endpoint) {
        return extractPoints.remove(endpoint);
    }

    public boolean containsExtractPoint(PipeEndpoint endpoint) {
        return extractPoints.contains(endpoint);
    }

    // NETWORK INSERT POINTS \\
    public List<PipeEndpoint> getInsertPoints() {
        return Collections.unmodifiableList(insertPoints);
    }

    public int getInsertPointCount() {
        return insertPoints.size();
    }

    public boolean addInsertPoint(PipeEndpoint endpoint) {
        return insertPoints.add(endpoint);
    }

    public boolean removeInsertPoint(PipeEndpoint endpoint) {
        return insertPoints.remove(endpoint);
    }

    public boolean containsInsertPoint(PipeEndpoint endpoint) {
        return insertPoints.contains(endpoint);
    }

    // NETWORK STACKS IN TRANSIT \\
    public List<NetworkStack> getStacksInTransit() {
        return Collections.unmodifiableList(stacksInTransit);
    }

    public int getStackCount() {
        return stacksInTransit.size();
    }

    public boolean addStack(NetworkStack stack) {
        return stacksInTransit.add(stack);
    }

    public boolean removeStack(NetworkStack stack) {
        return stacksInTransit.remove(stack);
    }

    // NETWORK EXTRACT PROGRESS \\
    public Map<PipeEndpoint, Integer> getExtractProgress() {
        return Collections.unmodifiableMap(extractProgress);
    }

    // TICKING \\
    public void tick(World world) {

        tickStacksInTransit(world);
        tickExtraction(world);
    }

    private void tickStacksInTransit(World world) {

        Iterator<NetworkStack> iterator = stacksInTransit.iterator();

        while (iterator.hasNext()) {

            NetworkStack stack = iterator.next();

            stack.tick();

            BlockPos currentPos = stack.getCurrentPos();

            Block block = world.getBlockState(currentPos).getBlock();

            if (!(block instanceof BlockPipe)) {
                continue;
            }

            BlockPipe pipeBlock = (BlockPipe) block;

            PipeDevice definition = pipeBlock.getDefinition();

            if (!(definition.getData() instanceof ItemPipeData)) {
                continue;
            }

            ItemPipeData data = (ItemPipeData) definition.getData();

            int progress = stack.getProgressTicks();
            int interval = data.getTransferInterval();

            if (progress >= interval) {

                if (!stack.advance()) {

                    if (tryOutputStack(world, stack)) {
                        iterator.remove();
                    } else {
                        stack.resetProgress();
                    }
                }
            }
        }
    }

    public boolean tryOutputStack(World world, NetworkStack stack) {

        BlockPos destinationPos = stack.getDestinationPos();

        TileEntity destinationTile = world.getTileEntity(destinationPos);

        if (destinationTile == null) {
            return false;
        }

        IItemHandler handler = destinationTile.getCapability(
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                stack.getDestinationSide()
        );

        if (handler == null) {
            handler = destinationTile.getCapability(
                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    null
            );
        }

        if (handler == null) {
            return false;
        }

        ItemStack remainder = insertItem(handler, stack.getStack(), false);

        stack.setStack(remainder);

        if (remainder.isEmpty()) {
            printInfo("Delivered stack!");
            return true;
        }

        printInfo(
                "Destination blocked: "
                        + destinationPos
                        + ", remaining=" + remainder.getCount()
        );

        return false;
    }

    private void tickExtraction(World world) {

        for (PipeEndpoint extractPoint : extractPoints) {

            int progress = extractProgress.getOrDefault(extractPoint, 0) + 1;
            int interval = getTransferInterval(world, extractPoint.getPipePos());

            if (progress < interval) {
                extractProgress.put(extractPoint, progress);
                continue;
            }

            printInfo("Extraction timer reached: " + extractPoint.getInventoryPos());
            tryCreateNetworkStack(world, extractPoint);
            extractProgress.put(extractPoint, 0);
        }
    }

    private boolean tryCreateNetworkStack(World world, PipeEndpoint extractPoint) {

        TileEntity sourceTile = world.getTileEntity(extractPoint.getInventoryPos());

        if (sourceTile == null) {
            return false;
        }

        IItemHandler handler = sourceTile.getCapability(
                CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                extractPoint.getInventorySide()
        );

        if (handler == null) {
            handler = sourceTile.getCapability(
                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    null
            );
        }

        if (handler == null) {
            return false;
        }

        int transferAmount = getTransferAmount(world, extractPoint.getPipePos());

        for (int slot = 0; slot < handler.getSlots(); slot++) {

            ItemStack simulated = handler.extractItem(
                    slot,
                    transferAmount,
                    true
            );

            if (simulated.isEmpty()) {
                continue;
            }

            PipePathResult pathResult = findClosestPath(world, this, extractPoint);

            if (pathResult == null || pathResult.getPath().isEmpty()) {
                return false;
            }

            ItemStack extracted = handler.extractItem(
                    slot,
                    simulated.getCount(),
                    false
            );

            if (extracted.isEmpty()) {
                continue;
            }

            PipeEndpoint destination = pathResult.getDestination();

            NetworkStack networkStack = new NetworkStack(
                    extracted,
                    pathResult.getPath(),
                    destination.getInventoryPos(),
                    destination.getInventorySide(),
                    0,
                    0
            );

            printInfo(
                    "Created NetworkStack: "
                            + extracted.getCount()
                            + "x " + extracted.getDisplayName()
                            + ", pathLength=" + pathResult.getPath().size()
                            + ", destination=" + destination.getInventoryPos()
            );

            addStack(networkStack);
            return true;
        }

        printInfo("Nothing to extract.");

        return false;
    }

    private int getTransferInterval(World world, BlockPos pipePos) {

        Block block = world.getBlockState(pipePos).getBlock();

        if (!(block instanceof BlockPipe)) {
            return Integer.MAX_VALUE;
        }

        PipeDevice definition = ((BlockPipe) block).getDefinition();

        if (!(definition.getData() instanceof ItemPipeData)) {
            return Integer.MAX_VALUE;
        }

        return ((ItemPipeData) definition.getData()).getTransferInterval();
    }

    private int getTransferAmount(World world, BlockPos pipePos) {

        Block block = world.getBlockState(pipePos).getBlock();

        if (!(block instanceof BlockPipe)) {
            return 0;
        }

        PipeDevice definition = ((BlockPipe) block).getDefinition();

        if (!(definition.getData() instanceof ItemPipeData)) {
            return 0;
        }

        return ((ItemPipeData) definition.getData()).getTransferAmount();
    }
}
