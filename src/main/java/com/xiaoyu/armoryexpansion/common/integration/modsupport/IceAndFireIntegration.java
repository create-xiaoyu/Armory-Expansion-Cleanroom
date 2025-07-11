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

import com.github.alexthe666.iceandfire.IceAndFire;

@Mod(
        modid = IceAndFireIntegration.MODID,
        name = IceAndFireIntegration.NAME,
        version = Reference.VERSION,
        dependencies = IceAndFireIntegration.DEPENDENCIES
)

@Mod.EventBusSubscriber
public class IceAndFireIntegration extends JsonIntegration {
    static final String MODID = Reference.MOD_ID + "-" + IceAndFire.MODID;
    static final String NAME = Reference.MOD_NAME + " - " + IceAndFire.NAME;
    static final String DEPENDENCIES =
            "required-after:" + Reference.MOD_ID + "; " +
                    "after:" + IceAndFire.MODID + "; ";

    public IceAndFireIntegration() {
        super(IceAndFire.MODID, Reference.MOD_ID, IceAndFire.MODID);
        MinecraftForge.EVENT_BUS.register(this);
    }

//    public IceAndFireIntegration() {
//        super(IceAndFire.MODID);
//    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modId = IceAndFire.MODID;
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
