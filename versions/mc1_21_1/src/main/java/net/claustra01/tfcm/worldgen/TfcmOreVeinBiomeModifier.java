package net.claustra01.tfcm.worldgen;

import java.util.List;

import net.claustra01.tfcm.TfcmConfig;
import net.claustra01.tfcm.TfcmPlatform;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public final class TfcmOreVeinBiomeModifier implements BiomeModifier {
    static final TfcmOreVeinBiomeModifier INSTANCE = new TfcmOreVeinBiomeModifier();

    private static final TagKey<Biome> IS_NETHER = TagKey.create(
        Registries.BIOME, TfcmPlatform.id("minecraft", "is_nether")
    );
    private static final TagKey<Biome> IS_END = TagKey.create(
        Registries.BIOME, TfcmPlatform.id("minecraft", "is_end")
    );

    private TfcmOreVeinBiomeModifier() {
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.AFTER_EVERYTHING) {
            return;
        }

        final var server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }

        final Registry<PlacedFeature> placedFeatures = server.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        final List<Holder<PlacedFeature>> ores = builder.getGenerationSettings()
            .getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
        final List<Holder<PlacedFeature>> extra = builder.getGenerationSettings()
            .getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION);

        TfcmOreVeinModifierCore.modify(
            biome.is(IS_NETHER), biome.is(IS_END), ores, extra, placedFeatures,
            TfcmConfig.COMMON.enableCustomVeinGeneration.get()
        );
    }

    @Override
    public com.mojang.serialization.MapCodec<? extends BiomeModifier> codec() {
        return TfcmWorldgen.ORE_VEINS.get();
    }
}
