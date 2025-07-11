package com.xiaoyu.armoryexpansion;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.TConstruct;
import c4.conarm.ConstructsArmory;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class ArmoryExpansion {

    public static final String DEPENDENCIES =
            "required-after:" + TConstruct.modID + "; " + "required-after:" + ConstructsArmory.MODID + "; ";

    private static Configuration config;

    public static Configuration getConfig() {
        return config;
    }

    public static void setConfig(Configuration config) {
        ArmoryExpansion.config = config;
    }

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        config = new Configuration(event.getSuggestedConfigurationFile());
    }

    public static boolean isIntegrationEnabled(String modid) {
        return config
                .get("integrations", modid, true, "Whether integration with " + modid + " should be enabled")
                .getBoolean();
    }

    public static int getBoundedInputStreamMaxSize() {
        return config.get("web server", "input stream max size", 131072,
                "The maximum size of the data received from the Web Server").getInt();
    }

}
