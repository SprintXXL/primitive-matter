package com.SprintXXL.primitivematter.content.devices.transport.pipe.network.pathfinding;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.PipeNetwork;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.discovery.PipeEndpoint;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

import static com.SprintXXL.primitivematter.PrimitiveMatter.printInfo;

public class PipePathFinder {

    @Nullable
    public static PipePathResult findClosestPath(World world, PipeNetwork network, PipeEndpoint extractPoint) {

        printInfo("Finding path from: " + extractPoint.getInventoryPos());

        Queue<BlockPos> toVisit = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();
        Map<BlockPos, BlockPos> cameFrom = new HashMap<>();
        Set<BlockPos> members = network.getMembers();

        toVisit.add(extractPoint.getPipePos());

        while (!toVisit.isEmpty()) {

            BlockPos currentPos = toVisit.remove();

            if (!visited.add(currentPos)) {
                continue;
            }

            for (PipeEndpoint endpoint : network.getInsertPoints()) {

                if (endpoint.getInventoryPos().equals(extractPoint.getInventoryPos())) {
                    continue;
                }

                if (endpoint.getPipePos().equals(currentPos)) {

                    List<BlockPos> path = reconstructPath(cameFrom, extractPoint.getPipePos(), currentPos);

                    printInfo("Reached insert point: " + endpoint.getInventoryPos());
                    printInfo("Path length: " + path.size());

                    return new PipePathResult(path, endpoint);
                }
            }

            for (EnumFacing side : EnumFacing.values()) {

                BlockPos neighborPos = currentPos.offset(side);

                if (!members.contains(neighborPos)) {
                    continue;
                }

                if (visited.contains(neighborPos)) {
                    continue;
                }

                if (cameFrom.containsKey(neighborPos)) {
                    continue;
                }

                cameFrom.put(neighborPos, currentPos);
                toVisit.add(neighborPos);
            }
        }

        printInfo("No valid path found.");

        return null;
    }

    private static List<BlockPos> reconstructPath(Map<BlockPos, BlockPos> cameFrom, BlockPos startPos, BlockPos destinationPos) {

        List<BlockPos> path = new ArrayList<>();

        BlockPos currentPos = destinationPos;
        path.add(currentPos);

        while (!currentPos.equals(startPos)) {

            currentPos = cameFrom.get(currentPos);

            if (currentPos == null) {
                return Collections.emptyList();
            }

            path.add(currentPos);
        }

        Collections.reverse(path);

        return path;
    }
}
