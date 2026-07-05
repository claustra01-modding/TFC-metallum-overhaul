package net.claustra01.tfcmu2;

import net.claustra01.tfcmu2.worldgen.Tfcmu2Worldgen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Tfcmu2Mod.MOD_ID)
public final class Tfcmu2Mod {
    public static final String MOD_ID = "tfcmu2";
    public static final String TFC_MORE_ITEMS_MOD_ID = "tfc_items";
    public static final String TFC_ORE_WASHING_MOD_ID = "tfcorewashing";
    public static final String FIRMALIFE_MOD_ID = "firmalife";
    public static final String TFC_IE_ADDON_MOD_ID = "tfc_ie_addon";

    public Tfcmu2Mod(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Tfcmu2Config.COMMON_SPEC);
        Tfcmu2Worldgen.bootstrap();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            Tfcmu2ClientEvents.register(modEventBus);
        }
        Tfcmu2Worldgen.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
        Tfcmu2Fluids.FLUID_TYPES.register(modEventBus);
        Tfcmu2Fluids.FLUIDS.register(modEventBus);
        Tfcmu2Blocks.BLOCKS.register(modEventBus);
        Tfcmu2CreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        Tfcmu2Items.ITEMS.register(modEventBus);
    }
}
