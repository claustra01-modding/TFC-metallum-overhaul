package net.claustra01.tfcm;

import net.claustra01.tfcm.worldgen.TfcmWorldgen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(TfcmMod.MOD_ID)
public final class TfcmMod {
    public static final String MOD_ID = "tfcm";
    public static final String TFC_MORE_ITEMS_MOD_ID = "tfc_items";
    public static final String TFC_ORE_WASHING_MOD_ID = "tfcorewashing";
    public static final String FIRMALIFE_MOD_ID = "firmalife";
    public static final String TFC_METAL_TOOLS_MOD_ID = "tfc_metal_tools";
    public static final String TFC_HOT_OR_NOT_MOD_ID = "tfchotornot";

    public TfcmMod(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, TfcmConfig.COMMON_SPEC);
        TfcmWorldgen.bootstrap();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TfcmClientEvents.register(modEventBus);
        }
        TfcmWorldgen.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        TfcmFluids.FLUID_TYPES.register(modEventBus);
        TfcmFluids.FLUIDS.register(modEventBus);
        TfcmBlocks.BLOCKS.register(modEventBus);
        TfcmArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        TfcmCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        TfcmItems.ITEMS.register(modEventBus);
    }
}
