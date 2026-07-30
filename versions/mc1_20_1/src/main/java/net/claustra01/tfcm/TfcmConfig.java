package net.claustra01.tfcm;

import java.util.List;

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
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> disabledMetals;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> disabledOres;
        public final ForgeConfigSpec.BooleanValue enableCustomVeinGeneration;

        private Common(ForgeConfigSpec.Builder builder) {
            builder.push("content");
            disabledMetals = builder
                .comment("Metal names whose registries should not be created. Changes require a full game restart.")
                .defineListAllowEmpty("disabledMetals", List.of(), value -> value instanceof String);
            disabledOres = builder
                .comment("Ore names whose registries should not be created. Changes require a full game restart.")
                .defineListAllowEmpty("disabledOres", List.of(), value -> value instanceof String);
            builder.pop();

            builder.push("worldgen");
            enableCustomVeinGeneration = builder
                .comment(
                    "If true, TFCM will replace TFC ore veins and TFCM's bundled geodes with worldgen loaded from",
                    "config/tfcm/{overworld,nether,end}.yaml. Changes require a full game restart."
                )
                .define("enableCustomVeinGeneration", false);
            builder.pop();
        }
    }

    private TfcmConfig() {
    }
}
