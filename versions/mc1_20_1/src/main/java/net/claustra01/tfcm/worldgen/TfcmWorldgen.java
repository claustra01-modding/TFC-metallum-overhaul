package net.claustra01.tfcm.worldgen;

import com.mojang.serialization.Codec;

import net.claustra01.tfcm.TfcmMod;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.atomic.AtomicBoolean;

public final class TfcmWorldgen {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, TfcmMod.MOD_ID);

    public static final RegistryObject<Codec<TfcmOreVeinBiomeModifier>> ORE_VEINS =
        BIOME_MODIFIER_SERIALIZERS.register("ore_veins", () -> Codec.unit(TfcmOreVeinBiomeModifier.INSTANCE));

    private static final AtomicBoolean BOOTSTRAPPED = new AtomicBoolean(false);

    public static void bootstrap() {
        if (!BOOTSTRAPPED.compareAndSet(false, true)) {
            return;
        }
        TfcmCustomVeins.bootstrap();
    }

    private TfcmWorldgen() {
    }
}
