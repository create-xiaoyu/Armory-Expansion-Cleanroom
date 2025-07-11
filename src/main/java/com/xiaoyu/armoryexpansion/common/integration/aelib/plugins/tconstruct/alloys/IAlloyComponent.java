package com.xiaoyu.armoryexpansion.common.integration.aelib.plugins.tconstruct.alloys;

import net.minecraft.nbt.NBTTagCompound;

public interface IAlloyComponent {
    String getFluid();

    int getAmount();

    NBTTagCompound getFluidTag();
}