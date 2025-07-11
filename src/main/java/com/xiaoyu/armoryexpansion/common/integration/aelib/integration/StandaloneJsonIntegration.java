package com.xiaoyu.armoryexpansion.common.integration.aelib.integration;

import com.xiaoyu.armoryexpansion.common.util.ConfigFileSuffixEnum;

import java.io.File;

public class StandaloneJsonIntegration extends IndependentJsonIntegration {
    protected StandaloneJsonIntegration(String modId, String root, String json) {
        super(modId, root, json);
    }

    @Override
    protected String getFilePath(File dir, String fileName, ConfigFileSuffixEnum suffix) {
        return dir.getPath()+ "/" + this.root + "/" + fileName + suffix.getSuffix() + ".json";
    }
}