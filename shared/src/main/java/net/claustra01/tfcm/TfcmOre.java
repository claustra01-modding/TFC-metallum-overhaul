package net.claustra01.tfcm;

import java.util.Locale;

import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public enum TfcmOre {
    NATIVE_PLATINUM(true, "platinum"),
    NATIVE_NAQUADAH(true, "naquadah"),
    NATIVE_IRIDIUM(true, "iridium"),
    NATIVE_OSMIUM(true, "osmium"),
    FLUORITE(false, "fluorite"),
    QUARTZ(false, "quartz"),
    RUTILE(true, "titanium"),
    COBALTITE(true, "cobalt"),
    SPODUMENE(true, "lithium"),
    BAUXITE(true, "aluminum"),
    GALENA(true, "lead"),
    URANINITE(true, "uranium"),
    MITHRIL_MATRIX(true, "mithril"),
    STIBNITE(true, "antimony"),
    WOLFRAMITE(true, "tungsten");

    public static final TfcmOre[] VALUES = values();

    private final String serializedName;
    private final boolean graded;
    private final String metalTagName;

    TfcmOre(boolean graded, String metalTagName) {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.graded = graded;
        this.metalTagName = metalTagName;
    }

    public String getSerializedName() {
        return serializedName;
    }

    public boolean isGraded() {
        return graded;
    }

    public String metalTagName() {
        return metalTagName;
    }

    public String oreWashingSerializedName() {
        return serializedName;
    }

    public Block create(RegistryRock rock) {
        return new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .strength(rock.category().hardness(6.5F), 10.0F)
            .requiresCorrectToolForDrops());
    }
}
