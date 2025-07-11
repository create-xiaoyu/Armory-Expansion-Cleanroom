package com.xiaoyu.armoryexpansion.common.integration.modsupport;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import com.xiaoyu.armoryexpansion.ArmoryExpansion;
import com.xiaoyu.armoryexpansion.Reference;
import c4.conarm.ConstructsArmory;

import com.xiaoyu.armoryexpansion.client.MaterialRenderType;
import com.xiaoyu.armoryexpansion.common.integration.aelib.integration.JsonIntegration;
import com.xiaoyu.armoryexpansion.common.integration.aelib.plugins.conarm.material.ArmorMaterial;
import com.xiaoyu.armoryexpansion.common.integration.aelib.plugins.conarm.material.IArmorMaterial;
import com.xiaoyu.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.tools.TinkerMaterials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

@Mod(
        modid = ConArmIntegration.MODID,
        name = ConArmIntegration.NAME,
        version = Reference.VERSION,
        dependencies = ConArmIntegration.DEPENDENCIES
)

@Mod.EventBusSubscriber
public class ConArmIntegration extends JsonIntegration {
    static final String MODID = Reference.MOD_ID + "-" + ConstructsArmory.MODID;
    static final String NAME = Reference.MOD_NAME + " - " + ConstructsArmory.MODNAME;
    static final String DEPENDENCIES = "required-after:" + Reference.MOD_ID + "; " + "after:*";

    private static final float STAT_MULTIPLIER = 1.25f;
    private static final int DURABILITY_MIN = 1;
    private static final int DURABILITY_MAX = 120;
    private static final int DEFENSE_MIN = 0;
    private static final int DEFENSE_MAX = 50;
    private static final int TOUGHNESS_MIN = DEFENSE_MIN / 10;
    private static final int TOUGHNESS_MAX = DEFENSE_MAX / 10;

    private final Collection<ItemArmor.ArmorMaterial> jsonMaterials = new LinkedList<>();

