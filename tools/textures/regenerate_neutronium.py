from __future__ import annotations

import json
from pathlib import Path

from color_transfer import load_zip_png, save_png, transfer_palette


ROOT = Path(__file__).resolve().parents[2]
ASSETS = ROOT / "shared/src/main/resources/assets"
TFC_JAR = next(
    (Path.home() / ".gradle/caches/modules-2/files-2.1/maven.modrinth/terrafirmacraft/4.2.5").glob(
        "*/terrafirmacraft-4.2.5.jar"
    )
)
MORE_ITEMS_JAR = ROOT / ".tmp/tfc_more_items/TFC-items-1.21.1-neoforge-1.2.1.jar"
NUCLEARCRAFT_JAR = ROOT / ".tmp/nuclearcraft/NuclearCraft-1.20.1-1.2.32.jar"
FORMS = ("ingot", "double_ingot", "sheet", "double_sheet", "rod")
MORE_ITEMS_FORMS = ("foil", "gear", "heavy_sheet", "nail", "ring", "rivet", "screw", "stamen", "wire")


def write_json(path: Path, value: object) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, indent=2) + "\n", encoding="utf-8")


def animated_frames() -> list[tuple[int, int, int, int]]:
    size, pixels = load_zip_png(
        NUCLEARCRAFT_JAR,
        "assets/nuclearcraft/textures/item/material/ingot/neutronium.png",
    )
    if size[0] != 16 or size[1] % size[0] != 0:
        raise ValueError(f"Unexpected neutronium source dimensions: {size}")
    frame_size = size[0] * size[0]
    return [pixels[offset:offset + frame_size] for offset in range(0, len(pixels), frame_size)]


def save_animated(path: Path, size: tuple[int, int], base: list[tuple[int, int, int, int]], frames) -> None:
    pixels = []
    for frame in frames:
        pixels.extend(transfer_palette(base, frame))
    save_png(path, (size[0], size[1] * len(frames)), pixels)
    path.with_suffix(path.suffix + ".mcmeta").write_text(
        json.dumps({"animation": {"frametime": 2}}) + "\n", encoding="utf-8"
    )


def item_model(form: str) -> None:
    write_json(
        ASSETS / f"tfcm/models/item/metal/{form}/neutronium.json",
        {"parent": "item/generated", "textures": {"layer0": f"tfcm:item/metal/{form}/neutronium"}},
    )


def block_models() -> None:
    source_root = ASSETS / "tfcm/models/block/metal/block"
    state_root = ASSETS / "tfcm/blockstates/metal/block"
    item_root = ASSETS / "tfcm/models/item/metal/block"
    for suffix in ("", "_slab", "_slab_top", "_stairs", "_stairs_inner", "_stairs_outer"):
        source = source_root / f"compressed_iron{suffix}.json"
        if source.exists():
            (source_root / f"neutronium{suffix}.json").write_text(
                source.read_text(encoding="utf-8").replace("compressed_iron", "neutronium"),
                encoding="utf-8",
            )
    for suffix in ("", "_slab", "_stairs"):
        source = state_root / f"compressed_iron{suffix}.json"
        if source.exists():
            (state_root / f"neutronium{suffix}.json").write_text(
                source.read_text(encoding="utf-8").replace("compressed_iron", "neutronium"),
                encoding="utf-8",
            )
    for suffix in ("", "_slab", "_stairs"):
        source = item_root / f"compressed_iron{suffix}.json"
        if source.exists():
            (item_root / f"neutronium{suffix}.json").write_text(
                source.read_text(encoding="utf-8").replace("compressed_iron", "neutronium"),
                encoding="utf-8",
            )


def main() -> None:
    frames = animated_frames()
    for form in FORMS:
        size, base = load_zip_png(TFC_JAR, f"assets/tfc/textures/item/metal/{form}/black_steel.png")
        save_animated(ASSETS / f"tfcm/textures/item/metal/{form}/neutronium.png", size, base, frames)
        item_model(form)
    for form in MORE_ITEMS_FORMS:
        size, base = load_zip_png(MORE_ITEMS_JAR, f"assets/tfc_items/textures/item/black_steel_{form}.png")
        save_animated(ASSETS / f"tfcm/textures/item/metal/{form}/neutronium.png", size, base, frames)
        item_model(form)

    for namespace, form in (("tfcm", "block"), ("tfc", "smooth")):
        size, base = load_zip_png(TFC_JAR, f"assets/tfc/textures/block/metal/{form}/black_steel.png")
        save_animated(ASSETS / f"{namespace}/textures/block/metal/{form}/neutronium.png", size, base, frames)
    block_models()
    print(f"Generated animated neutronium textures with {len(frames)} frames.")


if __name__ == "__main__":
    main()
