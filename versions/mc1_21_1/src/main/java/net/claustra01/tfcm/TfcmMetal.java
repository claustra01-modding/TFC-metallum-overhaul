package net.claustra01.tfcm;

import net.dries007.tfc.common.LevelTier;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryMetal;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

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
    NETHERITE(TfcmMetalSpec.NETHERITE);

    private final TfcmMetalSpec spec;

    TfcmMetal(TfcmMetalSpec spec) {
        this.spec = spec;
    }

    @Override
    public String getSerializedName() {
        return spec.serializedName();
    }

    @Override
    public LevelTier toolTier() {
        if (this == INVAR) return TfcmTiers.INVAR;
        if (this == TITANIUM) return TfcmTiers.TITANIUM;
        if (this == TUNGSTEN_STEEL) return TfcmTiers.TUNGSTEN_STEEL;
        throw unsupported("toolTier");
    }

    public boolean hasTools() {
        return spec.hasTools();
    }

    @Override
    public Holder<ArmorMaterial> armorMaterial() {
        if (this == INVAR) return TfcmArmorMaterials.INVAR.material();
        if (this == TITANIUM) return TfcmArmorMaterials.TITANIUM.material();
        if (this == TUNGSTEN_STEEL) return TfcmArmorMaterials.TUNGSTEN_STEEL.material();
        throw unsupported("armorMaterial");
    }

    @Override
    public int armorDurability(ArmorItem.Type type) {
        if (this == INVAR) return TfcmArmorMaterials.INVAR.durability(type);
        if (this == TITANIUM) return TfcmArmorMaterials.TITANIUM.durability(type);
        if (this == TUNGSTEN_STEEL) return TfcmArmorMaterials.TUNGSTEN_STEEL.durability(type);
        throw unsupported("armorDurability");
    }

    @Override
    public Block getBlock(Metal.BlockType type) {
        if (type == Metal.BlockType.BLOCK) {
            return TfcmBlocks.METAL_BLOCKS.get(this).get();
        }
        if (type == Metal.BlockType.BLOCK_SLAB) {
            return TfcmBlocks.METAL_BLOCK_SLABS.get(this).get();
        }
        if (type == Metal.BlockType.BLOCK_STAIRS) {
            return TfcmBlocks.METAL_BLOCK_STAIRS.get(this).get();
        }
        throw unsupported("getBlock(" + type.name() + ")");
    }

    @Override
    public MapColor mapColor() {
        return MapColor.METAL;
    }

    @Override
    public Rarity rarity() {
        return spec.rarity();
    }

    public int color() {
        return spec.color();
    }

    @Override
    public float weatheringResistance() {
        return -1f;
    }

    private UnsupportedOperationException unsupported(String method) {
        return new UnsupportedOperationException(method + " is not used by the implemented metal subset: " + spec.serializedName());
    }
}
