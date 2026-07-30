package net.claustra01.tfcm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides the loader-independent content selection used while registries are built.
 * Unknown config names intentionally have no effect.
 */
public final class TfcmEnableContent {
    private static final Pattern CONTENT_SECTION = Pattern.compile(
        "(?ms)(?:^|\\n)\\s*\\[content\\]\\s*(.*?)(?=\\n\\s*\\[|\\z)");
    private static final Pattern ARRAY_VALUE = Pattern.compile(
        "(?ms)^\\s*%s\\s*=\\s*\\[(.*?)\\]");
    private static final Pattern QUOTED_VALUE = Pattern.compile("\\\"([^\\\"]*)\\\"");

    private static Set<String> disabledMetals = Set.of();
    private static Set<String> disabledOres = Set.of();

    private TfcmEnableContent() {
    }

    public static void configure(Collection<?> disabledMetalNames, Collection<?> disabledOreNames) {
        disabledMetals = normalize(disabledMetalNames);
        disabledOres = normalize(disabledOreNames);
    }

    /**
     * Common config is loaded after registry events, so registry filtering needs
     * the already-existing TOML file before DeferredRegister entries are created.
     */
    public static void loadEarly(Path configFile) {
        if (!Files.isRegularFile(configFile)) {
            configure(Set.of(), Set.of());
            return;
        }
        try {
            final String toml = Files.readString(configFile, StandardCharsets.UTF_8);
            final Matcher sectionMatcher = CONTENT_SECTION.matcher(toml);
            if (!sectionMatcher.find()) {
                configure(Set.of(), Set.of());
                return;
            }
            final String content = sectionMatcher.group(1);
            configure(readArray(content, "disabledMetals"), readArray(content, "disabledOres"));
        } catch (Exception ignored) {
            // A malformed or unreadable optional selection falls back to all content.
            configure(Set.of(), Set.of());
        }
    }

    public static boolean isMetalEnabled(TfcmMetal metal) {
        return isEnabled(metal.getSerializedName(), disabledMetals);
    }

    public static boolean isOreEnabled(TfcmOre ore) {
        return isEnabled(ore.getSerializedName(), disabledOres);
    }

    public static boolean isOreEnabled(String oreName) {
        return isEnabled(normalizeOreName(oreName), disabledOres);
    }

    /** Filters a tfcm:ore/... resource path used by custom vein YAML. */
    public static boolean isOrePathEnabled(String path) {
        final String prefix = "ore/";
        if (!path.startsWith(prefix)) {
            return true;
        }
        String name = path.substring(prefix.length());
        final int slash = name.indexOf('/');
        if (slash >= 0) {
            name = name.substring(0, slash);
        }
        return isOreEnabled(name);
    }

    private static Set<String> normalize(Collection<?> names) {
        final Set<String> normalized = new HashSet<>();
        if (names != null) {
            for (Object name : names) {
                if (name instanceof String value) {
                    normalized.add(value.trim().toLowerCase(Locale.ROOT));
                }
            }
        }
        return Set.copyOf(normalized);
    }

    private static boolean isEnabled(String name, Set<String> disabledNames) {
        return !disabledNames.contains(name);
    }

    private static Set<String> readArray(String content, String key) {
        final Matcher arrayMatcher = Pattern.compile(ARRAY_VALUE.pattern().formatted(Pattern.quote(key))).matcher(content);
        if (!arrayMatcher.find()) {
            return Set.of();
        }
        final Set<String> values = new HashSet<>();
        final Matcher valueMatcher = QUOTED_VALUE.matcher(arrayMatcher.group(1));
        while (valueMatcher.find()) {
            values.add(valueMatcher.group(1));
        }
        return values;
    }

    private static String normalizeOreName(String name) {
        String normalized = name.toLowerCase(Locale.ROOT);
        if (normalized.startsWith("small_")) {
            normalized = normalized.substring("small_".length());
        }
        for (String grade : new String[] {"poor_", "normal_", "rich_"}) {
            if (normalized.startsWith(grade)) {
                return normalized.substring(grade.length());
            }
        }
        return normalized;
    }
}
