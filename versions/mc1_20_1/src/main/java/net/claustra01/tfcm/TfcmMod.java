package net.claustra01.tfcm;

import net.claustra01.tfcm.worldgen.TfcmWorldgen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(TfcmMod.MOD_ID)
public final class TfcmMod {
    public static final String MOD_ID = "tfcm";
    public static final String TFC_MORE_ITEMS_MOD_ID = "tfc_items";
    public static final String TFC_ORE_WASHING_MOD_ID = "tfcorewashing";
    public static final String FIRMALIFE_MOD_ID = "firmalife";
    public static final String TFC_METAL_TOOLS_MOD_ID = "tfc_metal_tools";

    public TfcmMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TfcmConfig.COMMON_SPEC);
        TfcmWorldgen.bootstrap();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            TfcmClientEvents.register(modEventBus);
        }
        TfcmWorldgen.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        TfcmFluids.FLUID_BLOCKS.register(modEventBus);
        TfcmFluids.BUCKET_ITEMS.register(modEventBus);
        TfcmFluids.FLUID_TYPES.register(modEventBus);
        TfcmFluids.FLUIDS.register(modEventBus);
        TfcmBlocks.BLOCKS.register(modEventBus);
        TfcmCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        TfcmItems.ITEMS.register(modEventBus);
    }
}
