package net.claustra01.tfcm;

import net.minecraft.world.item.Rarity;

public enum TfcmMetalSpec {
    COMPRESSED_IRON("compressed_iron", Rarity.UNCOMMON, 0x6F6C6B, false),
    PLATINUM("platinum", Rarity.RARE, 0x627C8B, false),
    NAQUADAH("naquadah", Rarity.RARE, 0x4D5742, false),
    IRIDIUM("iridium", Rarity.UNCOMMON, 0xADC4CE, false),
    OSMIUM("osmium", Rarity.UNCOMMON, 0xA5B4CA, false),
    OSMIRIDIUM("osmiridium", Rarity.UNCOMMON, 0x718383, false),
    MITHRIL("mithril", Rarity.COMMON, 0x8DB4BC, false),
    ARCANE("arcane", Rarity.RARE, 0x888CCC, false),
    REFINED_GLOWSTONE("refined_glowstone", Rarity.UNCOMMON, 0xE2B446, false),
    REFINED_OBSIDIAN("refined_obsidian", Rarity.RARE, 0x473752, false),
    ANTIMONY("antimony", Rarity.COMMON, 0xB4AFBD, false),
    TITANIUM("titanium", Rarity.UNCOMMON, 0x898B93, true),
    COBALT("cobalt", Rarity.UNCOMMON, 0x175AB3, false),
    LITHIUM("lithium", Rarity.COMMON, 0xC5CBD1, false),
    ALUMINUM("aluminum", Rarity.COMMON, 0x929799, false),
    CONSTANTAN("constantan", Rarity.COMMON, 0xA45B49, false),
    INVAR("invar", Rarity.COMMON, 0x869A97, true),
    ELECTRUM("electrum", Rarity.UNCOMMON, 0xA67F3A, false),
    LEAD("lead", Rarity.COMMON, 0x393D4A, false),
    URANIUM("uranium", Rarity.UNCOMMON, 0x4E5946, false),
    TUNGSTEN("tungsten", Rarity.EPIC, 0x585F6B, false),
    SOLDER("solder", Rarity.UNCOMMON, 0x888888, false),
    TUNGSTEN_STEEL("tungsten_steel", Rarity.EPIC, 0x2F353E, true),
    NETHERITE("netherite", Rarity.EPIC, 0x3B3230, false);

    private final String serializedName;
    private final Rarity rarity;
    private final int color;
    private final boolean hasTools;

    TfcmMetalSpec(String serializedName, Rarity rarity, int color, boolean hasTools) {
        this.serializedName = serializedName;
        this.rarity = rarity;
        this.color = color;
        this.hasTools = hasTools;
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

    public boolean hasTools() {
        return hasTools;
    }
}
