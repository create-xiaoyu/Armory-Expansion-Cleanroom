package com.xiaoyu.armoryexpansion.common.integration.aelib.integration;

import com.xiaoyu.armoryexpansion.ArmoryExpansion;

public class IndependentJsonIntegration extends JsonIntegration {
    protected IndependentJsonIntegration(String modId, String root, String json) {
        super(modId, root, json);
    }

    @Override
    public boolean isLoadable() {
        return ArmoryExpansion.isIntegrationEnabled(this.modId);
    }
}