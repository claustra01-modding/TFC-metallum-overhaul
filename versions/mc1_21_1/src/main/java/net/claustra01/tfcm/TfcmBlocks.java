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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TfcmBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TfcmMod.MOD_ID);

    public static final Map<TfcmMetal, DeferredBlock<Block>> METAL_BLOCKS = registerMetalBlocks();
    public static final Map<TfcmMetal, DeferredBlock<Block>> METAL_BLOCK_SLABS = registerMetalBlockSlabs();
    public static final Map<TfcmMetal, DeferredBlock<Block>> METAL_BLOCK_STAIRS = registerMetalBlockStairs();
    public static final Map<Rock, Map<TfcmOre, DeferredBlock<Block>>> ORES = registerOres();
    public static final Map<Rock, Map<TfcmOre, Map<Ore.Grade, DeferredBlock<Block>>>> GRADED_ORES = registerGradedOres();
    public static final Map<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, DeferredBlock<Block>>>> VANILLA_GRADED_ORES = registerVanillaGradedOres();
    public static final Map<TfcmVanillaStone, Map<String, DeferredBlock<Block>>> COMPAT_VANILLA_ORES = registerCompatVanillaOres();
    public static final Map<TfcmOre, DeferredBlock<Block>> SMALL_ORES = registerSmallOres();
    public static final Map<String, DeferredBlock<Block>> COMPAT_SMALL_ORE_PIECES = registerCompatSmallOrePieces();

    private TfcmBlocks() {
    }

    public static Map<TfcmMetal, DeferredItem<?>> registerMetalBlockItems(DeferredRegister.Items items) {
        final EnumMap<TfcmMetal, DeferredItem<?>> blockItems = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName();
            blockItems.put(metal, items.registerSimpleBlockItem(id, METAL_BLOCKS.get(metal)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmMetal, DeferredItem<?>> registerMetalSlabBlockItems(DeferredRegister.Items items) {
        final EnumMap<TfcmMetal, DeferredItem<?>> blockItems = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_slab";
            blockItems.put(metal, items.registerSimpleBlockItem(id, METAL_BLOCK_SLABS.get(metal)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmMetal, DeferredItem<?>> registerMetalStairsBlockItems(DeferredRegister.Items items) {
        final EnumMap<TfcmMetal, DeferredItem<?>> blockItems = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_stairs";
            blockItems.put(metal, items.registerSimpleBlockItem(id, METAL_BLOCK_STAIRS.get(metal)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<Rock, Map<TfcmOre, DeferredItem<?>>> registerOreBlockItems(DeferredRegister.Items items) {
        final EnumMap<Rock, Map<TfcmOre, DeferredItem<?>>> blockItems = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<TfcmOre, DeferredItem<?>> rockItems = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (ore.isGraded()) {
                    continue;
                }
                final String id = "ore/" + ore.getSerializedName() + "/" + rock.getSerializedName();
                rockItems.put(ore, items.registerSimpleBlockItem(id, ORES.get(rock).get(ore)));
            }
            blockItems.put(rock, Collections.unmodifiableMap(rockItems));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<Rock, Map<TfcmOre, Map<Ore.Grade, DeferredItem<?>>>> registerGradedOreBlockItems(DeferredRegister.Items items) {
        final EnumMap<Rock, Map<TfcmOre, Map<Ore.Grade, DeferredItem<?>>>> blockItems = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<TfcmOre, Map<Ore.Grade, DeferredItem<?>>> rockItems = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (!ore.isGraded()) {
                    continue;
                }
                final EnumMap<Ore.Grade, DeferredItem<?>> gradeItems = new EnumMap<>(Ore.Grade.class);
                for (Ore.Grade grade : Ore.Grade.values()) {
                    final String gradeName = grade.name().toLowerCase(Locale.ROOT);
                    final String id = "ore/" + gradeName + "_" + ore.getSerializedName() + "/" + rock.getSerializedName();
                    gradeItems.put(grade, items.registerSimpleBlockItem(id, GRADED_ORES.get(rock).get(ore).get(grade)));
                }
                rockItems.put(ore, Collections.unmodifiableMap(gradeItems));
            }
            blockItems.put(rock, Collections.unmodifiableMap(rockItems));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, DeferredItem<?>>>> registerVanillaGradedOreBlockItems(DeferredRegister.Items items) {
        final EnumMap<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, DeferredItem<?>>>> blockItems = new EnumMap<>(TfcmVanillaStone.class);
        for (TfcmVanillaStone stone : TfcmVanillaStone.values()) {
            final EnumMap<TfcmOre, Map<Ore.Grade, DeferredItem<?>>> stoneItems = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (!ore.isGraded()) {
                    continue;
                }
                final EnumMap<Ore.Grade, DeferredItem<?>> gradeItems = new EnumMap<>(Ore.Grade.class);
                for (Ore.Grade grade : Ore.Grade.values()) {
                    final String gradeName = grade.name().toLowerCase(Locale.ROOT);
                    final String id = "ore/" + gradeName + "_" + ore.getSerializedName() + "/" + stone.getSerializedName();
                    gradeItems.put(grade, items.registerSimpleBlockItem(id, VANILLA_GRADED_ORES.get(stone).get(ore).get(grade)));
                }
                stoneItems.put(ore, Collections.unmodifiableMap(gradeItems));
            }
            blockItems.put(stone, Collections.unmodifiableMap(stoneItems));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmVanillaStone, Map<String, DeferredItem<?>>> registerCompatVanillaOreBlockItems(DeferredRegister.Items items) {
        final EnumMap<TfcmVanillaStone, Map<String, DeferredItem<?>>> blockItems = new EnumMap<>(TfcmVanillaStone.class);
        for (TfcmVanillaStone stone : TfcmVanillaStone.values()) {
            final Map<String, DeferredItem<?>> stoneItems = new HashMap<>();
            for (Map.Entry<String, DeferredBlock<Block>> entry : COMPAT_VANILLA_ORES.get(stone).entrySet()) {
                final String oreName = entry.getKey();
                final String id = "ore/" + oreName + "/" + stone.getSerializedName();
                stoneItems.put(oreName, items.registerSimpleBlockItem(id, entry.getValue()));
            }
            blockItems.put(stone, Collections.unmodifiableMap(stoneItems));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<TfcmOre, DeferredItem<?>> registerSmallOreBlockItems(DeferredRegister.Items items) {
        final EnumMap<TfcmOre, DeferredItem<?>> blockItems = new EnumMap<>(TfcmOre.class);
        for (TfcmOre ore : TfcmOre.VALUES) {
            if (!ore.isGraded()) {
                continue;
            }
            final String id = "ore/small_" + ore.getSerializedName();
            blockItems.put(ore, items.registerSimpleBlockItem(id, SMALL_ORES.get(ore)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    private static Map<TfcmMetal, DeferredBlock<Block>> registerMetalBlocks() {
        final EnumMap<TfcmMetal, DeferredBlock<Block>> blocks = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName();
            blocks.put(metal, BLOCKS.register(id, Metal.BlockType.BLOCK.create(metal)));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<TfcmMetal, DeferredBlock<Block>> registerMetalBlockSlabs() {
        final EnumMap<TfcmMetal, DeferredBlock<Block>> blocks = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_slab";
            blocks.put(metal, BLOCKS.register(id, Metal.BlockType.BLOCK_SLAB.create(metal)));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<TfcmMetal, DeferredBlock<Block>> registerMetalBlockStairs() {
        final EnumMap<TfcmMetal, DeferredBlock<Block>> blocks = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_stairs";
            blocks.put(metal, BLOCKS.register(id, Metal.BlockType.BLOCK_STAIRS.create(metal)));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<Rock, Map<TfcmOre, DeferredBlock<Block>>> registerOres() {
        final EnumMap<Rock, Map<TfcmOre, DeferredBlock<Block>>> rocks = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<TfcmOre, DeferredBlock<Block>> ores = new EnumMap<>(TfcmOre.class);
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

    private static Map<Rock, Map<TfcmOre, Map<Ore.Grade, DeferredBlock<Block>>>> registerGradedOres() {
        final EnumMap<Rock, Map<TfcmOre, Map<Ore.Grade, DeferredBlock<Block>>>> rocks = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<TfcmOre, Map<Ore.Grade, DeferredBlock<Block>>> ores = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (!ore.isGraded()) {
                    continue;
                }
                final EnumMap<Ore.Grade, DeferredBlock<Block>> grades = new EnumMap<>(Ore.Grade.class);
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

    private static Map<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, DeferredBlock<Block>>>> registerVanillaGradedOres() {
        final EnumMap<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, DeferredBlock<Block>>>> stones = new EnumMap<>(TfcmVanillaStone.class);
        for (TfcmVanillaStone stone : TfcmVanillaStone.values()) {
            final EnumMap<TfcmOre, Map<Ore.Grade, DeferredBlock<Block>>> ores = new EnumMap<>(TfcmOre.class);
            for (TfcmOre ore : TfcmOre.VALUES) {
                if (!ore.isGraded()) {
                    continue;
                }
                final EnumMap<Ore.Grade, DeferredBlock<Block>> grades = new EnumMap<>(Ore.Grade.class);
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

    private static Map<TfcmVanillaStone, Map<String, DeferredBlock<Block>>> registerCompatVanillaOres() {
        final EnumMap<TfcmVanillaStone, Map<String, DeferredBlock<Block>>> stones = new EnumMap<>(TfcmVanillaStone.class);
        final List<String> oreNames = TfcmCompatOres.getLoadedOreNames();
        for (TfcmVanillaStone stone : TfcmVanillaStone.values()) {
            final Map<String, DeferredBlock<Block>> ores = new HashMap<>();
            for (String oreName : oreNames) {
                final String id = "ore/" + oreName + "/" + stone.getSerializedName();
                ores.put(oreName, BLOCKS.register(id, () -> createVanillaOreBlock(stone.baseBlock())));
            }
            stones.put(stone, Collections.unmodifiableMap(ores));
        }
        return Collections.unmodifiableMap(stones);
    }

    private static Map<TfcmOre, DeferredBlock<Block>> registerSmallOres() {
        final EnumMap<TfcmOre, DeferredBlock<Block>> ores = new EnumMap<>(TfcmOre.class);
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

    private static Map<String, DeferredBlock<Block>> registerCompatSmallOrePieces() {
        final Map<String, DeferredBlock<Block>> ores = new HashMap<>();
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
        return new Block(BlockBehaviour.Properties.ofFullCopy(baseBlock).requiresCorrectToolForDrops());
    }
}
