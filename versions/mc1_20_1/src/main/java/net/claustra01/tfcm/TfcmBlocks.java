package net.claustra01.tfcm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.List;
import java.util.Map;

import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class TfcmBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TfcmMod.MOD_ID);

    public static final Map<TfcmMetal, RegistryObject<Block>> METAL_BLOCKS = registerMetalBlocks();
    public static final Map<TfcmMetal, RegistryObject<Block>> METAL_BLOCK_SLABS = registerMetalBlockSlabs();
    public static final Map<TfcmMetal, RegistryObject<Block>> METAL_BLOCK_STAIRS = registerMetalBlockStairs();
    public static final Map<Rock, Map<TfcmOre, RegistryObject<Block>>> ORES = registerOres();
    public static final Map<Rock, Map<TfcmOre, Map<Ore.Grade, RegistryObject<Block>>>> GRADED_ORES = registerGradedOres();
    public static final Map<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, RegistryObject<Block>>>> VANILLA_GRADED_ORES = registerVanillaGradedOres();
    public static final Map<TfcmVanillaStone, Map<String, RegistryObject<Block>>> COMPAT_VANILLA_ORES = registerCompatVanillaOres();
    public static final Map<TfcmOre, RegistryObject<Block>> SMALL_ORES = registerSmallOres();
    public static final Map<String, RegistryObject<Block>> COMPAT_SMALL_ORE_PIECES = registerCompatSmallOrePieces();

    private TfcmBlocks() {
    }

    public static Map<TfcmMetal, RegistryObject<?>> registerMetalBlockItems(DeferredRegister<Item> items) {
        final EnumMap<TfcmMetal, RegistryObject<?>> blockItems = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName();
            blockItems.put(metal, registerBlockItem(items, id, METAL_BLOCKS.get(metal)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmMetal, RegistryObject<?>> registerMetalSlabBlockItems(DeferredRegister<Item> items) {
        final EnumMap<TfcmMetal, RegistryObject<?>> blockItems = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_slab";
            blockItems.put(metal, registerBlockItem(items, id, METAL_BLOCK_SLABS.get(metal)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmMetal, RegistryObject<?>> registerMetalStairsBlockItems(DeferredRegister<Item> items) {
        final EnumMap<TfcmMetal, RegistryObject<?>> blockItems = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_stairs";
            blockItems.put(metal, registerBlockItem(items, id, METAL_BLOCK_STAIRS.get(metal)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<Rock, Map<TfcmOre, RegistryObject<?>>> registerOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Rock, Map<TfcmOre, RegistryObject<?>>> blockItems = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<TfcmOre, RegistryObject<?>> rockItems = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (ore.isGraded()) {
                    continue;
                }
                final String id = "ore/" + ore.getSerializedName() + "/" + rock.getSerializedName();
                rockItems.put(ore, registerBlockItem(items, id, ORES.get(rock).get(ore)));
            }
            blockItems.put(rock, Collections.unmodifiableMap(rockItems));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<Rock, Map<TfcmOre, Map<Ore.Grade, RegistryObject<?>>>> registerGradedOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Rock, Map<TfcmOre, Map<Ore.Grade, RegistryObject<?>>>> blockItems = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<TfcmOre, Map<Ore.Grade, RegistryObject<?>>> rockItems = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (!ore.isGraded()) {
                    continue;
                }
                final EnumMap<Ore.Grade, RegistryObject<?>> gradeItems = new EnumMap<>(Ore.Grade.class);
                for (Ore.Grade grade : Ore.Grade.values()) {
                    final String gradeName = grade.name().toLowerCase(Locale.ROOT);
                    final String id = "ore/" + gradeName + "_" + ore.getSerializedName() + "/" + rock.getSerializedName();
                    gradeItems.put(grade, registerBlockItem(items, id, GRADED_ORES.get(rock).get(ore).get(grade)));
                }
                rockItems.put(ore, Collections.unmodifiableMap(gradeItems));
            }
            blockItems.put(rock, Collections.unmodifiableMap(rockItems));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, RegistryObject<?>>>> registerVanillaGradedOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, RegistryObject<?>>>> blockItems = new EnumMap<>(TfcmVanillaStone.class);
        for (TfcmVanillaStone stone : TfcmVanillaStone.values()) {
            final EnumMap<TfcmOre, Map<Ore.Grade, RegistryObject<?>>> stoneItems = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (!ore.isGraded()) {
                    continue;
                }
                final EnumMap<Ore.Grade, RegistryObject<?>> gradeItems = new EnumMap<>(Ore.Grade.class);
                for (Ore.Grade grade : Ore.Grade.values()) {
                    final String gradeName = grade.name().toLowerCase(Locale.ROOT);
                    final String id = "ore/" + gradeName + "_" + ore.getSerializedName() + "/" + stone.getSerializedName();
                    gradeItems.put(grade, registerBlockItem(items, id, VANILLA_GRADED_ORES.get(stone).get(ore).get(grade)));
                }
                stoneItems.put(ore, Collections.unmodifiableMap(gradeItems));
            }
            blockItems.put(stone, Collections.unmodifiableMap(stoneItems));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmVanillaStone, Map<String, RegistryObject<?>>> registerCompatVanillaOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<TfcmVanillaStone, Map<String, RegistryObject<?>>> blockItems = new EnumMap<>(TfcmVanillaStone.class);
        for (TfcmVanillaStone stone : TfcmVanillaStone.values()) {
            final Map<String, RegistryObject<?>> stoneItems = new HashMap<>();
            for (Map.Entry<String, RegistryObject<Block>> entry : COMPAT_VANILLA_ORES.get(stone).entrySet()) {
                final String oreName = entry.getKey();
                final String id = "ore/" + oreName + "/" + stone.getSerializedName();
                stoneItems.put(oreName, registerBlockItem(items, id, entry.getValue()));
            }
            blockItems.put(stone, Collections.unmodifiableMap(stoneItems));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmOre, RegistryObject<?>> registerSmallOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<TfcmOre, RegistryObject<?>> blockItems = new EnumMap<>(TfcmOre.class);
        for (TfcmOre ore : TfcmOre.VALUES) {
            if (!ore.isGraded()) {
                continue;
            }
            final String id = "ore/small_" + ore.getSerializedName();
            blockItems.put(ore, registerBlockItem(items, id, SMALL_ORES.get(ore)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    private static Map<TfcmMetal, RegistryObject<Block>> registerMetalBlocks() {
        final EnumMap<TfcmMetal, RegistryObject<Block>> blocks = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName();
            blocks.put(metal, BLOCKS.register(id, Metal.BlockType.BLOCK.create(metal)));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<TfcmMetal, RegistryObject<Block>> registerMetalBlockSlabs() {
        final EnumMap<TfcmMetal, RegistryObject<Block>> blocks = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_slab";
            blocks.put(metal, BLOCKS.register(id, Metal.BlockType.BLOCK_SLAB.create(metal)));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<TfcmMetal, RegistryObject<Block>> registerMetalBlockStairs() {
        final EnumMap<TfcmMetal, RegistryObject<Block>> blocks = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_stairs";
            blocks.put(metal, BLOCKS.register(id, Metal.BlockType.BLOCK_STAIRS.create(metal)));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<Rock, Map<TfcmOre, RegistryObject<Block>>> registerOres() {
        final EnumMap<Rock, Map<TfcmOre, RegistryObject<Block>>> rocks = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<TfcmOre, RegistryObject<Block>> ores = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (ore.isGraded()) {
                    continue;
                }
                final String id = "ore/" + ore.getSerializedName() + "/" + rock.getSerializedName();
                ores.put(ore, BLOCKS.register(id, () -> ore.create(rock)));
            }
            rocks.put(rock, Collections.unmodifiableMap(ores));
        }
        return Collections.unmodifiableMap(rocks);
    }

    private static Map<Rock, Map<TfcmOre, Map<Ore.Grade, RegistryObject<Block>>>> registerGradedOres() {
        final EnumMap<Rock, Map<TfcmOre, Map<Ore.Grade, RegistryObject<Block>>>> rocks = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<TfcmOre, Map<Ore.Grade, RegistryObject<Block>>> ores = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (!ore.isGraded()) {
                    continue;
                }
                final EnumMap<Ore.Grade, RegistryObject<Block>> grades = new EnumMap<>(Ore.Grade.class);
                for (Ore.Grade grade : Ore.Grade.values()) {
                    final String gradeName = grade.name().toLowerCase(Locale.ROOT);
                    final String id = "ore/" + gradeName + "_" + ore.getSerializedName() + "/" + rock.getSerializedName();
                    grades.put(grade, BLOCKS.register(id, () -> ore.create(rock)));
                }
                ores.put(ore, Collections.unmodifiableMap(grades));
            }
            rocks.put(rock, Collections.unmodifiableMap(ores));
        }
        return Collections.unmodifiableMap(rocks);
    }

    private static Map<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, RegistryObject<Block>>>> registerVanillaGradedOres() {
        final EnumMap<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, RegistryObject<Block>>>> stones = new EnumMap<>(TfcmVanillaStone.class);
        for (TfcmVanillaStone stone : TfcmVanillaStone.values()) {
            final EnumMap<TfcmOre, Map<Ore.Grade, RegistryObject<Block>>> ores = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (!ore.isGraded()) {
                    continue;
                }
                final EnumMap<Ore.Grade, RegistryObject<Block>> grades = new EnumMap<>(Ore.Grade.class);
                for (Ore.Grade grade : Ore.Grade.values()) {
                    final String gradeName = grade.name().toLowerCase(Locale.ROOT);
                    final String id = "ore/" + gradeName + "_" + ore.getSerializedName() + "/" + stone.getSerializedName();
                    grades.put(grade, BLOCKS.register(id, () -> createVanillaOreBlock(stone.baseBlock())));
                }
                ores.put(ore, Collections.unmodifiableMap(grades));
            }
            stones.put(stone, Collections.unmodifiableMap(ores));
        }
        return Collections.unmodifiableMap(stones);
    }

    private static Map<TfcmVanillaStone, Map<String, RegistryObject<Block>>> registerCompatVanillaOres() {
        final EnumMap<TfcmVanillaStone, Map<String, RegistryObject<Block>>> stones = new EnumMap<>(TfcmVanillaStone.class);
        final List<String> oreNames = TfcmCompatOres.getLoadedOreNames();
        for (TfcmVanillaStone stone : TfcmVanillaStone.values()) {
            final Map<String, RegistryObject<Block>> ores = new HashMap<>();
            for (String oreName : oreNames) {
                final String id = "ore/" + oreName + "/" + stone.getSerializedName();
                ores.put(oreName, BLOCKS.register(id, () -> createVanillaOreBlock(stone.baseBlock())));
            }
            stones.put(stone, Collections.unmodifiableMap(ores));
        }
        return Collections.unmodifiableMap(stones);
    }

    private static Map<TfcmOre, RegistryObject<Block>> registerSmallOres() {
        final EnumMap<TfcmOre, RegistryObject<Block>> ores = new EnumMap<>(TfcmOre.class);
        for (TfcmOre ore : TfcmOre.VALUES) {
            if (!ore.isGraded()) {
                continue;
            }
            final String id = "ore/small_" + ore.getSerializedName();
            ores.put(ore, BLOCKS.register(id, () -> GroundcoverBlock.looseOre(BlockBehaviour.Properties.of()
                .mapColor(MapColor.GRASS)
                .strength(0.05F, 0.0F)
                .sound(SoundType.NETHER_ORE)
                .noCollission()
                .pushReaction(PushReaction.DESTROY))));
        }
        return Collections.unmodifiableMap(ores);
    }

    private static Map<String, RegistryObject<Block>> registerCompatSmallOrePieces() {
        final Map<String, RegistryObject<Block>> ores = new HashMap<>();
        for (String oreName : TfcmContentNames.ORE_PIECES_WITHOUT_SAMPLES) {
            final String id = "ore/small_" + oreName;
            ores.put(oreName, BLOCKS.register(id, () -> GroundcoverBlock.looseOre(BlockBehaviour.Properties.of()
                .mapColor(MapColor.GRASS)
                .strength(0.05F, 0.0F)
                .sound(SoundType.NETHER_ORE)
                .noCollission()
                .pushReaction(PushReaction.DESTROY))));
        }
        return Collections.unmodifiableMap(ores);
    }

    private static Block createVanillaOreBlock(Block baseBlock) {
        return new Block(BlockBehaviour.Properties.copy(baseBlock).requiresCorrectToolForDrops());
    }

    private static RegistryObject<Item> registerBlockItem(DeferredRegister<Item> items, String id, RegistryObject<? extends Block> block) {
        return items.register(id, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
