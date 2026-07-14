from __future__ import annotations

import subprocess
import sys
from io import BytesIO
from pathlib import Path

from PIL import Image


ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(Path(__file__).resolve().parent))

from color_transfer import load_png, load_zip_png, restore_reference_highlights, save_png, transfer_palette


ASSETS = ROOT / "shared/src/main/resources/assets"
TFC_JAR = next(
    (Path.home() / ".gradle/caches/modules-2/files-2.1/maven.modrinth/terrafirmacraft/4.2.5").glob(
        "*/terrafirmacraft-4.2.5.jar"
    )
)
MORE_ITEMS_JAR = ROOT / ".tmp/tfc_more_items/TFC-items-1.21.1-neoforge-1.2.1.jar"
IE_JAR = ROOT / ".tmp/immersive_engineering/ImmersiveEngineering-1.21.1-12.4.2-194.jar"
LEGACY_METALLUM_JAR = ROOT / ".tmp/tfc_metallum_legacy/TFC-Metallum-MC1.12.2-1.4.2.jar"
METALLUM_U_JAR = ROOT / ".tmp/tfc_metallum_u/TFC Metallum 1.18.2-1.0.8.jar"
MINECRAFT_JAR = Path.home() / ".gradle/caches/neoformruntime/artifacts/minecraft_1.21.1_client.jar"
REFERENCE_COMMIT = "4b6a2316"

IE_METALS = {"aluminum", "constantan", "electrum", "lead", "uranium"}
IRON_SPELLS_METALS = {"mithril", "arcane"}
METALLUM_U_METALS = {
    "antimony",
    "compressed_iron",
    "high_carbon_tungsten_steel",
    "iridium",
    "osmiridium",
    "osmium",
    "platinum",
    "refined_glowstone",
    "refined_obsidian",
    "solder",
    "titanium",
    "tungsten",
    "tungsten_steel",
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


def git_reference(metal: str) -> list[tuple[int, int, int, int]]:
    relative = f"shared/src/main/resources/assets/tfcmu2/textures/item/metal/ingot/{metal}.png"
    data = subprocess.run(
        ["git", "show", f"{REFERENCE_COMMIT}:{relative}"],
        cwd=ROOT,
        check=True,
        stdout=subprocess.PIPE,
    ).stdout
    with Image.open(BytesIO(data)) as image:
        return list(image.convert("RGBA").get_flattened_data())


def source_pixels(metal: str) -> list[tuple[int, int, int, int]]:
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
    return restore_reference_highlights(git_reference(metal))


def main() -> None:
    ingot_dir = ASSETS / "tfcmu2/textures/item/metal/ingot"
    metals = sorted(path.stem for path in ingot_dir.glob("*.png"))
    sources = {metal: source_pixels(metal) for metal in metals}
    generated = 0

    for form, (archive, member) in FORM_BASES.items():
        size, base = load_zip_png(archive, member)
        target_dir = ASSETS / f"tfcmu2/textures/item/metal/{form}"
        for metal in metals:
            target = target_dir / f"{metal}.png"
            if target.exists():
                save_png(target, size, transfer_palette(base, sources[metal]))
                generated += 1

    for namespace, form, member in (
        ("tfcmu2", "block", "assets/tfc/textures/block/metal/block/wrought_iron.png"),
        ("tfc", "smooth", "assets/tfc/textures/block/metal/smooth/wrought_iron.png"),
    ):
        size, base = load_zip_png(TFC_JAR, member)
        for metal in metals:
            target = ASSETS / f"{namespace}/textures/block/metal/{form}/{metal}.png"
            if target.exists():
                save_png(target, size, transfer_palette(base, sources[metal]))
                generated += 1

    print(f"Regenerated {generated} metal textures for {len(metals)} metals.")


if __name__ == "__main__":
    main()
