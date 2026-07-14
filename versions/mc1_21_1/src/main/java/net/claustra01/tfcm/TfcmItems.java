package net.claustra01.tfcm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Metal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TfcmItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TfcmMod.MOD_ID);
    public static final boolean TFC_MORE_ITEMS_LOADED = ModList.get().isLoaded(TfcmMod.TFC_MORE_ITEMS_MOD_ID);
    public static final boolean TFC_ORE_WASHING_LOADED = ModList.get().isLoaded(TfcmMod.TFC_ORE_WASHING_MOD_ID);
    public static final boolean TFC_METAL_TOOLS_LOADED = ModList.get().isLoaded(TfcmMod.TFC_METAL_TOOLS_MOD_ID);
    public static final boolean TFC_HOT_OR_NOT_LOADED = ModList.get().isLoaded(TfcmMod.TFC_HOT_OR_NOT_MOD_ID);
    public static final Map<TfcmMetal, DeferredItem<Item>> METAL_INGOTS = registerMetalItems("ingot", Metal.ItemType.INGOT);
    public static final DeferredItem<Item> HIGH_CARBON_TUNGSTEN_STEEL_INGOT = ITEMS.register("metal/ingot/high_carbon_tungsten_steel", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CUT_QUARTZ = ITEMS.register("gem/cut_quartz", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FLUORITE_POWDER = ITEMS.register("powder/fluorite", () -> new Item(new Item.Properties()));
    public static final Map<TfcmMetal, DeferredItem<Item>> METAL_DOUBLE_INGOTS = registerMetalItems("double_ingot", Metal.ItemType.DOUBLE_INGOT);
    public static final Map<TfcmMetal, DeferredItem<Item>> METAL_SHEETS = registerMetalItems("sheet", Metal.ItemType.SHEET);
    public static final Map<TfcmMetal, DeferredItem<Item>> METAL_DOUBLE_SHEETS = registerMetalItems("double_sheet", Metal.ItemType.DOUBLE_SHEET);
    public static final Map<TfcmMetal, DeferredItem<Item>> METAL_RODS = registerMetalItems("rod", Metal.ItemType.ROD);
    public static final Map<TfcmMetal, Map<Metal.ItemType, DeferredItem<Item>>> METAL_TOOL_ITEMS = registerMetalToolItems();
    public static final Map<TfcmMetal, DeferredItem<Item>> METAL_TOOL_CROSSGUARDS = registerCompatItems("crossguard", false);
    public static final Map<TfcmMetal, DeferredItem<Item>> METAL_TOOL_POMMELS = registerCompatItems("pommel", false);
    public static final Map<TfcmMetal, DeferredItem<Item>> HOT_OR_NOT_TONG_PARTS = registerCompatItems("tong_part", false);
    public static final Map<TfcmMetal, DeferredItem<Item>> HOT_OR_NOT_TONGS = registerCompatItems("tongs", true);
    public static final Map<TfcmMetal, Map<TfcmMoreItemType, DeferredItem<Item>>> MORE_METAL_ITEMS = TFC_MORE_ITEMS_LOADED
        ? registerMoreMetalItems()
        : Collections.emptyMap();
    public static final Map<TfcmOre, Map<TfcmOreWashingItemType, DeferredItem<Item>>> ORE_WASHING_ORE_ITEMS = TFC_ORE_WASHING_LOADED
        ? registerOreWashingOreItems()
        : Collections.emptyMap();
    public static final Map<TfcmMetal, DeferredItem<?>> METAL_BLOCK_ITEMS = TfcmBlocks.registerMetalBlockItems(ITEMS);
    public static final Map<TfcmMetal, DeferredItem<?>> METAL_BLOCK_SLAB_ITEMS = TfcmBlocks.registerMetalSlabBlockItems(ITEMS);
    public static final Map<TfcmMetal, DeferredItem<?>> METAL_BLOCK_STAIRS_ITEMS = TfcmBlocks.registerMetalStairsBlockItems(ITEMS);
    public static final Map<TfcmOre, DeferredItem<Item>> ORES = registerOreItems();
    public static final Map<TfcmOre, Map<Ore.Grade, DeferredItem<Item>>> GRADED_ORES = registerGradedOreItems();
    public static final Map<Rock, Map<TfcmOre, DeferredItem<?>>> ORE_BLOCK_ITEMS = TfcmBlocks.registerOreBlockItems(ITEMS);
    public static final Map<Rock, Map<TfcmOre, Map<Ore.Grade, DeferredItem<?>>>> GRADED_ORE_BLOCK_ITEMS = TfcmBlocks.registerGradedOreBlockItems(ITEMS);
    public static final Map<TfcmVanillaStone, Map<TfcmOre, Map<Ore.Grade, DeferredItem<?>>>> VANILLA_GRADED_ORE_BLOCK_ITEMS = TfcmBlocks.registerVanillaGradedOreBlockItems(ITEMS);
    public static final Map<TfcmVanillaStone, Map<String, DeferredItem<?>>> COMPAT_VANILLA_ORE_BLOCK_ITEMS = TfcmBlocks.registerCompatVanillaOreBlockItems(ITEMS);
    public static final Map<TfcmOre, DeferredItem<?>> SMALL_ORE_BLOCK_ITEMS = TfcmBlocks.registerSmallOreBlockItems(ITEMS);

    private TfcmItems() {
    }

    private static Map<TfcmMetal, DeferredItem<Item>> registerMetalItems(String itemTypePath, Metal.ItemType itemType) {
        final EnumMap<TfcmMetal, DeferredItem<Item>> items = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            items.put(metal, ITEMS.register("metal/" + itemTypePath + "/" + metal.getSerializedName(), () -> itemType.create(metal)));
        }
        return Collections.unmodifiableMap(items);
    }

    private static Map<TfcmMetal, Map<Metal.ItemType, DeferredItem<Item>>> registerMetalToolItems() {
        final EnumMap<TfcmMetal, Map<Metal.ItemType, DeferredItem<Item>>> itemsByMetal = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            if (!metal.hasTools()) {
                continue;
            }
            final EnumMap<Metal.ItemType, DeferredItem<Item>> itemsByType = new EnumMap<>(Metal.ItemType.class);
            for (Metal.ItemType type : Metal.ItemType.values()) {
                if (isBaseMetalForm(type)) {
                    continue;
                }
                final String path = type.name().toLowerCase(Locale.ROOT);
                itemsByType.put(type, ITEMS.register("metal/" + path + "/" + metal.getSerializedName(), () -> type.create(metal)));
            }
            itemsByMetal.put(metal, Collections.unmodifiableMap(itemsByType));
        }
        return Collections.unmodifiableMap(itemsByMetal);
    }

    private static boolean isBaseMetalForm(Metal.ItemType type) {
        return type == Metal.ItemType.INGOT
            || type == Metal.ItemType.DOUBLE_INGOT
            || type == Metal.ItemType.SHEET
            || type == Metal.ItemType.DOUBLE_SHEET
            || type == Metal.ItemType.ROD;
    }

    private static Map<TfcmMetal, DeferredItem<Item>> registerCompatItems(String form, boolean tongs) {
        final EnumMap<TfcmMetal, DeferredItem<Item>> items = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            if (!metal.hasTools()) {
                continue;
            }
            final String id = "metal/" + form + "/" + metal.getSerializedName();
            items.put(metal, ITEMS.register(id, () -> tongs ? createHotOrNotTongs(metal) : new Item(new Item.Properties().rarity(metal.rarity()))));
        }
        return Collections.unmodifiableMap(items);
    }

    private static Item createHotOrNotTongs(TfcmMetal metal) {
        final Item.Properties properties = new Item.Properties().rarity(metal.rarity());
        if (!TFC_HOT_OR_NOT_LOADED) {
            return new Item(properties);
        }
        try {
            final Class<?> type = Class.forName("tfchotornot.common.items.TongsItem");
            return (Item) type.getConstructor(Item.Properties.class, Tier.class)
                .newInstance(properties, metal.toolTier());
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to create TFC Hot or Not tongs for " + metal.getSerializedName(), exception);
        }
    }

    public static boolean isOptionalCompatItemEnabled(ResourceLocation id) {
        final String path = id.getPath();
        if (path.startsWith("metal/crossguard/") || path.startsWith("metal/pommel/")) {
            return TFC_METAL_TOOLS_LOADED;
        }
        if (path.startsWith("metal/tongs/") || path.startsWith("metal/tong_part/")) {
            return TFC_HOT_OR_NOT_LOADED;
        }
        return true;
    }

    private static Map<TfcmMetal, Map<TfcmMoreItemType, DeferredItem<Item>>> registerMoreMetalItems() {
        final EnumMap<TfcmMetal, Map<TfcmMoreItemType, DeferredItem<Item>>> itemsByMetal = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final EnumMap<TfcmMoreItemType, DeferredItem<Item>> itemsByType = new EnumMap<>(TfcmMoreItemType.class);
            for (TfcmMoreItemType type : TfcmMoreItemType.values()) {
                final String id = "metal/" + type.path + "/" + metal.getSerializedName();
                itemsByType.put(type, ITEMS.register(id, () -> new Item(new Item.Properties())));
            }
            itemsByMetal.put(metal, Collections.unmodifiableMap(itemsByType));
        }
        return Collections.unmodifiableMap(itemsByMetal);
    }

    private static Map<TfcmOre, Map<TfcmOreWashingItemType, DeferredItem<Item>>> registerOreWashingOreItems() {
        final EnumMap<TfcmOre, Map<TfcmOreWashingItemType, DeferredItem<Item>>> itemsByOre = new EnumMap<>(TfcmOre.class);
        for (TfcmOre ore : TfcmOre.VALUES) {
            if (!ore.isGraded()) {
                continue;
            }
            final String oreName = ore.oreWashingSerializedName();
            final EnumMap<TfcmOreWashingItemType, DeferredItem<Item>> itemsByType = new EnumMap<>(TfcmOreWashingItemType.class);
            for (TfcmOreWashingItemType type : TfcmOreWashingItemType.values()) {
                final String id = "metal/" + type.path + "/" + oreName;
                itemsByType.put(type, ITEMS.register(id, () -> new Item(new Item.Properties())));
            }
            itemsByOre.put(ore, Collections.unmodifiableMap(itemsByType));
        }
        return Collections.unmodifiableMap(itemsByOre);
    }

    private static Map<TfcmOre, DeferredItem<Item>> registerOreItems() {
        final EnumMap<TfcmOre, DeferredItem<Item>> items = new EnumMap<>(TfcmOre.class);
        for (TfcmOre ore : TfcmOre.VALUES) {
            if (ore.isGraded()) {
                continue;
            }
            items.put(ore, ITEMS.register("ore/" + ore.getSerializedName(), () -> new Item(new Item.Properties())));
        }
        return Collections.unmodifiableMap(items);
    }

    private static Map<TfcmOre, Map<Ore.Grade, DeferredItem<Item>>> registerGradedOreItems() {
        final EnumMap<TfcmOre, Map<Ore.Grade, DeferredItem<Item>>> items = new EnumMap<>(TfcmOre.class);
        for (TfcmOre ore : TfcmOre.VALUES) {
            if (!ore.isGraded()) {
                continue;
            }
            final EnumMap<Ore.Grade, DeferredItem<Item>> grades = new EnumMap<>(Ore.Grade.class);
            for (Ore.Grade grade : Ore.Grade.values()) {
                final String gradeName = grade.name().toLowerCase(Locale.ROOT);
                final String id = "ore/" + gradeName + "_" + ore.getSerializedName();
                grades.put(grade, ITEMS.register(id, () -> new Item(new Item.Properties())));
            }
            items.put(ore, Collections.unmodifiableMap(grades));
        }
        return Collections.unmodifiableMap(items);
    }

    public enum TfcmMoreItemType {
        FOIL("foil"),
        GEAR("gear"),
        HEAVY_SHEET("heavy_sheet"),
        NAIL("nail"),
        RING("ring"),
        RIVET("rivet"),
        SCREW("screw"),
        STAMEN("stamen"),
        WIRE("wire");

        private final String path;

        TfcmMoreItemType(String path) {
            this.path = path;
        }
    }

    public enum TfcmOreWashingItemType {
        PELLET("pellet"),
        BRIQUET("briquet"),
        CHUNKS("chunks"),
        ROCKY_CHUNKS("rocky_chunks"),
        DIRTY_DUST("dirty_dust"),
        DIRTY_PILE("dirty_pile"),
        POWDER("powder");

        private final String path;

        TfcmOreWashingItemType(String path) {
            this.path = path;
        }
    }
}
