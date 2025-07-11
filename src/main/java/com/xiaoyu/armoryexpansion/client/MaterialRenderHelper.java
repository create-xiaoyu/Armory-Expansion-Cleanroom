package com.xiaoyu.armoryexpansion.client;

import com.xiaoyu.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.Material;

@SideOnly(Side.CLIENT)
public enum MaterialRenderHelper {

    ;

    public static void setMaterialRenderInfo(Material material, IBasicMaterial ticMaterial) {
        MaterialRenderInfo materialRenderInfo;
        switch (ticMaterial.getType()) {
            case METAL:
                materialRenderInfo = new MaterialRenderInfo.Metal(ticMaterial.getColor());
                break;
            case METAL_TEXTURED:
                materialRenderInfo = new MaterialRenderInfo.MetalTextured(ticMaterial.getTexture(), ticMaterial.getColor(), 0.4f, 0.4f, 0.1f);
                break;
            default:
                materialRenderInfo = new MaterialRenderInfo.Default(ticMaterial.getColor());
                break;
        }
        material.setRenderInfo(materialRenderInfo);
    }
}
