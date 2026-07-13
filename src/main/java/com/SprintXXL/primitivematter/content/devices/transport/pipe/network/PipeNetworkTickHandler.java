package com.SprintXXL.primitivematter.content.devices.transport.pipe.network;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.persistence.PipeNetworkSavedData;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber
public final class PipeNetworkTickHandler {

    private PipeNetworkTickHandler() {}

    private static final Map<World, PipeNetworkManager> MANAGERS = new WeakHashMap<>();

    public static PipeNetworkManager getManager(World world) {
        return MANAGERS.computeIfAbsent(world, ignored -> {

            PipeNetworkManager manager = new PipeNetworkManager();

            PipeNetworkSavedData.get(world).restore(manager);

            return manager;
        });
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (event.world.isRemote) {
            return;
        }

        getManager(event.world).tick(event.world);
    }
}
