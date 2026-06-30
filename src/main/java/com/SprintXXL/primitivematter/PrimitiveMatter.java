package com.SprintXXL.primitivematter;

import com.SprintXXL.primitivematter.content.ForgeRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.SprintXXL.primitivematter.Reference.*;
import static com.SprintXXL.primitivematter.library.registry.MatterRegistry.initAllMatter;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class PrimitiveMatter {

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        initAllMatter();

        MinecraftForge.EVENT_BUS.register(new ForgeRegistry());
    }
}
