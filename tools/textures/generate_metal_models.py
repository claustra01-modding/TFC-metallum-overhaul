from __future__ import annotations

import json
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
ASSETS = ROOT / "shared/src/main/resources/assets"
METALS = (
    "boron",
    "thorium",
    "magnesium",
    "beryllium",
    "zirconium",
    "neutronium",
    "ferroboron",
    "tough_alloy",
    "zircaloy",
    "hsla_steel",
    "super_alloy",
)
FORMS = (
    "ingot",
    "double_ingot",
    "sheet",
    "double_sheet",
    "rod",
    "foil",
    "gear",
    "heavy_sheet",
    "nail",
    "ring",
    "rivet",
    "screw",
    "stamen",
    "wire",
)


def write_json(path: Path, value: object) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, indent=2) + "\n", encoding="utf-8")


def item_models(metal: str) -> None:
    for form in FORMS:
        write_json(
            ASSETS / f"tfcm/models/item/metal/{form}/{metal}.json",
            {
                "parent": "item/generated",
                "textures": {"layer0": f"tfcm:item/metal/{form}/{metal}"},
            },
        )

    for suffix in ("", "_slab", "_stairs"):
        write_json(
            ASSETS / f"tfcm/models/item/metal/block/{metal}{suffix}.json",
            {"parent": f"tfcm:block/metal/block/{metal}{suffix}"},
        )


def block_models(metal: str) -> None:
    source_root = ASSETS / "tfcm/models/block/metal/block"
    state_root = ASSETS / "tfcm/blockstates/metal/block"
    for suffix in ("", "_slab", "_slab_top", "_stairs", "_stairs_inner", "_stairs_outer"):
        source = source_root / f"awakened_draconium{suffix}.json"
        target = source_root / f"{metal}{suffix}.json"
        target.write_text(
            source.read_text(encoding="utf-8").replace("awakened_draconium", metal),
            encoding="utf-8",
        )
    for suffix in ("", "_slab", "_stairs"):
        source = state_root / f"awakened_draconium{suffix}.json"
        target = state_root / f"{metal}{suffix}.json"
        target.write_text(
            source.read_text(encoding="utf-8").replace("awakened_draconium", metal),
            encoding="utf-8",
        )


def main() -> None:
    for metal in METALS:
        item_models(metal)
        block_models(metal)
    print(f"Generated metal models and blockstates for {len(METALS)} metals.")


if __name__ == "__main__":
    main()
