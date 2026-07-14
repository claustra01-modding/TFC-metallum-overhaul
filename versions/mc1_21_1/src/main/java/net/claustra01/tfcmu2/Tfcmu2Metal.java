package net.claustra01.tfcmu2;

import net.dries007.tfc.common.LevelTier;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryMetal;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

public enum Tfcmu2Metal implements RegistryMetal {
    COMPRESSED_IRON(Tfcmu2MetalSpec.COMPRESSED_IRON),
    PLATINUM(Tfcmu2MetalSpec.PLATINUM),
    NAQUADAH(Tfcmu2MetalSpec.NAQUADAH),
    IRIDIUM(Tfcmu2MetalSpec.IRIDIUM),
    OSMIUM(Tfcmu2MetalSpec.OSMIUM),
    OSMIRIDIUM(Tfcmu2MetalSpec.OSMIRIDIUM),
    MITHRIL(Tfcmu2MetalSpec.MITHRIL),
    ARCANE(Tfcmu2MetalSpec.ARCANE),
    REFINED_GLOWSTONE(Tfcmu2MetalSpec.REFINED_GLOWSTONE),
    REFINED_OBSIDIAN(Tfcmu2MetalSpec.REFINED_OBSIDIAN),
    ANTIMONY(Tfcmu2MetalSpec.ANTIMONY),
    TITANIUM(Tfcmu2MetalSpec.TITANIUM),
    COBALT(Tfcmu2MetalSpec.COBALT),
    LITHIUM(Tfcmu2MetalSpec.LITHIUM),
    ALUMINUM(Tfcmu2MetalSpec.ALUMINUM),
    CONSTANTAN(Tfcmu2MetalSpec.CONSTANTAN),
    INVAR(Tfcmu2MetalSpec.INVAR),
    ELECTRUM(Tfcmu2MetalSpec.ELECTRUM),
    LEAD(Tfcmu2MetalSpec.LEAD),
    URANIUM(Tfcmu2MetalSpec.URANIUM),
    TUNGSTEN(Tfcmu2MetalSpec.TUNGSTEN),
    SOLDER(Tfcmu2MetalSpec.SOLDER),
    TUNGSTEN_STEEL(Tfcmu2MetalSpec.TUNGSTEN_STEEL),
    NETHERITE(Tfcmu2MetalSpec.NETHERITE);

    private final Tfcmu2MetalSpec spec;

    Tfcmu2Metal(Tfcmu2MetalSpec spec) {
        this.spec = spec;
    }

    @Override
    public String getSerializedName() {
        return spec.serializedName();
    }

    @Override
    public LevelTier toolTier() {
        if (this == INVAR) return Tfcmu2Tiers.INVAR;
        if (this == TITANIUM) return Tfcmu2Tiers.TITANIUM;
        if (this == TUNGSTEN_STEEL) return Tfcmu2Tiers.TUNGSTEN_STEEL;
        throw unsupported("toolTier");
    }

    public boolean hasTools() {
        return spec.hasTools();
    }

    @Override
    public Holder<ArmorMaterial> armorMaterial() {
        if (this == INVAR) return Tfcmu2ArmorMaterials.INVAR.material();
        if (this == TITANIUM) return Tfcmu2ArmorMaterials.TITANIUM.material();
        if (this == TUNGSTEN_STEEL) return Tfcmu2ArmorMaterials.TUNGSTEN_STEEL.material();
        throw unsupported("armorMaterial");
    }

    @Override
    public int armorDurability(ArmorItem.Type type) {
        if (this == INVAR) return Tfcmu2ArmorMaterials.INVAR.durability(type);
        if (this == TITANIUM) return Tfcmu2ArmorMaterials.TITANIUM.durability(type);
        if (this == TUNGSTEN_STEEL) return Tfcmu2ArmorMaterials.TUNGSTEN_STEEL.durability(type);
        throw unsupported("armorDurability");
    }

    @Override
    public Block getBlock(Metal.BlockType type) {
        if (type == Metal.BlockType.BLOCK) {
            return Tfcmu2Blocks.METAL_BLOCKS.get(this).get();
        }
        if (type == Metal.BlockType.BLOCK_SLAB) {
            return Tfcmu2Blocks.METAL_BLOCK_SLABS.get(this).get();
        }
        if (type == Metal.BlockType.BLOCK_STAIRS) {
            return Tfcmu2Blocks.METAL_BLOCK_STAIRS.get(this).get();
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
