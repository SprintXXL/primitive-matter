package com.SprintXXL.primitivematter;

import com.SprintXXL.primitivematter.content.ForgeRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.SprintXXL.primitivematter.Reference.*;
import static com.SprintXXL.primitivematter.content.ContentRegistry.createContent;
import static com.SprintXXL.primitivematter.content.ContentRegistry.createCustomFluidBlocks;
import static com.SprintXXL.primitivematter.content.ForgeRegistry.registerCustomFluids;
import static com.SprintXXL.primitivematter.library.devices.registry.DeviceRegistry.initDeviceRegistry;
import static com.SprintXXL.primitivematter.library.substances.registry.SubstanceRegistry.initSubstanceRegistry;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class PrimitiveMatter {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        initSubstanceRegistry();
        initDeviceRegistry();

        createContent();
        registerCustomFluids();
        createCustomFluidBlocks();
        MinecraftForge.EVENT_BUS.register(new ForgeRegistry());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static void printInfo(String text) {
        LOGGER.info(text);
    }

    public static void printWarn(String text) {
        LOGGER.warn(text);
    }
}
