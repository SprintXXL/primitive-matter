package com.SprintXXL.primitivematter.content.devices.transport.pipe.debug;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.PipeNetwork;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.PipeNetworkManager;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery.PipeNetworkDiscoveryResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery.PipeNetworkDiscovery.discoverNetwork;

public class DebugPipeNetwork {

    public static void testPathFinding(World world, BlockPos startPipePos, PipeNetworkManager manager) {

        PipeNetworkDiscoveryResult discovery = discoverNetwork(world, startPipePos);

        PipeNetwork network = new PipeNetwork(
                discovery.getMembers(),
                discovery.getExtractPoints(),
                discovery.getInsertPoints()
        );

        manager.addNetwork(network);
    }
}
