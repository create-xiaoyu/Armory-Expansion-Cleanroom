package com.xiaoyu.armoryexpansion.common.integration.aelib.plugins.tconstruct.alloys;

public interface IAlloy {
    void registerTiCAlloy();

    AlloyComponent getOutput();

    AlloyComponent[] getInputs();

    String getName();
}