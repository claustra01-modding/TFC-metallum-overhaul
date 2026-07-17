package net.claustra01.tfcm.block;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public final class TfcmBuddingQuartzBlock extends BuddingAmethystBlock {
    private static final Direction[] DIRECTIONS = Direction.values();

    private final Supplier<? extends Block> smallBud;
    private final Supplier<? extends Block> mediumBud;
    private final Supplier<? extends Block> largeBud;
    private final Supplier<? extends Block> cluster;

    public TfcmBuddingQuartzBlock(Properties properties, Supplier<? extends Block> smallBud,
                                  Supplier<? extends Block> mediumBud, Supplier<? extends Block> largeBud,
                                  Supplier<? extends Block> cluster) {
        super(properties);
        this.smallBud = smallBud;
        this.mediumBud = mediumBud;
        this.largeBud = largeBud;
        this.cluster = cluster;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(15) != 0) {
            return;
        }

        final Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        final BlockPos targetPos = pos.relative(direction);
        final BlockState targetState = level.getBlockState(targetPos);
        final Block next;
        if (canClusterGrowAtState(targetState)) {
            next = smallBud.get();
        } else if (targetState.is(smallBud.get()) && targetState.getValue(AmethystClusterBlock.FACING) == direction) {
            next = mediumBud.get();
        } else if (targetState.is(mediumBud.get()) && targetState.getValue(AmethystClusterBlock.FACING) == direction) {
            next = largeBud.get();
        } else if (targetState.is(largeBud.get()) && targetState.getValue(AmethystClusterBlock.FACING) == direction) {
            next = cluster.get();
        } else {
            return;
        }

        final BlockState nextState = next.defaultBlockState()
            .setValue(AmethystClusterBlock.FACING, direction)
            .setValue(AmethystClusterBlock.WATERLOGGED, targetState.getFluidState().getType() == Fluids.WATER);
        level.setBlockAndUpdate(targetPos, nextState);
    }

    public static boolean canClusterGrowAtState(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }
}
