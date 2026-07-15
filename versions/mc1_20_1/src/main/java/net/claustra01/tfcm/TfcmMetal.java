package net.claustra01.tfcm;

import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryMetal;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public enum TfcmMetal implements RegistryMetal {
    COMPRESSED_IRON(TfcmMetalSpec.COMPRESSED_IRON),
    PLATINUM(TfcmMetalSpec.PLATINUM),
    NAQUADAH(TfcmMetalSpec.NAQUADAH),
    IRIDIUM(TfcmMetalSpec.IRIDIUM),
    OSMIUM(TfcmMetalSpec.OSMIUM),
    OSMIRIDIUM(TfcmMetalSpec.OSMIRIDIUM),
    MITHRIL(TfcmMetalSpec.MITHRIL),
    ARCANE(TfcmMetalSpec.ARCANE),
    REFINED_GLOWSTONE(TfcmMetalSpec.REFINED_GLOWSTONE),
    REFINED_OBSIDIAN(TfcmMetalSpec.REFINED_OBSIDIAN),
    ANTIMONY(TfcmMetalSpec.ANTIMONY),
    TITANIUM(TfcmMetalSpec.TITANIUM),
    COBALT(TfcmMetalSpec.COBALT),
    LITHIUM(TfcmMetalSpec.LITHIUM),
    ALUMINUM(TfcmMetalSpec.ALUMINUM),
    CONSTANTAN(TfcmMetalSpec.CONSTANTAN),
    INVAR(TfcmMetalSpec.INVAR),
    ELECTRUM(TfcmMetalSpec.ELECTRUM),
    LEAD(TfcmMetalSpec.LEAD),
    URANIUM(TfcmMetalSpec.URANIUM),
    TUNGSTEN(TfcmMetalSpec.TUNGSTEN),
    SOLDER(TfcmMetalSpec.SOLDER),
    TUNGSTEN_STEEL(TfcmMetalSpec.TUNGSTEN_STEEL),
    NETHERITE(TfcmMetalSpec.NETHERITE),
    DAWNSTONE(TfcmMetalSpec.DAWNSTONE);

    private final TfcmMetalSpec spec;

    TfcmMetal(TfcmMetalSpec spec) {
        this.spec = spec;
    }

    @Override
    public String getSerializedName() {
        return spec.serializedName();
    }

    @Override
    public Tier toolTier() {
        if (this == INVAR) return TfcmTiers.INVAR;
        if (this == TITANIUM) return TfcmTiers.TITANIUM;
        if (this == TUNGSTEN_STEEL) return TfcmTiers.TUNGSTEN_STEEL;
        if (this == NETHERITE) return TfcmTiers.NETHERITE;
        throw unsupported("toolTier");
    }

    public boolean hasTools() {
        return spec.hasTools();
    }

    @Override
    public ArmorMaterial armorTier() {
        if (this == INVAR) return TfcmArmorMaterials.INVAR;
        if (this == TITANIUM) return TfcmArmorMaterials.TITANIUM;
        if (this == TUNGSTEN_STEEL) return TfcmArmorMaterials.TUNGSTEN_STEEL;
        if (this == NETHERITE) return TfcmArmorMaterials.NETHERITE;
        throw unsupported("armorTier");
    }

    @Override
    public Metal.Tier metalTier() {
        if (this == INVAR) return Metal.Tier.TIER_III;
        if (this == TITANIUM) return Metal.Tier.TIER_IV;
        if (this == TUNGSTEN_STEEL) return Metal.Tier.TIER_VI;
        if (this == NETHERITE) return Metal.Tier.TIER_V;
        throw unsupported("metalTier");
    }

    @Override
    public Supplier<Block> getFullBlock() {
        return () -> TfcmBlocks.METAL_BLOCKS.get(this).get();
    }

    @Override
    public MapColor mapColor() {
        return MapColor.METAL;
    }

    @Override
    public Rarity getRarity() {
        return spec.rarity();
    }

    public Rarity rarity() {
        return spec.rarity();
    }

    public int color() {
        return spec.color();
    }

    private UnsupportedOperationException unsupported(String method) {
        return new UnsupportedOperationException(method + " is not used by the implemented metal subset: " + spec.serializedName());
    }
}
