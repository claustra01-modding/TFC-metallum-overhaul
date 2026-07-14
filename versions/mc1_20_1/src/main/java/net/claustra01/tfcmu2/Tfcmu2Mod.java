package net.claustra01.tfcmu2;

import net.claustra01.tfcmu2.worldgen.Tfcmu2Worldgen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Tfcmu2Mod.MOD_ID)
public final class Tfcmu2Mod {
    public static final String MOD_ID = "tfcm";
    public static final String TFC_MORE_ITEMS_MOD_ID = "tfc_items";
    public static final String TFC_ORE_WASHING_MOD_ID = "tfcorewashing";
    public static final String FIRMALIFE_MOD_ID = "firmalife";
    public static final String TFC_METAL_TOOLS_MOD_ID = "tfc_metal_tools";

    public Tfcmu2Mod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Tfcmu2Config.COMMON_SPEC);
        Tfcmu2Worldgen.bootstrap();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            Tfcmu2ClientEvents.register(modEventBus);
        }
        Tfcmu2Worldgen.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        Tfcmu2Fluids.FLUID_BLOCKS.register(modEventBus);
        Tfcmu2Fluids.BUCKET_ITEMS.register(modEventBus);
        Tfcmu2Fluids.FLUID_TYPES.register(modEventBus);
        Tfcmu2Fluids.FLUIDS.register(modEventBus);
        Tfcmu2Blocks.BLOCKS.register(modEventBus);
        Tfcmu2CreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        Tfcmu2Items.ITEMS.register(modEventBus);
    }
}
