from __future__ import annotations

import sys
import zipfile
from pathlib import Path

from PIL import UnidentifiedImageError


ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(Path(__file__).resolve().parent))

from color_transfer import flip_horizontal, load_png, load_zip_png, save_png, transfer_palette


ASSETS = ROOT / "shared/src/main/resources/assets"
MC1_21_1_ASSETS = ROOT / "versions/mc1_21_1/src/main/resources/assets"
TFC_JAR = next(
    (Path.home() / ".gradle/caches/modules-2/files-2.1/maven.modrinth/terrafirmacraft/4.2.5").glob(
        "*/terrafirmacraft-4.2.5.jar"
    )
)
MORE_ITEMS_JAR = ROOT / ".tmp/tfc_more_items/TFC-items-1.21.1-neoforge-1.2.1.jar"
IE_JAR = ROOT / ".tmp/immersive_engineering/ImmersiveEngineering-1.21.1-12.4.2-194.jar"
LEGACY_METALLUM_JAR = ROOT / ".tmp/tfc_metallum_legacy/TFC-Metallum-MC1.12.2-1.4.2.jar"
METALLUM_U_JAR = ROOT / ".tmp/tfc_metallum_u/TFC Metallum 1.18.2-1.0.8.jar"
PNEUMATICCRAFT_JAR = ROOT / ".tmp/pneumaticcraft/pneumaticcraft-repressurized-8.2.20+mc1.21.1.jar"
MEKANISM_JAR = ROOT / ".tmp/mekanism/Mekanism-1.21.1-10.7.19.85.jar"
MEKANISM_EXTRAS_JAR = ROOT / ".tmp/mekanism_extras/mekanism_extras-1.21.1-1.4.0.jar"
THERMAL_FOUNDATION_JAR = ROOT / ".tmp/thermal_foundation/thermal_foundation-1.20.1-11.0.6.70.jar"
TFC_METAL_TOOLS_JAR = ROOT / ".tmp/optional_compat/tfc_metal_tools.jar"
TFC_HOT_OR_NOT_JAR = ROOT / ".tmp/optional_compat/tfc_hot_or_not.jar"
MINECRAFT_JAR = Path.home() / ".gradle/caches/neoformruntime/artifacts/minecraft_1.21.1_client.jar"
LARGE_SURFACE_RANK_SCALE = 0.55

IE_METALS = {"aluminum", "constantan", "electrum", "lead", "uranium"}
IRON_SPELLS_METALS = {"mithril", "arcane"}
METALLUM_U_METALS = {
    "antimony",
    "high_carbon_tungsten_steel",
    "iridium",
    "osmiridium",
    "platinum",
    "solder",
    "titanium",
    "tungsten",
    "tungsten_steel",
}

ORIGINAL_MOD_SOURCES = {
    "compressed_iron": (
        PNEUMATICCRAFT_JAR,
        "assets/pneumaticcraft/textures/item/ingot_iron_compressed.png",
    ),
    "naquadah": (
        MEKANISM_EXTRAS_JAR,
        "assets/mekanism_extras/textures/item/ingot_naquadah.png",
    ),
    "osmium": (MEKANISM_JAR, "assets/mekanism/textures/item/ingot_osmium.png"),
    "refined_glowstone": (
        MEKANISM_JAR,
        "assets/mekanism/textures/item/ingot_refined_glowstone.png",
    ),
    "refined_obsidian": (
        MEKANISM_JAR,
        "assets/mekanism/textures/item/ingot_refined_obsidian.png",
    ),
    "invar": (THERMAL_FOUNDATION_JAR, "assets/thermal/textures/item/invar_ingot.png"),
}

