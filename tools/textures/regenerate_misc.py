from __future__ import annotations

import sys
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
sys.path.insert(0, str(Path(__file__).resolve().parent))

from color_transfer import load_png, load_zip_png, save_png, transfer_palette


ASSETS = ROOT / "shared/src/main/resources/assets/tfcmu2/textures/item"
TFC_JAR = next(
    (Path.home() / ".gradle/caches/modules-2/files-2.1/maven.modrinth/terrafirmacraft/4.2.5").glob(
        "*/terrafirmacraft-4.2.5.jar"
    )
)
CROSSOVER_JAR = ROOT / ".tmp/tfc_ie_crossover/TFC-IE-Crossover-NeoForge-1.21.1-2.1.3.jar"


def main() -> None:
    cut_size, cut_quartz = load_zip_png(
        CROSSOVER_JAR, "assets/tfc_ie_addon/textures/item/mineral/quartz_shard.png"
    )
    save_png(ASSETS / "gem/cut_quartz.png", cut_size, cut_quartz)

    raw_size, raw_base = load_zip_png(TFC_JAR, "assets/tfc/textures/item/ore/amethyst.png")
    save_png(ASSETS / "ore/quartz.png", raw_size, transfer_palette(raw_base, cut_quartz))

    powder_size, powder_base = load_zip_png(
        TFC_JAR, "assets/tfc/textures/item/powder/graphite.png"
    )
    fluorite = load_png(ASSETS / "ore/fluorite.png")[1]
    save_png(
        ASSETS / "powder/fluorite.png",
        powder_size,
        transfer_palette(powder_base, fluorite),
    )

    print("Regenerated Quartz, Cut Quartz, and Fluorite Powder textures.")


if __name__ == "__main__":
    main()
