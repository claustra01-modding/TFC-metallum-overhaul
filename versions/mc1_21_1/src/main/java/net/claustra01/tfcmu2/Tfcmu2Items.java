package net.claustra01.tfcmu2;

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

public final class Tfcmu2Items {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Tfcmu2Mod.MOD_ID);
    public static final boolean TFC_MORE_ITEMS_LOADED = ModList.get().isLoaded(Tfcmu2Mod.TFC_MORE_ITEMS_MOD_ID);
    public static final boolean TFC_ORE_WASHING_LOADED = ModList.get().isLoaded(Tfcmu2Mod.TFC_ORE_WASHING_MOD_ID);
    public static final boolean TFC_METAL_TOOLS_LOADED = ModList.get().isLoaded(Tfcmu2Mod.TFC_METAL_TOOLS_MOD_ID);
    public static final boolean TFC_HOT_OR_NOT_LOADED = ModList.get().isLoaded(Tfcmu2Mod.TFC_HOT_OR_NOT_MOD_ID);
    public static final Map<Tfcmu2Metal, DeferredItem<Item>> METAL_INGOTS = registerMetalItems("ingot", Metal.ItemType.INGOT);
    public static final DeferredItem<Item> HIGH_CARBON_TUNGSTEN_STEEL_INGOT = ITEMS.register("metal/ingot/high_carbon_tungsten_steel", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CUT_QUARTZ = ITEMS.register("gem/cut_quartz", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FLUORITE_POWDER = ITEMS.register("powder/fluorite", () -> new Item(new Item.Properties()));
    public static final Map<Tfcmu2Metal, DeferredItem<Item>> METAL_DOUBLE_INGOTS = registerMetalItems("double_ingot", Metal.ItemType.DOUBLE_INGOT);
    public static final Map<Tfcmu2Metal, DeferredItem<Item>> METAL_SHEETS = registerMetalItems("sheet", Metal.ItemType.SHEET);
    public static final Map<Tfcmu2Metal, DeferredItem<Item>> METAL_DOUBLE_SHEETS = registerMetalItems("double_sheet", Metal.ItemType.DOUBLE_SHEET);
    public static final Map<Tfcmu2Metal, DeferredItem<Item>> METAL_RODS = registerMetalItems("rod", Metal.ItemType.ROD);
    public static final Map<Tfcmu2Metal, Map<Metal.ItemType, DeferredItem<Item>>> METAL_TOOL_ITEMS = registerMetalToolItems();
    public static final Map<Tfcmu2Metal, DeferredItem<Item>> METAL_TOOL_CROSSGUARDS = registerCompatItems("crossguard", false);
    public static final Map<Tfcmu2Metal, DeferredItem<Item>> METAL_TOOL_POMMELS = registerCompatItems("pommel", false);
    public static final Map<Tfcmu2Metal, DeferredItem<Item>> HOT_OR_NOT_TONG_PARTS = registerCompatItems("tong_part", false);
    public static final Map<Tfcmu2Metal, DeferredItem<Item>> HOT_OR_NOT_TONGS = registerCompatItems("tongs", true);
    public static final Map<Tfcmu2Metal, Map<Tfcmu2MoreItemType, DeferredItem<Item>>> MORE_METAL_ITEMS = TFC_MORE_ITEMS_LOADED
        ? registerMoreMetalItems()
        : Collections.emptyMap();
    public static final Map<Tfcmu2Ore, Map<Tfcmu2OreWashingItemType, DeferredItem<Item>>> ORE_WASHING_ORE_ITEMS = TFC_ORE_WASHING_LOADED
        ? registerOreWashingOreItems()
        : Collections.emptyMap();
    public static final Map<Tfcmu2Metal, DeferredItem<?>> METAL_BLOCK_ITEMS = Tfcmu2Blocks.registerMetalBlockItems(ITEMS);
    public static final Map<Tfcmu2Metal, DeferredItem<?>> METAL_BLOCK_SLAB_ITEMS = Tfcmu2Blocks.registerMetalSlabBlockItems(ITEMS);
    public static final Map<Tfcmu2Metal, DeferredItem<?>> METAL_BLOCK_STAIRS_ITEMS = Tfcmu2Blocks.registerMetalStairsBlockItems(ITEMS);
    public static final Map<Tfcmu2Ore, DeferredItem<Item>> ORES = registerOreItems();
    public static final Map<Tfcmu2Ore, Map<Ore.Grade, DeferredItem<Item>>> GRADED_ORES = registerGradedOreItems();
    public static final Map<Rock, Map<Tfcmu2Ore, DeferredItem<?>>> ORE_BLOCK_ITEMS = Tfcmu2Blocks.registerOreBlockItems(ITEMS);
    public static final Map<Rock, Map<Tfcmu2Ore, Map<Ore.Grade, DeferredItem<?>>>> GRADED_ORE_BLOCK_ITEMS = Tfcmu2Blocks.registerGradedOreBlockItems(ITEMS);
    public static final Map<Tfcmu2VanillaStone, Map<Tfcmu2Ore, Map<Ore.Grade, DeferredItem<?>>>> VANILLA_GRADED_ORE_BLOCK_ITEMS = Tfcmu2Blocks.registerVanillaGradedOreBlockItems(ITEMS);
    public static final Map<Tfcmu2VanillaStone, Map<String, DeferredItem<?>>> COMPAT_VANILLA_ORE_BLOCK_ITEMS = Tfcmu2Blocks.registerCompatVanillaOreBlockItems(ITEMS);
    public static final Map<Tfcmu2Ore, DeferredItem<?>> SMALL_ORE_BLOCK_ITEMS = Tfcmu2Blocks.registerSmallOreBlockItems(ITEMS);

    private Tfcmu2Items() {
    }

    private static Map<Tfcmu2Metal, DeferredItem<Item>> registerMetalItems(String itemTypePath, Metal.ItemType itemType) {
        final EnumMap<Tfcmu2Metal, DeferredItem<Item>> items = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            items.put(metal, ITEMS.register("metal/" + itemTypePath + "/" + metal.getSerializedName(), () -> itemType.create(metal)));
        }
        return Collections.unmodifiableMap(items);
    }

    private static Map<Tfcmu2Metal, Map<Metal.ItemType, DeferredItem<Item>>> registerMetalToolItems() {
        final EnumMap<Tfcmu2Metal, Map<Metal.ItemType, DeferredItem<Item>>> itemsByMetal = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
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

    private static Map<Tfcmu2Metal, DeferredItem<Item>> registerCompatItems(String form, boolean tongs) {
        final EnumMap<Tfcmu2Metal, DeferredItem<Item>> items = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            if (!metal.hasTools()) {
                continue;
            }
            final String id = "metal/" + form + "/" + metal.getSerializedName();
            items.put(metal, ITEMS.register(id, () -> tongs ? createHotOrNotTongs(metal) : new Item(new Item.Properties().rarity(metal.rarity()))));
        }
        return Collections.unmodifiableMap(items);
    }

    private static Item createHotOrNotTongs(Tfcmu2Metal metal) {
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

    private static Map<Tfcmu2Metal, Map<Tfcmu2MoreItemType, DeferredItem<Item>>> registerMoreMetalItems() {
        final EnumMap<Tfcmu2Metal, Map<Tfcmu2MoreItemType, DeferredItem<Item>>> itemsByMetal = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            final EnumMap<Tfcmu2MoreItemType, DeferredItem<Item>> itemsByType = new EnumMap<>(Tfcmu2MoreItemType.class);
            for (Tfcmu2MoreItemType type : Tfcmu2MoreItemType.values()) {
                final String id = "metal/" + type.path + "/" + metal.getSerializedName();
                itemsByType.put(type, ITEMS.register(id, () -> new Item(new Item.Properties())));
            }
            itemsByMetal.put(metal, Collections.unmodifiableMap(itemsByType));
        }
        return Collections.unmodifiableMap(itemsByMetal);
    }

    private static Map<Tfcmu2Ore, Map<Tfcmu2OreWashingItemType, DeferredItem<Item>>> registerOreWashingOreItems() {
        final EnumMap<Tfcmu2Ore, Map<Tfcmu2OreWashingItemType, DeferredItem<Item>>> itemsByOre = new EnumMap<>(Tfcmu2Ore.class);
        for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
            if (!ore.isGraded()) {
                continue;
            }
            final String oreName = ore.oreWashingSerializedName();
            final EnumMap<Tfcmu2OreWashingItemType, DeferredItem<Item>> itemsByType = new EnumMap<>(Tfcmu2OreWashingItemType.class);
            for (Tfcmu2OreWashingItemType type : Tfcmu2OreWashingItemType.values()) {
                final String id = "metal/" + type.path + "/" + oreName;
                itemsByType.put(type, ITEMS.register(id, () -> new Item(new Item.Properties())));
            }
            itemsByOre.put(ore, Collections.unmodifiableMap(itemsByType));
        }
        return Collections.unmodifiableMap(itemsByOre);
    }

    private static Map<Tfcmu2Ore, DeferredItem<Item>> registerOreItems() {
        final EnumMap<Tfcmu2Ore, DeferredItem<Item>> items = new EnumMap<>(Tfcmu2Ore.class);
        for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
            if (ore.isGraded()) {
                continue;
            }
            items.put(ore, ITEMS.register("ore/" + ore.getSerializedName(), () -> new Item(new Item.Properties())));
        }
        return Collections.unmodifiableMap(items);
    }

    private static Map<Tfcmu2Ore, Map<Ore.Grade, DeferredItem<Item>>> registerGradedOreItems() {
        final EnumMap<Tfcmu2Ore, Map<Ore.Grade, DeferredItem<Item>>> items = new EnumMap<>(Tfcmu2Ore.class);
        for (Tfcmu2Ore ore : Tfcmu2Ore.VALUES) {
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

    public enum Tfcmu2MoreItemType {
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

        Tfcmu2MoreItemType(String path) {
            this.path = path;
        }
    }

    public enum Tfcmu2OreWashingItemType {
        PELLET("pellet"),
        BRIQUET("briquet"),
        CHUNKS("chunks"),
        ROCKY_CHUNKS("rocky_chunks"),
        DIRTY_DUST("dirty_dust"),
        DIRTY_PILE("dirty_pile"),
        POWDER("powder");

        private final String path;

        Tfcmu2OreWashingItemType(String path) {
            this.path = path;
        }
    }
}
