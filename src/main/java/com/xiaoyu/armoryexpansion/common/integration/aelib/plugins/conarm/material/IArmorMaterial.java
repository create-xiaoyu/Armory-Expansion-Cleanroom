package com.xiaoyu.armoryexpansion.common.integration.aelib.plugins.conarm.material;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;

public interface IArmorMaterial {
    CoreMaterialStats getCoreMaterialStats();

    PlatesMaterialStats getPlatesMaterialStats();

    TrimMaterialStats getTrimMaterialStats();

    boolean isArmorMaterial();
}