FORM_BASES = {
    "ingot": (TFC_JAR, "assets/tfc/textures/item/metal/ingot/wrought_iron.png"),
    "double_ingot": (TFC_JAR, "assets/tfc/textures/item/metal/double_ingot/wrought_iron.png"),
    "sheet": (TFC_JAR, "assets/tfc/textures/item/metal/sheet/wrought_iron.png"),
    "double_sheet": (TFC_JAR, "assets/tfc/textures/item/metal/double_sheet/wrought_iron.png"),
    "rod": (TFC_JAR, "assets/tfc/textures/item/metal/rod/wrought_iron.png"),
    "foil": (MORE_ITEMS_JAR, "assets/tfc_items/textures/item/wrought_iron_foil.png"),
    "gear": (MORE_ITEMS_JAR, "assets/tfc_items/textures/item/wrought_iron_gear.png"),
    "heavy_sheet": (MORE_ITEMS_JAR, "assets/tfc_items/textures/item/wrought_iron_heavy_sheet.png"),
    "nail": (MORE_ITEMS_JAR, "assets/tfc_items/textures/item/wrought_iron_nail.png"),
    "ring": (MORE_ITEMS_JAR, "assets/tfc_items/textures/item/wrought_iron_ring.png"),
    "rivet": (MORE_ITEMS_JAR, "assets/tfc_items/textures/item/wrought_iron_rivet.png"),
    "screw": (MORE_ITEMS_JAR, "assets/tfc_items/textures/item/wrought_iron_screw.png"),
    "stamen": (MORE_ITEMS_JAR, "assets/tfc_items/textures/item/wrought_iron_stamen.png"),
    "wire": (MORE_ITEMS_JAR, "assets/tfc_items/textures/item/wrought_iron_wire.png"),
}

TOOL_METAL_PROFILES = {
    "invar": ("wrought_iron", "steel"),
    "titanium": ("steel", "wrought_iron"),
    "tungsten_steel": ("red_steel", "blue_steel"),
}

ARMOR_METAL_BASES = {
    "invar": "wrought_iron",
    "titanium": "black_steel",
    "tungsten_steel": "steel",
}

TOOL_FORMS = {
    "tuyere",
    "fish_hook",
    "fishing_rod",
    "unfinished_lamp",
    "pickaxe",
    "pickaxe_head",
    "propick",
    "propick_head",
    "axe",
    "axe_head",
    "shovel",
    "shovel_head",
    "hoe",
    "hoe_head",
    "chisel",
    "chisel_head",
    "hammer",
    "hammer_head",
    "saw",
    "saw_blade",
    "javelin",
    "javelin_head",
    "sword",
    "sword_blade",
    "mace",
    "mace_head",
    "knife",
    "knife_blade",
    "scythe",
    "scythe_blade",
    "shears",
    "unfinished_helmet",
    "helmet",
    "unfinished_chestplate",
    "chestplate",
    "unfinished_greaves",
    "greaves",
    "unfinished_boots",
    "boots",
    "horse_armor",
    "shield",
}

FIXED_COLOR_FORMS = {
    "fishing_rod",
    "pickaxe",
    "propick",
    "axe",
    "shovel",
    "hoe",
    "chisel",
    "hammer",
    "saw",
    "javelin",
    "sword",
    "mace",
    "knife",
    "scythe",
    "shears",
}

HORIZONTALLY_FLIPPED_TOOL_FORMS = {
    "knife",
    "knife_blade",
    "javelin",
    "javelin_head",
}

ARMOR_FORMS = {
    "unfinished_helmet",
    "helmet",
    "unfinished_chestplate",
    "chestplate",
    "unfinished_greaves",
    "greaves",
    "unfinished_boots",
    "boots",
    "horse_armor",
    "shield",
}

TFC_FIXED_COLOR_METALS = (
    "copper",
    "bismuth_bronze",
    "black_bronze",
    "bronze",
    "wrought_iron",
    "steel",
    "black_steel",
    "blue_steel",
    "red_steel",
)

def transfer_tool_palette(
    archive: zipfile.ZipFile,
    archive_path: Path,
    member: str,
    base_metal: str,
    comparison_metal: str,
    base: list[tuple[int, int, int, int]],
    source: list[tuple[int, int, int, int]],
) -> list[tuple[int, int, int, int]]:
    mapped = transfer_palette(base, source)
    candidate = member.replace(base_metal, comparison_metal)
    if candidate not in archive.namelist():
        return mapped

    try:
        _, comparison = load_zip_png(archive_path, candidate)
    except UnidentifiedImageError:
        return mapped
    if len(comparison) != len(base):
        return mapped

    return [
        pixel if comparison[index] == pixel else mapped[index]
        for index, pixel in enumerate(base)
    ]


