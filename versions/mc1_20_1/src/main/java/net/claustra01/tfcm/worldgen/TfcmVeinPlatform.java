package net.claustra01.tfcm.worldgen;

import java.util.Map;
import java.util.Optional;

import net.dries007.tfc.util.collections.IWeighted;
import net.dries007.tfc.world.feature.vein.Indicator;
import net.dries007.tfc.world.feature.vein.VeinConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

final class TfcmVeinPlatform {
    private TfcmVeinPlatform() {
    }

    static VeinConfig createVeinConfig(
        Map<Block, IWeighted<BlockState>> states,
        Optional<Indicator> indicator,
        int rarity,
        float density,
        int minY,
        int maxY,
        boolean project,
        boolean projectOffset,
        long seed,
        boolean nearLava
    ) {
        return new VeinConfig(states, indicator, rarity, density, minY, maxY, project, projectOffset, seed, Optional.empty(), nearLava);
    }
}
