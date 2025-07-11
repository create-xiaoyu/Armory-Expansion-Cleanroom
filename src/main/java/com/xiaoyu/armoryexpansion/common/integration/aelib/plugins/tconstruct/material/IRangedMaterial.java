package com.xiaoyu.armoryexpansion.common.integration.aelib.plugins.tconstruct.material;

import slimeknights.tconstruct.library.materials.*;

public interface IRangedMaterial {
    BowMaterialStats getBowMaterialStats();

    BowStringMaterialStats getBowStringMaterialStats();

    ArrowShaftMaterialStats getArrowShaftMaterialStats();

    FletchingMaterialStats getFletchingMaterialStats();

    ProjectileMaterialStats getProjectileMaterialStats();

    boolean isRangedMaterial();
}