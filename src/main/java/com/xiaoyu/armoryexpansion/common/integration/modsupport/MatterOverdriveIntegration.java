package com.xiaoyu.armoryexpansion.common.integration.modsupport;

import com.xiaoyu.armoryexpansion.Reference;

import com.xiaoyu.armoryexpansion.common.integration.aelib.integration.JsonIntegration;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(
        modid = MatterOverdriveIntegration.MODID,
        name = MatterOverdriveIntegration.NAME,
        version = Reference.VERSION,
        dependencies = MatterOverdriveIntegration.DEPENDENCIES
)

@Mod.EventBusSubscriber
public class MatterOverdriveIntegration extends JsonIntegration {
    static final String MODID = Reference.MOD_ID + "-matteroverdrive";
    static final String NAME = Reference.MOD_NAME + " - MatterOverdrive";
    static final String DEPENDENCIES = "required-after:" + Reference.MOD_ID + "; " + "after:matteroverdrive; ";

    public MatterOverdriveIntegration() {
        super("matteroverdrive", Reference.MOD_ID, "matteroverdrive");
        MinecraftForge.EVENT_BUS.register(this);
    }

//    public MatterOverdriveIntegration() {
//        super("matteroverdrive");
//    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modId = "matteroverdrive";
        super.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<? super Block> event){
        super.registerBlocks(event);
    }
}
