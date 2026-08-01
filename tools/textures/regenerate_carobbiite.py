from __future__ import annotations

import json
from pathlib import Path

from color_transfer import load_zip_png, save_png


ROOT = Path(__file__).resolve().parents[2]
ASSETS = ROOT / "shared/src/main/resources/assets/tfcm"
LEGACY_METALLUM_JAR = ROOT / ".tmp/tfc_metallum_legacy/TFC-Metallum-MC1.12.2-1.4.2.jar"
ROCKS = (
    "andesite", "basalt", "chalk", "chert", "claystone", "conglomerate", "dacite",
    "diorite", "dolomite", "gabbro", "gneiss", "granite", "limestone", "marble",
    "phyllite", "quartzite", "rhyolite", "schist", "shale", "slate", "tuff",
)


def write_json(path: Path, value: object) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, indent=2) + "\n", encoding="utf-8")


def remove_old_graded_assets() -> None:
    for root in (
        ASSETS / "textures/item/ore",
        ASSETS / "textures/block/ore",
        ASSETS / "models/item/ore",
        ASSETS / "models/block/ore",
        ASSETS / "blockstates/ore",
    ):
        if not root.exists():
            continue
        for path in root.rglob("*"):
            if "carobbiite" not in path.name and "carobbiite" not in str(path.parent):
                continue
            if path.is_file():
                path.unlink()


def write_block_assets(path_name: str) -> None:
    if path_name == "endstone":
        all_texture = "minecraft:block/end_stone"
    elif path_name == "netherrack":
        all_texture = "minecraft:block/netherrack"
    else:
        all_texture = f"tfc:block/rock/raw/{path_name}"
    model = {
        "parent": "tfc:block/ore",
        "textures": {"all": all_texture, "particle": all_texture, "overlay": "tfcm:block/ore/carobbiite"},
    }
    blockstate = {"variants": {"": {"model": f"tfcm:block/ore/carobbiite/{path_name}"}}}
    item_model = {"parent": f"tfcm:block/ore/carobbiite/{path_name}"}
    write_json(ASSETS / f"models/block/ore/carobbiite/{path_name}.json", model)
    write_json(ASSETS / f"blockstates/ore/carobbiite/{path_name}.json", blockstate)
    write_json(ASSETS / f"models/item/ore/carobbiite/{path_name}.json", item_model)


def main() -> None:
    remove_old_graded_assets()
    item_size, item_pixels = load_zip_png(
        LEGACY_METALLUM_JAR, "assets/tfc/textures/items/ore/carobbiite.png"
    )
    block_size, block_pixels = load_zip_png(
        LEGACY_METALLUM_JAR, "assets/tfc/textures/blocks/ores/carobbiite.png"
    )
    save_png(ASSETS / "textures/item/ore/carobbiite.png", item_size, item_pixels)
    save_png(ASSETS / "textures/block/ore/carobbiite.png", block_size, block_pixels)
    write_json(
        ASSETS / "models/item/ore/carobbiite.json",
        {"parent": "item/generated", "textures": {"layer0": "tfcm:item/ore/carobbiite"}},
    )

    for rock in (*ROCKS, "netherrack", "endstone"):
        write_block_assets(rock)

    write_json(
        ASSETS / "models/block/ore/small_carobbiite.json",
        {"parent": "tfcm:block/ore/small_ore_piece", "textures": {"ore": "tfcm:item/ore/carobbiite"}},
    )
    write_json(
        ASSETS / "blockstates/ore/small_carobbiite.json",
        {"variants": {"": [
            {"model": "tfcm:block/ore/small_carobbiite", "y": 90},
            {"model": "tfcm:block/ore/small_carobbiite"},
            {"model": "tfcm:block/ore/small_carobbiite", "y": 180},
            {"model": "tfcm:block/ore/small_carobbiite", "y": 270},
        ]}},
    )
    print("Regenerated ungraded carobbiite assets and removed obsolete graded assets.")


if __name__ == "__main__":
    main()
