package com.SprintXXL.primitivematter.events;

import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.PipeNetworkManager;
import com.SprintXXL.primitivematter.content.devices.transport.pipe.network.persistence.PipeNetworkSavedData;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.SprintXXL.primitivematter.content.devices.transport.pipe.network.PipeNetworkTickHandler.getManager;

@Mod.EventBusSubscriber
public class WorldEvents {

    @SubscribeEvent
    public static void onWorldSave(WorldEvent.Save event) {

        World world = event.getWorld();

        if (world.isRemote) {
            return;
        }

        PipeNetworkManager manager = getManager(world);

        PipeNetworkSavedData.get(world).save(manager);
    }
}
