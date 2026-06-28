package com.SprintXXL.primitivematter;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.SprintXXL.primitivematter.Reference.*;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class PrimitiveMatter {

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }
}
