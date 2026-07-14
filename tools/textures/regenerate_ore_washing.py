from __future__ import annotations

import json
import sys
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(Path(__file__).resolve().parent))

from color_transfer import load_png, load_zip_png, save_png, transfer_palette


ASSETS = ROOT / "shared/src/main/resources/assets/tfcmu2"
TFC_JAR = next(
    (Path.home() / ".gradle/caches/modules-2/files-2.1/maven.modrinth/terrafirmacraft/4.2.5").glob(
        "*/terrafirmacraft-4.2.5.jar"
    )
)
ORE_WASHING_JAR = ROOT / ".tmp/tfcorewashing/tfcorewashing-1.21.1-neoforge-1.1.4.jar"

ORES = (
    "stibnite", "rutile", "cobaltite", "spodumene", "bauxite", "galena", "uraninite",
    "wolframite", "native_platinum", "native_naquadah", "native_iridium", "native_osmium",
    "mithril_matrix",
)

FORM_BASES = {
    "pellet": (ORE_WASHING_JAR, "assets/tfcorewashing/textures/item/pellet_chromium.png"),
    "briquet": (ORE_WASHING_JAR, "assets/tfcorewashing/textures/item/briquet_chromium.png"),
    "chunks": (ORE_WASHING_JAR, "assets/tfcorewashing/textures/item/chunks_graphite.png"),
    "rocky_chunks": (ORE_WASHING_JAR, "assets/tfcorewashing/textures/item/rocky_chunks_graphite.png"),
    "dirty_dust": (ORE_WASHING_JAR, "assets/tfcorewashing/textures/item/dirty_dust_graphite.png"),
    "dirty_pile": (ORE_WASHING_JAR, "assets/tfcorewashing/textures/item/dirty_pile_graphite.png"),
    "powder": (TFC_JAR, "assets/tfc/textures/item/powder/graphite.png"),
}


def write_rocky_model(ore: str) -> None:
    path = ASSETS / f"models/item/metal/rocky_chunks/{ore}.json"
    data = {
        "parent": "item/generated",
        "textures": {
            "layer0": f"tfcmu2:item/metal/rocky_chunks/{ore}",
            "layer1": "tfcmu2:item/metal/rocky_chunks/_rocky_overlay",
        },
    }
    path.write_text(json.dumps(data, separators=(",", ":")), encoding="utf-8")


def main() -> None:
    overlay_size, overlay = load_png(
        ASSETS / "textures/item/metal/rocky_chunks/_rocky_overlay.png"
    )
    overlay_indices = {index for index, pixel in enumerate(overlay) if pixel[3] > 0}
    sources = {
        ore: load_png(ASSETS / f"textures/item/ore/normal_{ore}.png")[1]
        for ore in ORES
    }
    generated = 0

    for form, (archive, member) in FORM_BASES.items():
        size, base = load_zip_png(archive, member)
        hidden = overlay_indices if form == "rocky_chunks" else set()
        if form == "rocky_chunks" and size != overlay_size:
            raise ValueError("rocky chunks and overlay dimensions differ")
        for ore in ORES:
            target = ASSETS / f"textures/item/metal/{form}/{ore}.png"
            save_png(target, size, transfer_palette(base, sources[ore], hidden))
            if form == "rocky_chunks":
                write_rocky_model(ore)
            generated += 1

    print(f"Regenerated {generated} Ore Washing textures for {len(ORES)} ores.")


if __name__ == "__main__":
    main()
