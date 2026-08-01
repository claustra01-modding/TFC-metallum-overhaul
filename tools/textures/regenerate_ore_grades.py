from __future__ import annotations

import json
from pathlib import Path

from color_transfer import load_zip_png, save_png, transfer_palette


ROOT = Path(__file__).resolve().parents[2]
ASSETS = ROOT / "shared/src/main/resources/assets/tfcm"
DATA = ROOT / "shared/src/main/resources/data/tfcm"
TFC_JAR = next(
    (Path.home() / ".gradle/caches/modules-2/files-2.1/maven.modrinth/terrafirmacraft/4.2.5").glob(
        "*/terrafirmacraft-4.2.5.jar"
    )
)
METALLUM_U_JAR = ROOT / ".tmp/tfc_metallum_u/TFC Metallum 1.18.2-1.0.8.jar"
METALLUM_LEGACY_JAR = ROOT / ".tmp/tfc_metallum_legacy/TFC-Metallum-MC1.12.2-1.4.2.jar"
ORES = ("uraninite", "spodumene", "thorianite", "magnesite", "zircon")
GRADES = ("poor", "normal", "rich")
ROCKS = (
    "andesite", "basalt", "chalk", "chert", "claystone", "conglomerate", "dacite",
    "diorite", "dolomite", "gabbro", "gneiss", "granite", "limestone", "marble",
    "phyllite", "quartzite", "rhyolite", "schist", "shale", "slate", "tuff",
)


def write_json(path: Path, value: object) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, indent=2) + "\n", encoding="utf-8")


def legacy_item_member(ore: str, grade: str) -> str:
    if grade == "normal":
        return f"assets/tfc/textures/items/ore/{ore}.png"
    return f"assets/tfc/textures/items/ore/{grade}/{ore}.png"


def item_source(ore: str, grade: str):
    if ore == "uraninite":
        return load_zip_png(METALLUM_U_JAR, f"assets/tfc_metallum/textures/item/ore/{grade}_uraninite.png")
    return load_zip_png(METALLUM_LEGACY_JAR, legacy_item_member(ore, grade))


def block_source(ore: str, grade: str):
    base_size, base = load_zip_png(TFC_JAR, f"assets/tfc/textures/block/ore/{grade}_hematite.png")
    _, source = load_zip_png(METALLUM_U_JAR if ore == "uraninite" else METALLUM_LEGACY_JAR,
        f"assets/tfc_metallum/textures/block/ore/{grade}_uraninite.png" if ore == "uraninite"
        else f"assets/tfc/textures/blocks/ores/{ore}.png")
    return base_size, transfer_palette(base, source)


def write_grade_assets(ore: str, grade: str) -> None:
    item_size, item_pixels = item_source(ore, grade)
    save_png(ASSETS / f"textures/item/ore/{grade}_{ore}.png", item_size, item_pixels)
    write_json(
        ASSETS / f"models/item/ore/{grade}_{ore}.json",
        {"parent": "item/generated", "textures": {"layer0": f"tfcm:item/ore/{grade}_{ore}"}},
    )
    block_size, block_pixels = block_source(ore, grade)
    save_png(ASSETS / f"textures/block/ore/{grade}_{ore}.png", block_size, block_pixels)
    for rock in (*ROCKS, "netherrack", "endstone"):
        all_texture = f"minecraft:block/{rock}" if rock in {"netherrack", "endstone"} else f"tfc:block/rock/raw/{rock}"
        model = {
            "parent": "tfc:block/ore",
            "textures": {"all": all_texture, "particle": all_texture, "overlay": f"tfcm:block/ore/{grade}_{ore}"},
        }
        blockstate = {"variants": {"": {"model": f"tfcm:block/ore/{grade}_{ore}/{rock}"}}}
        item_model = {"parent": f"tfcm:block/ore/{grade}_{ore}/{rock}"}
        write_json(ASSETS / f"models/block/ore/{grade}_{ore}/{rock}.json", model)
        write_json(ASSETS / f"blockstates/ore/{grade}_{ore}/{rock}.json", blockstate)
        write_json(ASSETS / f"models/item/ore/{grade}_{ore}/{rock}.json", item_model)


def write_small_assets(ore: str) -> None:
    size, pixels = item_source(ore, "small")
    save_png(ASSETS / f"textures/item/ore/small_{ore}.png", size, pixels)
    write_json(
        ASSETS / f"models/item/ore/small_{ore}.json",
        {"parent": "item/generated", "textures": {"layer0": f"tfcm:item/ore/small_{ore}"}},
    )
    write_json(
        ASSETS / f"models/block/ore/small_{ore}.json",
        {"parent": "tfcm:block/ore/small_ore_piece", "textures": {"ore": f"tfcm:item/ore/small_{ore}"}},
    )
    write_json(
        ASSETS / f"blockstates/ore/small_{ore}.json",
        {"variants": {"": [
            {"model": f"tfcm:block/ore/small_{ore}", "y": 90},
            {"model": f"tfcm:block/ore/small_{ore}"},
            {"model": f"tfcm:block/ore/small_{ore}", "y": 180},
            {"model": f"tfcm:block/ore/small_{ore}", "y": 270},
        ]}},
    )


def main() -> None:
    for ore in ORES:
        for grade in GRADES:
            write_grade_assets(ore, grade)
        write_small_assets(ore)
    print(f"Regenerated separate grade and small textures for {len(ORES)} ores.")


if __name__ == "__main__":
    main()
