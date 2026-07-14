package net.claustra01.tfcm;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum TfcmVanillaStone {
    NETHERRACK("netherrack", Blocks.NETHERRACK),
    ENDSTONE("endstone", Blocks.END_STONE);

    private final String serializedName;
    private final Block baseBlock;

    TfcmVanillaStone(String serializedName, Block baseBlock) {
        this.serializedName = serializedName;
        this.baseBlock = baseBlock;
    }

    public String getSerializedName() {
        return serializedName;
    }

    public Block baseBlock() {
        return baseBlock;
    }
}