def transfer_horse_armor_palette(
    archive: zipfile.ZipFile,
    member: str,
    base_metal: str,
    base: list[tuple[int, int, int, int]],
    source: list[tuple[int, int, int, int]],
) -> list[tuple[int, int, int, int]]:
    mapped = transfer_palette(base, source)
    comparisons = []
    for metal in TFC_FIXED_COLOR_METALS:
        candidate = member.replace(base_metal, metal)
        if candidate == member or candidate not in archive.namelist():
            continue
        try:
            _, candidate_pixels = load_zip_png(TFC_JAR, candidate)
        except UnidentifiedImageError:
            continue
        if len(candidate_pixels) == len(base):
            comparisons.append(candidate_pixels)

    if not comparisons:
        return mapped
    return [
        pixel if all(other[index] == pixel for other in comparisons) else mapped[index]
        for index, pixel in enumerate(base)
    ]


def source_pixels(metal: str) -> list[tuple[int, int, int, int]]:
    if metal in ORIGINAL_MOD_SOURCES:
        return load_zip_png(*ORIGINAL_MOD_SOURCES[metal])[1]
    if metal in IE_METALS:
        return load_zip_png(
            IE_JAR, f"assets/immersiveengineering/textures/item/metal_ingot_{metal}.png"
        )[1]
    if metal in IRON_SPELLS_METALS:
        return load_png(ROOT / f".tmp/irons_spells/{metal}_ingot.png")[1]
    if metal == "cobalt":
        return load_png(ROOT / ".tmp/tfc_metallum_u/source_cobalt_ingot.png")[1]
    if metal == "lithium":
        return load_zip_png(
            LEGACY_METALLUM_JAR, "assets/tfc/textures/items/metal/ingot/lithium.png"
        )[1]
    if metal in METALLUM_U_METALS:
        return load_zip_png(
            METALLUM_U_JAR, f"assets/tfc_metallum/textures/item/metal/ingot/{metal}.png"
        )[1]
    if metal == "netherite":
        return load_zip_png(
            MINECRAFT_JAR, "assets/minecraft/textures/item/netherite_ingot.png"
        )[1]
    raise ValueError(f"No source texture configured for metal: {metal}")


