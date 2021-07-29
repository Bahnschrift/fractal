package com.bahnschrift.fractal;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("fractal")
public class Main {
    public static final Logger LOGGER = LogManager.getLogger();
    public Main() {
        MinecraftForge.EVENT_BUS.register(RegisterCommandEvent.class);
    }
}
