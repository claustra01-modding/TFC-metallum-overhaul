package net.claustra01.tfcmu2;

import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryMetal;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public enum Tfcmu2Metal implements RegistryMetal {
    COMPRESSED_IRON("compressed_iron", Rarity.UNCOMMON, 0x6F6C6B),
    PLATINUM("platinum", Rarity.RARE, 0x627C8B),
    NAQUADAH("naquadah", Rarity.RARE, 0x4D5742),
    IRIDIUM("iridium", Rarity.UNCOMMON, 0xADC4CE),
    OSMIUM("osmium", Rarity.UNCOMMON, 0xA5B4CA),
    OSMIRIDIUM("osmiridium", Rarity.UNCOMMON, 0x718383),
    MYTHRIL("mythril", Rarity.COMMON, 0x5383A2),
    ADAMANT("adamant", Rarity.RARE, 0x5B83A9),
    BIOSTEEL("biosteel", Rarity.UNCOMMON, 0x6A856A),
    DURATIUM("duratium", Rarity.RARE, 0x463A57),
    ENERGITE("energite", Rarity.EPIC, 0xBB57E3),
    REFINED_GLOWSTONE("refined_glowstone", Rarity.UNCOMMON, 0xE2B446),
    REFINED_OBSIDIAN("refined_obsidian", Rarity.RARE, 0x473752),
    ANTIMONY("antimony", Rarity.COMMON, 0xB4AFBD),
    TITANIUM("titanium", Rarity.UNCOMMON, 0x898B93),
    TUNGSTEN("tungsten", Rarity.EPIC, 0x585F6B),
    SOLDER("solder", Rarity.UNCOMMON, 0x888888),
    TUNGSTEN_STEEL("tungsten_steel", Rarity.EPIC, 0x2F353E),
    NETHERITE("netherite", Rarity.EPIC, 0x3B3230);

    private final String serializedName;
    private final Rarity rarity;
    private final int color;

    Tfcmu2Metal(String serializedName, Rarity rarity, int color) {
        this.serializedName = serializedName;
        this.rarity = rarity;
        this.color = color;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }

    @Override
    public Tier toolTier() {
        throw unsupported("toolTier");
    }

    @Override
    public ArmorMaterial armorTier() {
        throw unsupported("armorTier");
    }

    @Override
    public Metal.Tier metalTier() {
        throw unsupported("metalTier");
    }

    @Override
    public Supplier<Block> getFullBlock() {
        return () -> Tfcmu2Blocks.METAL_BLOCKS.get(this).get();
    }

    @Override
    public MapColor mapColor() {
        return MapColor.METAL;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    public Rarity rarity() {
        return rarity;
    }

    public int color() {
        return color;
    }

    private UnsupportedOperationException unsupported(String method) {
        return new UnsupportedOperationException(method + " is not used by the implemented metal subset: " + serializedName);
    }
}
