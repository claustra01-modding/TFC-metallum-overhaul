from __future__ import annotations

import json
from pathlib import Path


ROOT = Path(__file__).resolve().parents[2]
RESOURCES = ROOT / "shared/src/main/resources"
ASSETS = RESOURCES / "assets/tfcmu2"
DATA = RESOURCES / "data"


def clone_rock_resources() -> list[str]:
    source_dir = ASSETS / "models/block/ore/fluorite"
    rocks = sorted(path.stem for path in source_dir.glob("*.json") if path.stem not in {"netherrack", "endstone"})
    templates = (
        ASSETS / "blockstates/ore/fluorite",
        ASSETS / "models/block/ore/fluorite",
        ASSETS / "models/item/ore/fluorite",
        DATA / "tfcmu2/loot_table/blocks/ore/fluorite",
    )
    for source_root in templates:
        target_root = Path(str(source_root).replace("fluorite", "quartz"))
        target_root.mkdir(parents=True, exist_ok=True)
        for rock in rocks:
            source = source_root / f"{rock}.json"
            target = target_root / f"{rock}.json"
            target.write_text(source.read_text(encoding="utf-8").replace("fluorite", "quartz"), encoding="utf-8")
    return rocks


def add_tag_value(path: Path, value: str) -> None:
    data = json.loads(path.read_text(encoding="utf-8-sig"))
    if value not in data["values"]:
        data["values"].append(value)
    path.write_text(json.dumps(data, indent=2) + "\n", encoding="utf-8")


def write_tags(rocks: list[str]) -> None:
    block_tag = DATA / "c/tags/block/ores/quartz.json"
    block_tag.parent.mkdir(parents=True, exist_ok=True)
    block_tag.write_text(
        json.dumps({"values": [f"tfcmu2:ore/quartz/{rock}" for rock in rocks]}, indent=2) + "\n",
        encoding="utf-8",
    )
    add_tag_value(DATA / "c/tags/block/ores.json", "#c:ores/quartz")


def write_language(rocks: list[str]) -> None:
    path = ASSETS / "lang/en_us.json"
    data = json.loads(path.read_text(encoding="utf-8-sig"))
    rock_names = {
        "andesite": "Andesite",
        "basalt": "Basalt",
        "chalk": "Chalk",
        "chert": "Chert",
        "claystone": "Claystone",
        "conglomerate": "Conglomerate",
        "dacite": "Dacite",
        "diorite": "Diorite",
        "dolomite": "Dolomite",
        "gabbro": "Gabbro",
        "gneiss": "Gneiss",
        "granite": "Granite",
        "limestone": "Limestone",
        "marble": "Marble",
        "phyllite": "Phyllite",
        "quartzite": "Quartzite",
        "rhyolite": "Rhyolite",
        "schist": "Schist",
        "shale": "Shale",
        "slate": "Slate",
        "tuff": "Tuff",
    }
    for rock in rocks:
        key = f"block.tfcmu2.ore.quartz.{rock}"
        data[key] = f"Quartz {rock_names[rock]} Ore"
        data[f"{key}.prospected"] = "Quartz"
    path.write_text(json.dumps(data, indent=2) + "\n", encoding="utf-8")


def main() -> None:
    rocks = clone_rock_resources()
    write_tags(rocks)
    write_language(rocks)
    print(f"Generated Quartz ore resources for {len(rocks)} TFC rocks.")


if __name__ == "__main__":
    main()