def main() -> None:
    ingot_dir = ASSETS / "tfcm/textures/item/metal/ingot"
    metals = sorted(path.stem for path in ingot_dir.glob("*.png"))
    sources = {metal: source_pixels(metal) for metal in metals}
    generated = 0

    for form, (archive, member) in FORM_BASES.items():
        size, base = load_zip_png(archive, member)
        target_dir = ASSETS / f"tfcm/textures/item/metal/{form}"
        for metal in metals:
            target = target_dir / f"{metal}.png"
            if target.exists():
                save_png(target, size, transfer_palette(base, sources[metal]))
                generated += 1

    for namespace, form, member, rank_scale in (
        (
            "tfcm",
            "block",
            "assets/tfc/textures/block/metal/block/wrought_iron.png",
            LARGE_SURFACE_RANK_SCALE,
        ),
        (
            "tfc",
            "smooth",
            "assets/tfc/textures/block/metal/smooth/wrought_iron.png",
            LARGE_SURFACE_RANK_SCALE,
        ),
    ):
        size, base = load_zip_png(TFC_JAR, member)
        for metal in metals:
            target = ASSETS / f"{namespace}/textures/block/metal/{form}/{metal}.png"
            if target.exists():
                save_png(
                    target,
                    size,
                    transfer_palette(base, sources[metal], rank_scale=rank_scale),
                )
                generated += 1

    with zipfile.ZipFile(TFC_JAR) as archive:
        members = set(archive.namelist())
        for metal, (base_metal, comparison_metal) in TOOL_METAL_PROFILES.items():
            for form in TOOL_FORMS:
                form_base_metal = ARMOR_METAL_BASES[metal] if form in ARMOR_FORMS else base_metal
                prefix = f"assets/tfc/textures/item/metal/{form}/{form_base_metal}"
                target_dir = ASSETS / f"tfcm/textures/item/metal/{form}"
                for stale in target_dir.glob(f"{metal}*.png"):
                    stale.unlink()
                for member in sorted(name for name in members if name.startswith(prefix) and name.endswith(".png")):
                    suffix = member.removeprefix(prefix)
                    try:
                        size, base = load_zip_png(TFC_JAR, member)
                    except UnidentifiedImageError:
                        fallback = member.replace(form_base_metal, "wrought_iron")
                        size, base = load_zip_png(TFC_JAR, fallback)
                    target = target_dir / f"{metal}{suffix}"
                    if form == "horse_armor":
                        pixels = transfer_horse_armor_palette(
                            archive, member, form_base_metal, base, sources[metal]
                        )
                    elif form in FIXED_COLOR_FORMS:
                        pixels = transfer_tool_palette(
                            archive,
                            TFC_JAR,
                            member,
                            form_base_metal,
                            comparison_metal,
                            base,
                            sources[metal],
                        )
                    else:
                        pixels = transfer_palette(base, sources[metal])
                    if form in HORIZONTALLY_FLIPPED_TOOL_FORMS:
                        pixels = flip_horizontal(size, pixels)
                    save_png(target, size, pixels)
                    generated += 1

            for layer in (1, 2):
                armor_base_metal = ARMOR_METAL_BASES[metal]
                member = f"assets/tfc/textures/models/armor/{armor_base_metal}_layer_{layer}.png"
                size, base = load_zip_png(TFC_JAR, member)
                target = ASSETS / f"tfcm/textures/models/armor/{metal}_layer_{layer}.png"
                save_png(target, size, transfer_palette(base, sources[metal]))
                generated += 1

            member = f"assets/tfc/textures/entity/projectiles/{base_metal}_javelin.png"
            size, base = load_zip_png(TFC_JAR, member)
            target = ASSETS / f"tfcm/textures/entity/projectiles/{metal}_javelin.png"
            save_png(
                target,
                size,
                transfer_tool_palette(
                    archive,
                    TFC_JAR,
                    member,
                    base_metal,
                    comparison_metal,
                    base,
                    sources[metal],
                ),
            )
            generated += 1

    compat_forms = {
        "crossguard": (TFC_METAL_TOOLS_JAR, "tfc_metal_tools", "{metal}_crossguard"),
        "pommel": (TFC_METAL_TOOLS_JAR, "tfc_metal_tools", "{metal}_pommel"),
        "tongs": (TFC_HOT_OR_NOT_JAR, "tfchotornot", "tongs/{metal}"),
        "tong_part": (TFC_HOT_OR_NOT_JAR, "tfchotornot", "tong_part/{metal}"),
    }
    for form, (archive_path, namespace, pattern) in compat_forms.items():
        with zipfile.ZipFile(archive_path) as archive:
            for metal, (base_metal, comparison_metal) in TOOL_METAL_PROFILES.items():
                texture = pattern.format(metal=base_metal)
                member = f"assets/{namespace}/textures/item/{texture}.png"
                size, base = load_zip_png(archive_path, member)
                if form in {"tongs", "tong_part"}:
                    pixels = transfer_tool_palette(
                        archive,
                        archive_path,
                        member,
                        base_metal,
                        comparison_metal,
                        base,
                        sources[metal],
                    )
                else:
                    pixels = transfer_palette(base, sources[metal])
                target_assets = ASSETS if form in {"crossguard", "pommel"} else MC1_21_1_ASSETS
                target = target_assets / f"tfcm/textures/item/metal/{form}/{metal}.png"
                save_png(target, size, pixels)
                generated += 1

    print(f"Regenerated {generated} metal textures for {len(metals)} metals.")


if __name__ == "__main__":
    main()
