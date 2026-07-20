package net.claustra01.tfcm;

import net.minecraft.world.item.Rarity;

public enum TfcmFluidSpec {
    MOLTEN_ANDESITE("andesite", Rarity.COMMON, 0x74736C);

    private final String serializedName;
    private final Rarity rarity;
    private final int color;

    TfcmFluidSpec(String serializedName, Rarity rarity, int color) {
        this.serializedName = serializedName;
        this.rarity = rarity;
        this.color = color;
    }

    public String serializedName() {
        return serializedName;
    }

    public Rarity rarity() {
        return rarity;
    }

    public int color() {
        return color;
    }
}
