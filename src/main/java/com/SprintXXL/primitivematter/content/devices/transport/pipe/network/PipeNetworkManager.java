package com.SprintXXL.primitivematter.content.devices.transport.pipe.network;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.base.BlockPipe;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery.PipeNetworkDiscoveryResult;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

import static com.SprintXXL.primitivematter.PrimitiveMatter.printInfo;
import static com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery.PipeNetworkDiscovery.discoverNetwork;

public class PipeNetworkManager {

    private final List<PipeNetwork> networks = new ArrayList<>();

    public List<PipeNetwork> getNetworks() {
        return Collections.unmodifiableList(networks);
    }

    public boolean addNetwork(PipeNetwork network) {

        boolean added = networks.add(network);

        if (added) {
            printInfo(
                    "Created network: members=" + network.getMemberCount()
                            + ", extractPoints=" + network.getExtractPointCount()
                            + ", insertPoints=" + network.getInsertPointCount()
                            + ", totalNetworks=" + networks.size()
            );
        }

        return added;
    }

    public boolean removeNetwork(PipeNetwork network) {

        boolean removed = networks.remove(network);

        if (removed) {
            printInfo("Removed network. Total networks=" + networks.size());
        }

        return removed;
    }

    public void tick(World world) {

        for (PipeNetwork network : networks) {
            network.tick(world);
        }
    }

    @Nullable
    public PipeNetwork findNetworkContaining(BlockPos pos) {

        for (PipeNetwork network : networks) {

            if (network.getMembers().contains(pos)) {
                return network;
            }
        }

        return null;
    }

    public void rebuildNetwork(World world, BlockPos changedPos) {

        printInfo("=== Rebuilding network ===");
        printInfo("Changed pipe: " + changedPos);

        Block block = world.getBlockState(changedPos).getBlock();

        if (!(block instanceof BlockPipe)) {
            printInfo("Mode: Pipe removed");
            rebuildAfterRemoval(world, changedPos);
            return;
        }

        printInfo("Mode: Existing pipe");
        rebuildFromPipe(world, changedPos);
    }

    private void rebuildAfterRemoval(World world, BlockPos removedPos) {

        printInfo("Searching neighboring pipes...");

        PipeNetwork oldNetwork = findNetworkContaining(removedPos);

        if (oldNetwork == null) {
            return;
        }

        removeNetwork(oldNetwork);

        Set<BlockPos> rebuiltMembers = new HashSet<>();

        for (EnumFacing side : EnumFacing.values()) {

            BlockPos neighborPos = removedPos.offset(side);

            printInfo("Checking neighbor: " + neighborPos);

            Block neighborBlock = world.getBlockState(neighborPos).getBlock();

            if (!(neighborBlock instanceof BlockPipe)) {
                printInfo("Not a pipe.");
                continue;
            }

            if (rebuiltMembers.contains(neighborPos)) {
                printInfo("Already rebuilt this network.");
                continue;
            }

            printInfo("Rediscovering from: " + neighborPos);

            PipeNetworkDiscoveryResult result = discoverNetwork(world, neighborPos);

            printInfo(
                    "Found network: members="
                            + result.getMembers().size()
                            + ", extract="
                            + result.getExtractPoints().size()
                            + ", insert="
                            + result.getInsertPoints().size()
            );

            rebuiltMembers.addAll(result.getMembers());

            PipeNetwork rebuiltNetwork = new PipeNetwork(
                    result.getMembers(),
                    result.getExtractPoints(),
                    result.getInsertPoints()
            );

            addNetwork(rebuiltNetwork);
        }
    }

    private void rebuildFromPipe(World world, BlockPos startPos) {

        printInfo("Rediscovering network from: " + startPos);

        PipeNetworkDiscoveryResult result = discoverNetwork(world, startPos);

        printInfo(
                "Discovery complete: members="
                        + result.getMembers().size()
                        + ", extract="
                        + result.getExtractPoints().size()
                        + ", insert="
                        + result.getInsertPoints().size()
        );

        int before = networks.size();

        networks.removeIf(network -> network.getMembers().stream().anyMatch(result.getMembers()::contains));

        printInfo("Replacing overlapping networks...");
        printInfo("Removed " + (before - networks.size()) + " overlapping network(s)");

        PipeNetwork newNetwork = new PipeNetwork(
                result.getMembers(),
                result.getExtractPoints(),
                result.getInsertPoints()
        );

        addNetwork(newNetwork);
    }
}
