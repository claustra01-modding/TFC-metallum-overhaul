package net.claustra01.tfcmu2.worldgen;

import com.mojang.serialization.Codec;

import net.claustra01.tfcmu2.Tfcmu2Mod;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.atomic.AtomicBoolean;

public final class Tfcmu2Worldgen {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Tfcmu2Mod.MOD_ID);

    public static final RegistryObject<Codec<Tfcmu2OreVeinBiomeModifier>> ORE_VEINS =
        BIOME_MODIFIER_SERIALIZERS.register("ore_veins", () -> Codec.unit(Tfcmu2OreVeinBiomeModifier.INSTANCE));

    private static final AtomicBoolean BOOTSTRAPPED = new AtomicBoolean(false);

    public static void bootstrap() {
        if (!BOOTSTRAPPED.compareAndSet(false, true)) {
            return;
        }
        Tfcmu2CustomVeins.bootstrap();
    }

    private Tfcmu2Worldgen() {
    }
}
