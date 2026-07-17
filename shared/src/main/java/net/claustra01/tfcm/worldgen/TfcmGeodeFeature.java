package net.claustra01.tfcm.worldgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import net.claustra01.tfcm.block.TfcmBuddingQuartzBlock;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.FluidState;

/** Adapted from TFC IE Crossover's MIT-licensed quartz geode feature. */
public final class TfcmGeodeFeature extends Feature<TfcmGeodeConfig> {
    public TfcmGeodeFeature(Codec<TfcmGeodeConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TfcmGeodeConfig> context) {
        final WorldGenLevel level = context.level();
        final BlockPos origin = context.origin();
        final RandomSource random = context.random();
        final TfcmGeodeConfig config = context.config();
        final WorldgenRandom worldgenRandom = new WorldgenRandom(new XoroshiroRandomSource(level.getSeed()));
        final NormalNoise noiseGenerator = NormalNoise.create(worldgenRandom, -4, 1.0D);
        final boolean cracked = random.nextFloat() < 0.95F;
        final List<Pair<BlockPos, Integer>> distributionPoints = new LinkedList<>();
        final List<BlockPos> crackBlocks = new LinkedList<>();

        final UniformInt outerWall = UniformInt.of(4, 6);
        final UniformInt pointOffset = UniformInt.of(1, 2);
        final int distributionPointCount = UniformInt.of(3, 4).sample(random);
        final double relativeDistributionPoint = (double) distributionPointCount / outerWall.getMaxValue();

        final double fillLimit = 1.0D / Math.sqrt(1.7D);
        final double innerLimit = 1.0D / Math.sqrt(2.2D + relativeDistributionPoint);
        final double middleLimit = 1.0D / Math.sqrt(3.2D + relativeDistributionPoint);
        final double outerLimit = 1.0D / Math.sqrt(4.2D + relativeDistributionPoint);
        final double crackLimit = 1.0D / Math.sqrt(2D + random.nextDouble() / 2D
            + (distributionPointCount > 3 ? relativeDistributionPoint : 0D));

        int invalidPoints = 0;
        for (int index = 0; index < distributionPointCount; index++) {
            final BlockPos point = origin.offset(outerWall.sample(random), outerWall.sample(random), outerWall.sample(random));
            final BlockState found = level.getBlockState(point);
            if (found.isAir() || Helpers.isBlock(found, BlockTags.GEODE_INVALID_BLOCKS)) {
                invalidPoints++;
                if (invalidPoints > 1) {
                    return false;
                }
            }
            distributionPoints.add(Pair.of(point, pointOffset.sample(random)));
        }

        if (cracked) {
            addCrackPoints(origin, random, distributionPointCount * 2 + 1, crackBlocks);
        }

        final List<BlockPos> placementCandidates = new ArrayList<>();
        final Predicate<BlockState> replaceable = isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-16, -16, -16), origin.offset(16, 16, 16))) {
            final double noise = noiseGenerator.getValue(pos.getX(), pos.getY(), pos.getZ()) * 0.05D;
            double pointWeight = 0D;
            double crackWeight = 0D;

            for (Pair<BlockPos, Integer> point : distributionPoints) {
                pointWeight += Mth.invSqrt(pos.distSqr(point.getFirst()) + point.getSecond()) + noise;
            }
            for (BlockPos crackPos : crackBlocks) {
                crackWeight += Mth.invSqrt(pos.distSqr(crackPos) + 2D) + noise;
            }

            if (pointWeight < outerLimit) {
                continue;
            }
            if (cracked && crackWeight >= crackLimit && pointWeight < fillLimit) {
                safeSetBlock(level, pos, Blocks.AIR.defaultBlockState(), replaceable);
                scheduleAdjacentFluidTicks(level, pos);
            } else if (pointWeight >= fillLimit) {
                safeSetBlock(level, pos, config.filling().getRandomValue(random).orElse(Blocks.AIR.defaultBlockState()), replaceable);
            } else if (pointWeight >= innerLimit) {
                safeSetBlock(level, pos, config.inner().getRandomValue(random).orElseThrow(), replaceable);
                if (random.nextFloat() < 0.083D && random.nextFloat() < 0.35D) {
                    placementCandidates.add(pos.immutable());
                }
            } else if (pointWeight >= middleLimit) {
                safeSetBlock(level, pos, config.middle(), replaceable);
            } else {
                safeSetBlock(level, pos, config.outer(), replaceable);
            }
        }

        placeClusters(level, random, config, replaceable, placementCandidates);
        return true;
    }

    private static void addCrackPoints(BlockPos origin, RandomSource random, int distance, List<BlockPos> crackBlocks) {
        final int x = random.nextBoolean() ? distance : 0;
        final int z = random.nextBoolean() ? distance : 0;
        crackBlocks.add(origin.offset(x, 7, z));
        crackBlocks.add(origin.offset(x, 5, z));
        crackBlocks.add(origin.offset(x, 1, z));
    }

    private static void scheduleAdjacentFluidTicks(WorldGenLevel level, BlockPos pos) {
        for (Direction direction : Helpers.DIRECTIONS) {
            final BlockPos adjacent = pos.relative(direction);
            final FluidState fluid = level.getFluidState(adjacent);
            if (!fluid.isEmpty()) {
                level.scheduleTick(adjacent, fluid.getType(), 0);
            }
        }
    }

    private void placeClusters(WorldGenLevel level, RandomSource random, TfcmGeodeConfig config,
                               Predicate<BlockState> replaceable, List<BlockPos> candidates) {
        for (BlockPos sourcePos : candidates) {
            BlockState placement = config.innerPlacements().getRandomValue(random).orElseThrow();
            for (Direction direction : Helpers.DIRECTIONS) {
                if (placement.hasProperty(BlockStateProperties.FACING)) {
                    placement = placement.setValue(BlockStateProperties.FACING, direction);
                }

                final BlockPos targetPos = sourcePos.relative(direction);
                final BlockState targetState = level.getBlockState(targetPos);
                if (placement.hasProperty(BlockStateProperties.WATERLOGGED)) {
                    placement = placement.setValue(BlockStateProperties.WATERLOGGED, targetState.getFluidState().isSource());
                }
                if (TfcmBuddingQuartzBlock.canClusterGrowAtState(targetState)) {
                    safeSetBlock(level, targetPos, placement, replaceable);
                    break;
                }
            }
        }
    }
}
