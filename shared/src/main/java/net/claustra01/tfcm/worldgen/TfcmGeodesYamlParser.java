package net.claustra01.tfcm.worldgen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mojang.logging.LogUtils;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import org.slf4j.Logger;

/** Parses the geodes section of TFCM's purpose-built worldgen YAML format. */
public final class TfcmGeodesYamlParser {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final List<String> WEIGHTED_LIST_KEYS = List.of("inner", "filling", "inner_placements");

    private TfcmGeodesYamlParser() {
    }

    public record WeightedBlockDefinition(String block, int weight) {
    }

    public record GeodeDefinition(
        ResourceLocation id,
        ResourceLocation type,
        String outer,
        String middle,
        List<WeightedBlockDefinition> inner,
        List<WeightedBlockDefinition> filling,
        List<WeightedBlockDefinition> innerPlacements,
        int minY,
        int maxY,
        int rarity
    ) {
        public Holder<PlacedFeature> buildPlacedFeature() {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            final Feature<TfcmGeodeConfig> feature = (Feature) BuiltInRegistries.FEATURE.get(type);
            if (feature == null || !BuiltInRegistries.FEATURE.containsKey(type)) {
                throw new IllegalArgumentException("Missing geode feature type " + type + " referenced by " + id);
            }

            final TfcmGeodeConfig config = new TfcmGeodeConfig(
                resolveBlockState(outer, "outer"),
                resolveBlockState(middle, "middle"),
                buildWeightedStates(inner, "inner"),
                buildWeightedStates(filling, "filling"),
                buildWeightedStates(innerPlacements, "inner_placements")
            );
            final ConfiguredFeature<TfcmGeodeConfig, Feature<TfcmGeodeConfig>> configured =
                new ConfiguredFeature<>(feature, config);
            final PlacedFeature placed = new PlacedFeature(
                Holder.direct(configured),
                List.of(
                    RarityFilter.onAverageOnceEvery(rarity),
                    InSquarePlacement.spread(),
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY))
                )
            );
            return Holder.direct(placed);
        }

        public List<String> referencedBlocks() {
            final List<String> blocks = new ArrayList<>();
            blocks.add(outer);
            blocks.add(middle);
            inner.forEach(value -> blocks.add(value.block()));
            filling.forEach(value -> blocks.add(value.block()));
            innerPlacements.forEach(value -> blocks.add(value.block()));
            return List.copyOf(blocks);
        }

        private BlockState resolveBlockState(String value, String field) {
            final ResourceLocation blockId = ResourceLocation.tryParse(value);
            if (blockId == null || !BuiltInRegistries.BLOCK.containsKey(blockId)) {
                throw new IllegalArgumentException("Missing block '" + value + "' in " + field + " for " + id);
            }
            final Block block = BuiltInRegistries.BLOCK.get(blockId);
            if (block == Blocks.AIR && !"minecraft:air".equals(value)) {
                throw new IllegalArgumentException("Missing block '" + value + "' in " + field + " for " + id);
            }
            return block.defaultBlockState();
        }

        private SimpleWeightedRandomList<BlockState> buildWeightedStates(List<WeightedBlockDefinition> definitions, String field) {
            final SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
            for (WeightedBlockDefinition definition : definitions) {
                builder.add(resolveBlockState(definition.block(), field), definition.weight());
            }
            return builder.build();
        }
    }

    public record ParseResult(boolean sectionPresent, int declaredEntries, List<GeodeDefinition> definitions) {
        public boolean explicitlyEmpty() {
            return sectionPresent && declaredEntries == 0;
        }
    }

    public static ParseResult parseGeodes(Path yamlPath) throws IOException {
        if (!Files.exists(yamlPath)) {
            return new ParseResult(false, 0, List.of());
        }

        final List<String> lines = Files.readAllLines(yamlPath, StandardCharsets.UTF_8);
        int i = 0;
        while (i < lines.size()) {
            final String line = stripComment(lines.get(i));
            final String trimmed = line.trim();
            if (countIndent(line) == 0 && ("geodes:".equals(trimmed) || "geodes: []".equals(trimmed))) {
                if ("geodes: []".equals(trimmed)) {
                    return new ParseResult(true, 0, List.of());
                }
                break;
            }
            i++;
        }
        if (i >= lines.size()) {
            return new ParseResult(false, 0, List.of());
        }
        i++;

        int declaredEntries = 0;
        final List<GeodeDefinition> definitions = new ArrayList<>();
        while (i < lines.size()) {
            final String line = stripComment(lines.get(i));
            if (line.trim().isEmpty()) {
                i++;
                continue;
            }
            final int indent = countIndent(line);
            final String trimmed = line.trim();
            if (indent == 0) {
                break;
            }
            if (!trimmed.startsWith("- ")) {
                LOGGER.warn("Skipping unexpected geode line in {}: {}", yamlPath, trimmed);
                i++;
                continue;
            }

            declaredEntries++;
            final String key = parseListKey(trimmed);
            final ResourceLocation id = key == null ? null : ResourceLocation.tryParse(key);
            if (id == null) {
                LOGGER.warn("Skipping malformed geode entry line in {}: {}", yamlPath, trimmed);
                i++;
                continue;
            }
            i++;

            final LinkedHashMap<String, Object> properties = new LinkedHashMap<>();
            while (i < lines.size()) {
                final String raw = stripComment(lines.get(i));
                if (raw.trim().isEmpty()) {
                    i++;
                    continue;
                }
                final int propertyIndent = countIndent(raw);
                final String propertyText = raw.trim();
                if (propertyIndent == 0 || propertyIndent <= indent && propertyText.startsWith("- ")) {
                    break;
                }

                final ParsedKeyValue keyValue = parseKeyValue(propertyText);
                if (keyValue == null) {
                    LOGGER.warn("Skipping malformed line for geode {} in {}: {}", id, yamlPath, propertyText);
                    i++;
                    continue;
                }
                if (WEIGHTED_LIST_KEYS.contains(keyValue.key()) && keyValue.value().isEmpty()) {
                    i++;
                    final List<LinkedHashMap<String, String>> values = new ArrayList<>();
                    while (i < lines.size()) {
                        final String itemLine = stripComment(lines.get(i));
                        if (itemLine.trim().isEmpty()) {
                            i++;
                            continue;
                        }
                        final int itemIndent = countIndent(itemLine);
                        final String itemText = itemLine.trim();
                        if (itemIndent <= propertyIndent) {
                            break;
                        }
                        if (!itemText.startsWith("- ")) {
                            LOGGER.warn("Skipping malformed {} entry for {} in {}: {}", keyValue.key(), id, yamlPath, itemText);
                            i++;
                            continue;
                        }

                        final LinkedHashMap<String, String> valueProperties = new LinkedHashMap<>();
                        final ParsedKeyValue first = parseKeyValue(itemText.substring(2).trim());
                        if (first != null) {
                            valueProperties.put(first.key(), first.value());
                        }
                        i++;
                        while (i < lines.size()) {
                            final String child = stripComment(lines.get(i));
                            if (child.trim().isEmpty()) {
                                i++;
                                continue;
                            }
                            final int childIndent = countIndent(child);
                            final String childText = child.trim();
                            if (childIndent <= propertyIndent || childIndent == itemIndent && childText.startsWith("- ")) {
                                break;
                            }
                            if (childIndent <= itemIndent) {
                                break;
                            }
                            final ParsedKeyValue childKeyValue = parseKeyValue(childText);
                            if (childKeyValue != null) {
                                valueProperties.put(childKeyValue.key(), childKeyValue.value());
                            }
                            i++;
                        }
                        values.add(valueProperties);
                    }
                    properties.put(keyValue.key(), values);
                    continue;
                }

                properties.put(keyValue.key(), keyValue.value());
                i++;
            }

            final GeodeDefinition definition = toDefinition(id, properties);
            if (definition != null) {
                definitions.add(definition);
            }
        }

        return new ParseResult(true, declaredEntries, List.copyOf(definitions));
    }

    private static GeodeDefinition toDefinition(ResourceLocation id, Map<String, Object> properties) {
        try {
            final ResourceLocation type = requireResourceLocation(properties, "type", id);
            if (!"tfcm:quartz_geode".equals(type.toString())) {
                throw new IllegalArgumentException("Unsupported geode feature type '" + type + "' for " + id);
            }
            final String outer = requireString(properties, "outer", id);
            final String middle = requireString(properties, "middle", id);
            final List<WeightedBlockDefinition> inner = parseWeightedBlocks(properties, "inner", id);
            final List<WeightedBlockDefinition> filling = parseWeightedBlocks(properties, "filling", id);
            final List<WeightedBlockDefinition> placements = parseWeightedBlocks(properties, "inner_placements", id);
            final int minY = getInt(properties, id, "ymin", "min_y");
            final int maxY = getInt(properties, id, "ymax", "max_y");
            final int rarity = requireInt(properties, "rarity", id);
            if (minY > maxY) {
                throw new IllegalArgumentException("ymin must be less than or equal to ymax for " + id);
            }
            if (rarity <= 0) {
                throw new IllegalArgumentException("rarity must be greater than 0 for " + id);
            }
            return new GeodeDefinition(id, type, outer, middle, inner, filling, placements, minY, maxY, rarity);
        } catch (Exception e) {
            LOGGER.warn("Failed to build geode definition for {}: {}", id, e.getMessage());
            return null;
        }
    }

    private static List<WeightedBlockDefinition> parseWeightedBlocks(Map<String, Object> properties, String key, ResourceLocation id) {
        final Object value = properties.get(key);
        if (!(value instanceof List<?> entries) || entries.isEmpty()) {
            throw new IllegalArgumentException("Field '" + key + "' must be a non-empty list for " + id);
        }

        final List<WeightedBlockDefinition> definitions = new ArrayList<>(entries.size());
        for (Object entry : entries) {
            if (!(entry instanceof Map<?, ?> map)) {
                throw new IllegalArgumentException("Each '" + key + "' entry must be a mapping for " + id);
            }
            final Object blockValue = map.get("block");
            if (blockValue == null || blockValue.toString().isBlank()) {
                throw new IllegalArgumentException("Missing field '" + key + "[].block' for " + id);
            }
            final Object weightValue = map.get("weight");
            final int weight = weightValue == null || weightValue.toString().isBlank() ? 1 : Integer.parseInt(weightValue.toString());
            if (weight <= 0) {
                throw new IllegalArgumentException("Field '" + key + "[].weight' must be greater than 0 for " + id);
            }
            definitions.add(new WeightedBlockDefinition(blockValue.toString(), weight));
        }
        return List.copyOf(definitions);
    }

    private static String requireString(Map<String, Object> properties, String key, ResourceLocation id) {
        final Object value = properties.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalArgumentException("Missing field '" + key + "' for " + id);
        }
        return value.toString();
    }

    private static ResourceLocation requireResourceLocation(Map<String, Object> properties, String key, ResourceLocation id) {
        final String value = requireString(properties, key, id);
        final ResourceLocation result = ResourceLocation.tryParse(value);
        if (result == null) {
            throw new IllegalArgumentException("Invalid resource location '" + value + "' for " + id);
        }
        return result;
    }

    private static int requireInt(Map<String, Object> properties, String key, ResourceLocation id) {
        return Integer.parseInt(requireString(properties, key, id));
    }

    private static int getInt(Map<String, Object> properties, ResourceLocation id, String primary, String fallback) {
        if (properties.containsKey(primary)) {
            return Integer.parseInt(properties.get(primary).toString());
        }
        if (properties.containsKey(fallback)) {
            return Integer.parseInt(properties.get(fallback).toString());
        }
        throw new IllegalArgumentException("Missing int field '" + primary + "' for " + id);
    }

    private static String stripComment(String line) {
        final int index = line.indexOf('#');
        return index < 0 ? line : line.substring(0, index);
    }

    private static int countIndent(String line) {
        int index = 0;
        while (index < line.length() && line.charAt(index) == ' ') {
            index++;
        }
        return index;
    }

    private static String parseListKey(String value) {
        if (!value.startsWith("- ") || !value.endsWith(":")) {
            return null;
        }
        final String key = value.substring(2, value.length() - 1).trim();
        return key.isEmpty() ? null : key;
    }

    private static ParsedKeyValue parseKeyValue(String value) {
        final int index = value.indexOf(':');
        if (index <= 0) {
            return null;
        }
        return new ParsedKeyValue(value.substring(0, index).trim(), value.substring(index + 1).trim());
    }

    private record ParsedKeyValue(String key, String value) {
    }
}
