package net.claustra01.tfcm;

import net.minecraftforge.common.ForgeConfigSpec;

public final class TfcmConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

    public static final class Common {
        public final ForgeConfigSpec.BooleanValue enableCustomVeinGeneration;

        private Common(ForgeConfigSpec.Builder builder) {
            builder.push("worldgen");
            enableCustomVeinGeneration = builder
                .comment(
                    "If true, TFCM will replace TFC's '#tfc:in_biome/veins' placed features with veins loaded from",
                    "config/tfcm/{overworld,nether,end}.yaml. Changes require a full game restart."
                )
                .define("enableCustomVeinGeneration", false);
            builder.pop();
        }
    }

    private TfcmConfig() {
    }
}