    public ConArmIntegration() {
        super(ConstructsArmory.MODID, Reference.MOD_ID, ConstructsArmory.MODID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modId = ConstructsArmory.MODID;
        this.logger = event.getModLog();
        this.configDir = event.getModConfigurationDirectory().getPath();
        if (ArmoryExpansion.isIntegrationEnabled(this.modId)){
            this.loadMaterialsFromOtherIntegrations(event);
            this.loadIntegrationData(this.configDir);
            this.integrationConfigHelper.syncConfig(this.materials);
            this.saveIntegrationData(this.configDir);
            this.registerMaterialStats();
        }
        ArmoryExpansion.getConfig().save();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void registerItems(RegistryEvent<Item> event){
        if(ArmoryExpansion.isIntegrationEnabled(this.modId)){
            this.loadIntegrationData(this.configDir);
            this.integrationConfigHelper.syncConfig(this.materials);
            this.saveIntegrationData(this.configDir);
            this.registerMaterialStats();
        }
        ArmoryExpansion.getConfig().save();
    }

    private void loadMaterialsFromOtherIntegrations(FMLPreInitializationEvent event){
        this.loadJsonMaterialsFromOtherIntegrations(event);
    }

    private void loadJsonMaterialsFromOtherIntegrations(FMLPreInitializationEvent event){
        File jsonDir = new File(event.getModConfigurationDirectory().getPath() + "/" + Reference.MOD_ID + "/");
        //noinspection ResultOfMethodCallIgnored
        jsonDir.mkdirs();
        for (File json : Objects.requireNonNull(
                jsonDir.listFiles((dir, name) -> name.contains("-materials.json") && !name.contains(ConstructsArmory.MODID)))){
            this.loadMaterialsFromOtherIntegration(json);
        }
    }

    private void loadMaterialsFromOtherIntegration(File file){
        Gson gson = new GsonBuilder().setPrettyPrinting().setStrictness(Strictness.LENIENT).create();
        try {
            Collections.addAll(this.jsonMaterials, gson.fromJson(new FileReader(file), ItemArmor.ArmorMaterial[].class));
        } catch (FileNotFoundException e) {
            this.logger.error("", e);
        }
    }

    private void addMaterial(IBasicMaterial material){
        if(this.isMaterialEnabled(material.getIdentifier())){
            this.materials.putIfAbsent(material.getIdentifier(), material);
        }
    }

    @Override
    protected void loadMaterialsFromSource() {
        TinkerRegistry.getAllMaterials().stream().filter(this::isConversionAvailable)
                .map(this::newTiCMaterial).filter(material -> !this.jsonMaterials.contains(material))
                .forEach(material -> this.addMaterial((IBasicMaterial) material));
    }

    private boolean isConversionAvailable(Material material){
        boolean core = !material.hasStats(ArmorMaterialType.CORE) && material.hasStats(MaterialTypes.HEAD);
        boolean plates = !material.hasStats(ArmorMaterialType.PLATES) && material.hasStats(MaterialTypes.HANDLE);
        boolean trim = !material.hasStats(ArmorMaterialType.TRIM) && material.hasStats(MaterialTypes.EXTRA);
        return core || plates || trim;
    }

    private IArmorMaterial newTiCMaterial(Material material){
        return this.newTiCMaterial(material, TinkerMaterials.iron);
    }

    private IArmorMaterial newTiCMaterial(Material material, Material baseMaterial){
        ArmorMaterial armorMaterial = new ArmorMaterial(material.identifier, material.materialTextColor, MaterialRenderType.METAL,
                this.getCoreMaterialStats(material, baseMaterial),
                this.getPlatesMaterialStats(material, baseMaterial),
                this.getTrimMaterialStats(material, baseMaterial)
        );
        armorMaterial.setCastable(material.isCastable());
        armorMaterial.setCraftable(material.isCraftable());

        return armorMaterial;
    }

    private CoreMaterialStats getCoreMaterialStats(Material material, Material baseMaterial){
        return new CoreMaterialStats(
                this.calculateDurability(material, baseMaterial),
                this.calculateDefense(material, baseMaterial)
        );
    }

    private PlatesMaterialStats getPlatesMaterialStats(Material material, Material baseMaterial){
        return new PlatesMaterialStats(
                this.calculateModifier(material, baseMaterial),
                this.calculateExtraDurability(material, baseMaterial),
                this.calculateToughness(material, baseMaterial)
        );
    }

    private TrimMaterialStats getTrimMaterialStats(Material material, Material baseMaterial){
        return new TrimMaterialStats(
                this.calculateExtraDurability(material, baseMaterial)
        );
    }

    private static int clampInt(float value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return (int) value;
    }

    private static float clampFloat(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    private int calculateDurability(Material material, Material baseMaterial){
        HeadMaterialStats materialHead = material.getStats(MaterialTypes.HEAD);
        return null != materialHead
                ? clampInt(((CoreMaterialStats) baseMaterial.getStats(ArmorMaterialType.CORE)).durability * materialHead.durability
                / ((HeadMaterialStats) baseMaterial.getStats(MaterialTypes.HEAD)).durability / STAT_MULTIPLIER, DURABILITY_MIN, DURABILITY_MAX)
                : 0;
    }

    private float calculateDefense(Material material, Material baseMaterial){
        HeadMaterialStats materialHead = material.getStats(MaterialTypes.HEAD);
        return null != materialHead
                ? clampFloat(1.5f * ((CoreMaterialStats) baseMaterial.getStats(ArmorMaterialType.CORE)).defense * materialHead.attack
                / ((HeadMaterialStats) baseMaterial.getStats(MaterialTypes.HEAD)).attack  / STAT_MULTIPLIER, DEFENSE_MIN, DEFENSE_MAX)
                : 0;
    }

    private float calculateModifier(Material material, Material baseMaterial){
        HandleMaterialStats materialHandle = material.getStats(MaterialTypes.HANDLE);
        return null != materialHandle
                ? clampFloat(1.5f * ((CoreMaterialStats) baseMaterial.getStats(ArmorMaterialType.CORE)).defense * materialHandle.modifier
                / ((HandleMaterialStats) baseMaterial.getStats(MaterialTypes.HANDLE)).modifier  / STAT_MULTIPLIER, DEFENSE_MIN, DEFENSE_MAX)
                : 0;
    }

    private float calculateToughness(Material material, Material baseMaterial){
        HandleMaterialStats materialHandle = material.getStats(MaterialTypes.HANDLE);
        return null != materialHandle
                ? clampFloat(3 * ((PlatesMaterialStats) baseMaterial.getStats(ArmorMaterialType.PLATES)).toughness * materialHandle.durability
                / ((HandleMaterialStats) baseMaterial.getStats(MaterialTypes.HANDLE)).durability / STAT_MULTIPLIER, TOUGHNESS_MIN, TOUGHNESS_MAX)
                : 0;
    }

    private float calculateExtraDurability(Material material, Material baseMaterial){
        ExtraMaterialStats materialExtra = material.getStats(MaterialTypes.EXTRA);
        return null != materialExtra
                ? 2 * ((TrimMaterialStats) baseMaterial.getStats(ArmorMaterialType.TRIM)).extraDurability * materialExtra.extraDurability
                / ((ExtraMaterialStats) baseMaterial.getStats(MaterialTypes.EXTRA)).extraDurability / STAT_MULTIPLIER
                : 0;
    }
}