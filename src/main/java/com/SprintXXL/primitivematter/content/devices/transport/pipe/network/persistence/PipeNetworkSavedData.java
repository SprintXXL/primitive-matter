package com.SprintXXL.primitivematter.content.devices.transport.pipe.network.persistence;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.NetworkStack;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.PipeNetwork;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.PipeNetworkManager;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery.PipeEndpoint;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;
import java.util.*;

public class PipeNetworkSavedData extends WorldSavedData {

    public static final String DATA_NAME = "primitive_matter_pipe_networks";

    private final List<SavedPipeNetwork> savedNetworks = new ArrayList<>();

    public PipeNetworkSavedData() {
        super(DATA_NAME);
    }

    public PipeNetworkSavedData(String name) {
        super(name);
    }

    public static PipeNetworkSavedData get(World world) {

        PipeNetworkSavedData data = (PipeNetworkSavedData) world
                .getPerWorldStorage()
                .getOrLoadData(PipeNetworkSavedData.class, DATA_NAME);

        if (data == null) {
            data = new PipeNetworkSavedData();

            world.getPerWorldStorage().setData(DATA_NAME, data);
        }

        return data;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {

        tag.setTag("pipe_networks", writeNetworkList(savedNetworks));

        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {

        savedNetworks.clear();
        savedNetworks.addAll(readNetworkList(tag.getTagList("pipe_networks", 10)));
    }

    public void save(PipeNetworkManager manager) {

        savedNetworks.clear();
        savedNetworks.addAll(saveNetworkList(manager.getNetworks()));

        markDirty();
    }

    public void restore(PipeNetworkManager manager) {

        for (SavedPipeNetwork saved : savedNetworks) {
            manager.addNetwork(restoreNetwork(saved));
        }
    }

    // ---------------- \\
    // RUNTIME -> SAVED \\
    // ---------------- \\
    private List<SavedPipeNetwork> saveNetworkList(List<PipeNetwork> networks) {

        List<SavedPipeNetwork> savedNetworks = new ArrayList<>();

        for (PipeNetwork network : networks) {
            savedNetworks.add(saveNetwork(network));
        }

        return savedNetworks;
    }

    private SavedPipeNetwork saveNetwork(PipeNetwork network) {

        List<SavedPipeEndpoint> savedExtractPoints = saveEndpointList(network.getExtractPoints());
        List<SavedPipeEndpoint> savedInsertPoints = saveEndpointList(network.getInsertPoints());
        List<SavedNetworkStack> savedStacks = saveNetworkStackList(network.getStacksInTransit());

        Map<SavedPipeEndpoint, Integer> savedProgress = new HashMap<>();

        for (Map.Entry<PipeEndpoint, Integer> entry : network.getExtractProgress().entrySet()) {

            savedProgress.put(saveEndpoint(entry.getKey()), entry.getValue());
        }

        return new SavedPipeNetwork(
                network.getMembers(),
                savedExtractPoints,
                savedInsertPoints,
                savedStacks,
                savedProgress
        );
    }

    private PipeNetwork restoreNetwork(SavedPipeNetwork saved) {

        List<PipeEndpoint> extractPoints = restoreEndpointList(saved.getExtractPoints());
        List<PipeEndpoint> insertPoints = restoreEndpointList(saved.getInsertPoints());
        List<NetworkStack> stacks = restoreNetworkStackList(saved.getStacksInTransit());

        Map<PipeEndpoint, Integer> progress = new HashMap<>();

        for (Map.Entry<SavedPipeEndpoint, Integer> entry : saved.getExtractProgress().entrySet()) {

            progress.put(restoreEndpoint(entry.getKey()), entry.getValue());
        }

        return new PipeNetwork(
                saved.getMembers(),
                extractPoints,
                insertPoints,
                stacks,
                progress
        );
    }

    private List<SavedNetworkStack> saveNetworkStackList(List<NetworkStack> stacks) {

        List<SavedNetworkStack> savedStacks = new ArrayList<>();

        for (NetworkStack stack : stacks) {
            savedStacks.add(saveNetworkStack(stack));
        }

        return savedStacks;
    }

    private SavedNetworkStack saveNetworkStack(NetworkStack stack) {
        return new SavedNetworkStack(
                stack.getStack(),
                stack.getPath(),
                stack.getDestinationPos(),
                stack.getDestinationSide(),
                stack.getPathIndex(),
                stack.getProgressTicks()
        );
    }

    private List<NetworkStack> restoreNetworkStackList(List<SavedNetworkStack> savedStacks) {

        List<NetworkStack> stacks = new ArrayList<>();

        for (SavedNetworkStack saved : savedStacks) {
            stacks.add(restoreNetworkStack(saved));
        }

        return stacks;
    }

    private NetworkStack restoreNetworkStack(SavedNetworkStack saved) {
        return new NetworkStack(
                saved.getStack(),
                saved.getPath(),
                saved.getDestinationPos(),
                saved.getDestinationSide(),
                saved.getPathIndex(),
                saved.getProgressTicks()
        );
    }

    private List<SavedPipeEndpoint> saveEndpointList(List<PipeEndpoint> endpoints) {

        List<SavedPipeEndpoint> savedEndpoints = new ArrayList<>();

        for (PipeEndpoint endpoint : endpoints) {
            savedEndpoints.add(saveEndpoint(endpoint));
        }

        return savedEndpoints;
    }

    private SavedPipeEndpoint saveEndpoint(PipeEndpoint endpoint) {
        return new SavedPipeEndpoint(
                endpoint.getPipePos(),
                endpoint.getInventoryPos(),
                endpoint.getInventorySide()
        );
    }

    private List<PipeEndpoint> restoreEndpointList(List<SavedPipeEndpoint> savedEndpoints) {

        List<PipeEndpoint> endpoints = new ArrayList<>();

        for (SavedPipeEndpoint saved : savedEndpoints) {
            endpoints.add(restoreEndpoint(saved));
        }

        return endpoints;
    }

    private PipeEndpoint restoreEndpoint(SavedPipeEndpoint saved) {
        return new PipeEndpoint(
                saved.getPipePos(),
                saved.getInventoryPos(),
                saved.getInventorySide()
        );
    }

    // ------------------ \\
    // SAVED PIPE NETWORK \\
    // ------------------ \\
    private NBTTagList writeNetworkList(List<SavedPipeNetwork> networks) {

        NBTTagList list = new NBTTagList();

        for (SavedPipeNetwork network : networks) {
            list.appendTag(writeNetwork(network));
        }

        return list;
    }

    private List<SavedPipeNetwork> readNetworkList(NBTTagList list) {

        List<SavedPipeNetwork> networks = new ArrayList<>();

        for (int i = 0; i < list.tagCount(); i++) {

            NBTTagCompound tag = list.getCompoundTagAt(i);

            SavedPipeNetwork network = readNetwork(tag);

            networks.add(network);
        }

        return networks;
    }

    private NBTTagCompound writeNetwork(SavedPipeNetwork network) {

        NBTTagCompound tag = new NBTTagCompound();

        tag.setTag("members", writeBlockPosList(network.getMembers()));
        tag.setTag("extract_points", writeEndpointList(network.getExtractPoints()));
        tag.setTag("insert_points", writeEndpointList(network.getInsertPoints()));
        tag.setTag("stacks_in_transit", writeNetworkStackList(network.getStacksInTransit()));
        tag.setTag("extract_progress", writeExtractProgress(network.getExtractProgress()));

        return tag;
    }

    private SavedPipeNetwork readNetwork(NBTTagCompound tag) {

        Set<BlockPos> members = new HashSet<>(readBlockPosList(tag.getTagList("members", 10)));
        List<SavedPipeEndpoint> extractPoints = readEndpointList(tag.getTagList("extract_points", 10));
        List<SavedPipeEndpoint> insertPoints = readEndpointList(tag.getTagList("insert_points", 10));
        List<SavedNetworkStack> stacksInTransit = readNetworkStackList(tag.getTagList("stacks_in_transit", 10));
        Map<SavedPipeEndpoint, Integer> extractProgress = readExtractProgress(tag.getTagList("extract_progress", 10));

        return new SavedPipeNetwork(members, extractPoints, insertPoints, stacksInTransit, extractProgress);
    }

    // ------------------- \\
    // SAVED NETWORK STACK \\
    // ------------------- \\
    private NBTTagList writeNetworkStackList(List<SavedNetworkStack> stacks) {

        NBTTagList list = new NBTTagList();

        for (SavedNetworkStack stack : stacks) {
            list.appendTag(writeNetworkStack(stack));
        }

        return list;
    }

    private List<SavedNetworkStack> readNetworkStackList(NBTTagList list) {

        List<SavedNetworkStack> stacks = new ArrayList<>();

        for (int i = 0; i < list.tagCount(); i++) {

            NBTTagCompound tag = list.getCompoundTagAt(i);

            SavedNetworkStack stack = readNetworkStack(tag);

            if (stack != null) {
                stacks.add(stack);
            }
        }

        return stacks;
    }

    private NBTTagCompound writeNetworkStack(SavedNetworkStack networkStack) {

        NBTTagCompound tag = new NBTTagCompound();

        NBTTagCompound stackTag = new NBTTagCompound();
        networkStack.getStack().writeToNBT(stackTag);
        tag.setTag("stack", stackTag);

        tag.setTag("path", writeBlockPosList(networkStack.getPath()));

        BlockPos destination = networkStack.getDestinationPos();
        tag.setInteger("destination_x", destination.getX());
        tag.setInteger("destination_y", destination.getY());
        tag.setInteger("destination_z", destination.getZ());

        tag.setString("destination_side", networkStack.getDestinationSide().getName());

        tag.setInteger("path_index", networkStack.getPathIndex());

        tag.setInteger("progress_ticks", networkStack.getProgressTicks());

        return tag;
    }

    @Nullable
    private SavedNetworkStack readNetworkStack(NBTTagCompound tag) {

        ItemStack stack = new ItemStack(tag.getCompoundTag("stack"));
        List<BlockPos> path = readBlockPosList(tag.getTagList("path", 10));
        BlockPos destinationPos = new BlockPos(
                tag.getInteger("destination_x"),
                tag.getInteger("destination_y"),
                tag.getInteger("destination_z")
        );
        EnumFacing destinationSide = EnumFacing.byName(tag.getString("destination_side"));

        if (destinationSide == null) {
            return null;
        }

        int pathIndex = tag.getInteger("path_index");
        int progressTicks = tag.getInteger("progress_ticks");

        if (path.isEmpty()) {
            return null;
        }

        if (pathIndex < 0 || pathIndex >= path.size()) {
            return null;
        }

        return new SavedNetworkStack(stack, path, destinationPos, destinationSide, pathIndex, progressTicks);
    }

    // ------------------- \\
    // SAVED PIPE ENDPOINT \\
    // ------------------- \\
    private NBTTagList writeEndpointList(List<SavedPipeEndpoint> endpoints) {

        NBTTagList list = new NBTTagList();

        for (SavedPipeEndpoint endpoint : endpoints) {
            list.appendTag(writeEndpoint(endpoint));
        }

        return list;
    }

    private List<SavedPipeEndpoint> readEndpointList(NBTTagList list) {

        List<SavedPipeEndpoint> endpoints = new ArrayList<>();

        for (int i = 0; i < list.tagCount(); i++) {

            NBTTagCompound tag = list.getCompoundTagAt(i);

            SavedPipeEndpoint endpoint = readEndpoint(tag);

            if (endpoint != null) {
                endpoints.add(endpoint);
            }
        }

        return endpoints;
    }

    private NBTTagCompound writeEndpoint(SavedPipeEndpoint endpoint) {

        NBTTagCompound tag = new NBTTagCompound();

        BlockPos pipePos = endpoint.getPipePos();
        tag.setInteger("pipe_x", pipePos.getX());
        tag.setInteger("pipe_y", pipePos.getY());
        tag.setInteger("pipe_z", pipePos.getZ());

        BlockPos inventoryPos = endpoint.getInventoryPos();
        tag.setInteger("inventory_x", inventoryPos.getX());
        tag.setInteger("inventory_y", inventoryPos.getY());
        tag.setInteger("inventory_z", inventoryPos.getZ());

        String inventorySide = endpoint.getInventorySide().getName();
        tag.setString("inventory_side", inventorySide);

        return tag;
    }

    @Nullable
    private SavedPipeEndpoint readEndpoint(NBTTagCompound tag) {

        BlockPos pipePos = new BlockPos(
                tag.getInteger("pipe_x"),
                tag.getInteger("pipe_y"),
                tag.getInteger("pipe_z")
        );

        BlockPos inventoryPos = new BlockPos(
                tag.getInteger("inventory_x"),
                tag.getInteger("inventory_y"),
                tag.getInteger("inventory_z")
                );

        EnumFacing inventorySide = EnumFacing.byName(
                tag.getString("inventory_side"));

        if (inventorySide == null) {
            return null;
        }

        return new SavedPipeEndpoint(pipePos, inventoryPos, inventorySide);
    }

    // ------- \\
    // HELPERS \\
    // ------- \\
    private NBTTagList writeExtractProgress(Map<SavedPipeEndpoint, Integer> extractProgress) {

        NBTTagList list = new NBTTagList();

        for (Map.Entry<SavedPipeEndpoint, Integer> entry : extractProgress.entrySet()) {

            NBTTagCompound entryTag = new NBTTagCompound();

            entryTag.setTag("endpoint", writeEndpoint(entry.getKey()));

            entryTag.setInteger("progress", entry.getValue());

            list.appendTag(entryTag);
        }

        return list;
    }

    private Map<SavedPipeEndpoint, Integer> readExtractProgress(NBTTagList list) {

        Map<SavedPipeEndpoint, Integer> extractProgress = new HashMap<>();

        for (int i = 0; i < list.tagCount(); i++) {

            NBTTagCompound entryTag = list.getCompoundTagAt(i);

            SavedPipeEndpoint endpoint = readEndpoint(entryTag.getCompoundTag("endpoint"));

            if (endpoint == null) {
                continue;
            }

            int progress = entryTag.getInteger("progress");

            extractProgress.put(endpoint, progress);
        }

        return extractProgress;
    }

    private NBTTagList writeBlockPosList(Collection<BlockPos> positions) {

        NBTTagList list = new NBTTagList();

        for (BlockPos pos : positions) {

            NBTTagCompound posTag = new NBTTagCompound();

            posTag.setInteger("x", pos.getX());
            posTag.setInteger("y", pos.getY());
            posTag.setInteger("z", pos.getZ());

            list.appendTag(posTag);
        }

        return list;
    }

    private List<BlockPos> readBlockPosList(NBTTagList list) {

        List<BlockPos> positions = new ArrayList<>();

        for (int i = 0; i < list.tagCount(); i++) {

            NBTTagCompound posTag = list.getCompoundTagAt(i);

            positions.add(new BlockPos(
                    posTag.getInteger("x"),
                    posTag.getInteger("y"),
                    posTag.getInteger("z")
            ));
        }

        return positions;
    }
}
