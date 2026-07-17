package net.claustra01.tfcm.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.dries007.tfc.world.Codecs;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record TfcmGeodeConfig(
    BlockState outer,
    BlockState middle,
    SimpleWeightedRandomList<BlockState> inner,
    SimpleWeightedRandomList<BlockState> filling,
    SimpleWeightedRandomList<BlockState> innerPlacements
) implements FeatureConfiguration {
    public static final Codec<TfcmGeodeConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codecs.BLOCK_STATE.fieldOf("outer").forGetter(TfcmGeodeConfig::outer),
        Codecs.BLOCK_STATE.fieldOf("middle").forGetter(TfcmGeodeConfig::middle),
        SimpleWeightedRandomList.wrappedCodec(Codecs.BLOCK_STATE).fieldOf("inner").forGetter(TfcmGeodeConfig::inner),
        SimpleWeightedRandomList.wrappedCodec(Codecs.BLOCK_STATE).fieldOf("filling").forGetter(TfcmGeodeConfig::filling),
        SimpleWeightedRandomList.wrappedCodec(Codecs.BLOCK_STATE).fieldOf("inner_placements").forGetter(TfcmGeodeConfig::innerPlacements)
    ).apply(instance, TfcmGeodeConfig::new));
}
