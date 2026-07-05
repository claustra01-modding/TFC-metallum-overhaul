package net.claustra01.tfcmu2;

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

public final class Tfcmu2Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Tfcmu2Mod.MOD_ID);

    public static final Map<Tfcmu2Metal, RegistryObject<Block>> METAL_BLOCKS = registerMetalBlocks();
    public static final Map<Tfcmu2Metal, RegistryObject<Block>> METAL_BLOCK_SLABS = registerMetalBlockSlabs();
    public static final Map<Tfcmu2Metal, RegistryObject<Block>> METAL_BLOCK_STAIRS = registerMetalBlockStairs();
    public static final Map<Rock, Map<Tfcmu2Ore, RegistryObject<Block>>> ORES = registerOres();
    public static final Map<Rock, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<Block>>>> GRADED_ORES = registerGradedOres();
    public static final Map<Tfcmu2VanillaStone, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<Block>>>> VANILLA_GRADED_ORES = registerVanillaGradedOres();
    public static final Map<Tfcmu2VanillaStone, Map<String, RegistryObject<Block>>> COMPAT_VANILLA_ORES = registerCompatVanillaOres();
    public static final Map<Tfcmu2Ore, RegistryObject<Block>> SMALL_ORES = registerSmallOres();
    public static final Map<String, RegistryObject<Block>> COMPAT_SMALL_ORE_PIECES = registerCompatSmallOrePieces();

    private Tfcmu2Blocks() {
    }

    public static Map<Tfcmu2Metal, RegistryObject<?>> registerMetalBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Tfcmu2Metal, RegistryObject<?>> blockItems = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            final String id = "metal/block/" + metal.getSerializedName();
            blockItems.put(metal, registerBlockItem(items, id, METAL_BLOCKS.get(metal)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<Tfcmu2Metal, RegistryObject<?>> registerMetalSlabBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Tfcmu2Metal, RegistryObject<?>> blockItems = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_slab";
            blockItems.put(metal, registerBlockItem(items, id, METAL_BLOCK_SLABS.get(metal)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<Tfcmu2Metal, RegistryObject<?>> registerMetalStairsBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Tfcmu2Metal, RegistryObject<?>> blockItems = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_stairs";
            blockItems.put(metal, registerBlockItem(items, id, METAL_BLOCK_STAIRS.get(metal)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    public static Map<Rock, Map<Tfcmu2Ore, RegistryObject<?>>> registerOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Rock, Map<Tfcmu2Ore, RegistryObject<?>>> blockItems = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<Tfcmu2Ore, RegistryObject<?>> rockItems = new EnumMap<>(Tfcmu2Ore.class);
            for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
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

    public static Map<Rock, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<?>>>> registerGradedOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Rock, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<?>>>> blockItems = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<?>>> rockItems = new EnumMap<>(Tfcmu2Ore.class);
            for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
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

    public static Map<Tfcmu2VanillaStone, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<?>>>> registerVanillaGradedOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Tfcmu2VanillaStone, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<?>>>> blockItems = new EnumMap<>(Tfcmu2VanillaStone.class);
        for (Tfcmu2VanillaStone stone : Tfcmu2VanillaStone.values()) {
            final EnumMap<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<?>>> stoneItems = new EnumMap<>(Tfcmu2Ore.class);
            for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
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

    public static Map<Tfcmu2VanillaStone, Map<String, RegistryObject<?>>> registerCompatVanillaOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Tfcmu2VanillaStone, Map<String, RegistryObject<?>>> blockItems = new EnumMap<>(Tfcmu2VanillaStone.class);
        for (Tfcmu2VanillaStone stone : Tfcmu2VanillaStone.values()) {
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

    public static Map<Tfcmu2Ore, RegistryObject<?>> registerSmallOreBlockItems(DeferredRegister<Item> items) {
        final EnumMap<Tfcmu2Ore, RegistryObject<?>> blockItems = new EnumMap<>(Tfcmu2Ore.class);
        for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
            if (!ore.isGraded()) {
                continue;
            }
            final String id = "ore/small_" + ore.getSerializedName();
            blockItems.put(ore, registerBlockItem(items, id, SMALL_ORES.get(ore)));
        }
        return Collections.unmodifiableMap(blockItems);
    }

    private static Map<Tfcmu2Metal, RegistryObject<Block>> registerMetalBlocks() {
        final EnumMap<Tfcmu2Metal, RegistryObject<Block>> blocks = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            final String id = "metal/block/" + metal.getSerializedName();
            blocks.put(metal, BLOCKS.register(id, Metal.BlockType.BLOCK.create(metal)));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<Tfcmu2Metal, RegistryObject<Block>> registerMetalBlockSlabs() {
        final EnumMap<Tfcmu2Metal, RegistryObject<Block>> blocks = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_slab";
            blocks.put(metal, BLOCKS.register(id, Metal.BlockType.BLOCK_SLAB.create(metal)));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<Tfcmu2Metal, RegistryObject<Block>> registerMetalBlockStairs() {
        final EnumMap<Tfcmu2Metal, RegistryObject<Block>> blocks = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            final String id = "metal/block/" + metal.getSerializedName() + "_stairs";
            blocks.put(metal, BLOCKS.register(id, Metal.BlockType.BLOCK_STAIRS.create(metal)));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<Rock, Map<Tfcmu2Ore, RegistryObject<Block>>> registerOres() {
        final EnumMap<Rock, Map<Tfcmu2Ore, RegistryObject<Block>>> rocks = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<Tfcmu2Ore, RegistryObject<Block>> ores = new EnumMap<>(Tfcmu2Ore.class);
            for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
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

    private static Map<Rock, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<Block>>>> registerGradedOres() {
        final EnumMap<Rock, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<Block>>>> rocks = new EnumMap<>(Rock.class);
        for (Rock rock : Rock.VALUES) {
            final EnumMap<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<Block>>> ores = new EnumMap<>(Tfcmu2Ore.class);
            for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
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

    private static Map<Tfcmu2VanillaStone, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<Block>>>> registerVanillaGradedOres() {
        final EnumMap<Tfcmu2VanillaStone, Map<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<Block>>>> stones = new EnumMap<>(Tfcmu2VanillaStone.class);
        for (Tfcmu2VanillaStone stone : Tfcmu2VanillaStone.values()) {
            final EnumMap<Tfcmu2Ore, Map<Ore.Grade, RegistryObject<Block>>> ores = new EnumMap<>(Tfcmu2Ore.class);
            for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
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

    private static Map<Tfcmu2VanillaStone, Map<String, RegistryObject<Block>>> registerCompatVanillaOres() {
        final EnumMap<Tfcmu2VanillaStone, Map<String, RegistryObject<Block>>> stones = new EnumMap<>(Tfcmu2VanillaStone.class);
        final List<String> oreNames = Tfcmu2CompatOres.getLoadedOreNames();
        for (Tfcmu2VanillaStone stone : Tfcmu2VanillaStone.values()) {
            final Map<String, RegistryObject<Block>> ores = new HashMap<>();
            for (String oreName : oreNames) {
                final String id = "ore/" + oreName + "/" + stone.getSerializedName();
                ores.put(oreName, BLOCKS.register(id, () -> createVanillaOreBlock(stone.baseBlock())));
            }
            stones.put(stone, Collections.unmodifiableMap(ores));
        }
        return Collections.unmodifiableMap(stones);
    }

    private static Map<Tfcmu2Ore, RegistryObject<Block>> registerSmallOres() {
        final EnumMap<Tfcmu2Ore, RegistryObject<Block>> ores = new EnumMap<>(Tfcmu2Ore.class);
        for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
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
        for (String oreName : Tfcmu2ContentNames.ORE_PIECES_WITHOUT_SAMPLES) {
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
