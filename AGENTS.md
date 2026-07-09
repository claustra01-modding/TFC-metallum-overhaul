# tfcmu2: 金属・鉱石アイテム形状一覧（派生元Mod別）

このドキュメントでいう「各アイテム」は、`ingot` や `sheet` のような形状（フォーム）を指す。

## 運用ルール

- 仕様追加や実装変更があった場合、この `AGENTS.md` を随時更新する。
- 形状の追加・削除、条件付き有効化の変更、ID規則の変更を優先して反映する。
- 依存元Modの取得・展開・一時解析（jar展開や素材抽出など）は、リポジトリ直下の `.tmp` で行う。

## 1. 独自金属

### 1.1 金属アイテム形状

対象金属:
`compressed_iron`, `platinum`, `naquadah`, `iridium`, `osmium`, `osmiridium`, `mithril`, `arcane`, `refined_glowstone`, `refined_obsidian`, `antimony`, `titanium`, `cobalt`, `tungsten`, `solder`, `tungsten_steel`, `netherite`

常時生成される形状:
- `ingot`
- `double_ingot`
- `sheet`（plate）
- `double_sheet`
- `rod`

ID規則:
- `tfcmu2:metal/ingot/<metal>`
- `tfcmu2:metal/double_ingot/<metal>`
- `tfcmu2:metal/sheet/<metal>`
- `tfcmu2:metal/double_sheet/<metal>`
- `tfcmu2:metal/rod/<metal>`

`tfc_items` 導入時のみ生成される追加形状:
- `foil`
- `gear`
- `heavy_sheet`
- `nail`
- `ring`
- `rivet`
- `screw`
- `stamen`
- `wire`

ID規則:
- `tfcmu2:metal/<form>/<metal>`

特殊:
- `tfcmu2:metal/ingot/high_carbon_tungsten_steel`

保管・建材形状:
- `block`
- `block_slab`
- `block_stairs`

ID規則:
- `tfcmu2:metal/block/<metal>`
- `tfcmu2:metal/block/<metal>_slab`
- `tfcmu2:metal/block/<metal>_stairs`

### 1.2 鉱石アイテム形状

対象鉱石:
- 品位あり: `native_platinum`, `native_naquadah`, `native_iridium`, `native_osmium`, `rutile`, `cobaltite`, `mithril_matrix`, `stibnite`, `wolframite`
- 品位なし: `fluorite`

鉱石アイテム形状:
- 品位あり鉱石: `poor`, `normal`, `rich`
- 品位なし鉱石: 基本形のみ

ID規則:
- `tfcmu2:ore/{poor|normal|rich}_<ore>`（品位あり）
- `tfcmu2:ore/<ore>`（品位なし）

鉱石ブロック形状:
- TFC岩石内: `/<tfc_rock>`
- バニラ石材内: `/netherrack`, `/endstone`

ID規則:
- `tfcmu2:ore/{poor|normal|rich}_<ore>/<rock_or_stone>`
- `tfcmu2:ore/<ore>/<rock_or_stone>`

地表サンプル形状:
- 品位あり鉱石: `small_<ore>`（ブロックアイテムあり）
- `small_fluorite`: groundcoverブロックのみ（ブロックアイテムなし）

ID規則:
- `tfcmu2:ore/small_<ore>`

`tfcorewashing` 導入時のみ（品位あり鉱石対象）:
- `pellet`
- `briquet`
- `chunks`
- `rocky_chunks`
- `dirty_dust`
- `dirty_pile`
- `powder`

ID規則:
- `tfcmu2:metal/<form>/<ore>`

## 2. `tfc` 由来（compat展開）

`Tfcmu2CompatOres.TFC_ORES` の鉱石に対して、以下の形状を追加する。

追加される形状:
- `netherrack` 内鉱石ブロック
- `endstone` 内鉱石ブロック

ID規則:
- `tfcmu2:ore/<ore_name>/netherrack`
- `tfcmu2:ore/<ore_name>/endstone`

補足:
- 一部の非品位鉱石ピースは `small_<ore_piece_name>` の groundcoverブロックを追加（ブロックアイテムなし）。
- このcompat層では `ingot` や `sheet` などの金属形状は追加しない。

## 3. `firmalife` 由来（compat展開）

対象:
`normal_chromite`, `poor_chromite`, `rich_chromite`

追加される形状:
- `netherrack` 内鉱石ブロック
- `endstone` 内鉱石ブロック

ID規則:
- `tfcmu2:ore/<ore_name>/netherrack`
- `tfcmu2:ore/<ore_name>/endstone`

補足:
- このcompat層では金属形状は追加しない。

## 4. `tfc_ie_addon` 由来（compat展開）

対象:
`normal_bauxite`, `poor_bauxite`, `rich_bauxite`
`normal_galena`, `poor_galena`, `rich_galena`
`normal_uraninite`, `poor_uraninite`, `rich_uraninite`

追加される形状:
- `netherrack` 内鉱石ブロック
- `endstone` 内鉱石ブロック

ID規則:
- `tfcmu2:ore/<ore_name>/netherrack`
- `tfcmu2:ore/<ore_name>/endstone`

補足:
- このcompat層では金属形状は追加しない。

## 5. 鉱石アイテム有無（重要）

- 単体の鉱石アイテム（`tfcmu2:ore/{poor|normal|rich}_<ore>`, `tfcmu2:ore/<ore>`）を持つのは `tfcmu2` 独自鉱石のみ。
- `tfc` / `firmalife` / `tfc_ie_addon` 由来は、このMod側では主に `netherrack` / `endstone` 向け鉱石ブロック展開。

## 6. 有効化条件

- `tfcmu2` 独自の金属・鉱石形状: 常時有効
- `firmalife` compat鉱石形状: `firmalife` ロード時のみ
- `tfc_ie_addon` compat鉱石形状: `tfc_ie_addon` ロード時のみ
- 追加金属形状（`foil`, `wire` など）: `tfc_items` ロード時のみ
- 鉱石洗浄形状: `tfcorewashing` ロード時のみ

## 7. レシピ実装type

主要ディレクトリ:
- `src/main/resources/data/tfcmu2/recipe`
- `src/main/resources/data/minecraft/recipe`（`netherite_ingot.json` の条件付き上書き）

レシピカテゴリtype:
- `alloy`
- `anvil`
- `casting`
- `crafting`
- `heating`
- `ore_washing`
- `tfc`
- `welding`

金属形状のレシピtype:
- `casting/ingot`
- `anvil/metal/sheet`
- `anvil/metal/rod`
- `welding/metal/double_ingot`
- `welding/metal/double_sheet`
- `crafting/metal/block`（`block`, `block_slab`, `block_stairs`）
- `heating/metal/ingot`
- `heating/metal/double_ingot`
- `heating/metal/sheet`
- `heating/metal/double_sheet`
- `heating/metal/rod`
- `heating/metal/block`

追加金属形状のレシピtype（`tfc_items` 条件付き）:
- `anvil/more_items/foil`
- `anvil/more_items/gear`
- `anvil/more_items/nail`
- `anvil/more_items/ring`
- `anvil/more_items/rivet`
- `anvil/more_items/screw`
- `anvil/more_items/stamen`
- `anvil/more_items/wire`
- `welding/more_items/heavy_sheet`
- `heating/more_items/foil`
- `heating/more_items/gear`
- `heating/more_items/heavy_sheet`
- `heating/more_items/nail`
- `heating/more_items/ring`
- `heating/more_items/rivet`
- `heating/more_items/screw`
- `heating/more_items/stamen`
- `heating/more_items/wire`

鉱石関連レシピtype:
- `heating/ore`（`poor`, `normal`, `rich`, `small`）
- `ore_washing/ores/*`（hammer系）
- `ore_washing/chunks/quern`
- `ore_washing/chunks/milling`
- `ore_washing/chunks/crusher`
- `ore_washing/rocky_chunks/splashing`（`create` 併用条件あり）
- `ore_washing/dirt_dusts/splashing`（`create` 併用条件あり）
- `crafting/ore_washing/pellet`
- `crafting/ore_washing/briquet`
- `crafting/ore_washing/dirty_dust`
- `crafting/ore_washing/uncompress_pellet`
- `crafting/ore_washing/uncompress_briquet`
- `crafting/ore_washing/uncompress_dust`
- `tfc/heating/ore_washing/pellet`
- `tfc/heating/ore_washing/briquet`
- `tfc/heating/ore_washing/powder`

compat鉱石（`tfc` / `firmalife` / `tfc_ie_addon`）:
- このMod側では専用加工レシピtypeは持たず、主に鉱石ブロック展開が役割。

## 8. モデル実装type

主要ディレクトリ:
- `src/main/resources/assets/tfcmu2/models/block`
- `src/main/resources/assets/tfcmu2/models/item`

金属モデルtype:
- `models/item/metal/ingot`
- `models/item/metal/double_ingot`
- `models/item/metal/sheet`
- `models/item/metal/double_sheet`
- `models/item/metal/rod`
- `models/item/metal/foil`
- `models/item/metal/gear`
- `models/item/metal/heavy_sheet`
- `models/item/metal/nail`
- `models/item/metal/ring`
- `models/item/metal/rivet`
- `models/item/metal/screw`
- `models/item/metal/stamen`
- `models/item/metal/wire`
- `models/item/metal/pellet`
- `models/item/metal/briquet`
- `models/item/metal/chunks`
- `models/item/metal/rocky_chunks`
- `models/item/metal/dirty_dust`
- `models/item/metal/dirty_pile`
- `models/item/metal/powder`
- `models/item/metal/block`
- `models/block/metal/block`（`block`, `slab`, `stairs`）

鉱石モデルtype:
- `models/item/ore/<ore_or_grade>`
- `models/item/ore/<ore_or_grade>/<rock_or_stone>`
- `models/block/ore/small_*`
- `models/block/ore/<ore_or_grade>/<rock_or_stone>`

鉱石モデルの補足:
- compat鉱石は主に `netherrack` / `endstone` 向けtypeを持つ。
- 独自鉱石は TFC岩石 + バニラ石材向けtypeを持つ。
- 一部 `small_*` は groundcover blockのみ（ブロックアイテムなし）。

## 9. テクスチャ生成方法

対象:
- 新規金属の `block` / `ingot` / `double_ingot` / `sheet` / `double_sheet` / `rod`
- `tfc_items` 由来追加形状（`foil`, `gear`, `heavy_sheet`, `nail`, `ring`, `rivet`, `screw`, `stamen`, `wire`）

手順:
1. `.tmp` で依存jarを展開し、TFC / TFC More Items の `wrought_iron` テクスチャをベースとして取得する。
2. 元Modごとに、以下のどちらかの方式で色変換する。

通常版（単色式）:
- 元Modの該当 `ingot.png` から代表色（平均色）を抽出する。
- ベース画像の非透過ピクセルごとに輝度を算出し、代表色へ乗算して生成する。
- 元Mod側の固有ハイライトは持ち込まず、TFC / TFC More Items 側の陰影をそのまま使う。

補正式（通常版）:
- `lum = (0.2126*R + 0.7152*G + 0.0722*B) / 255`
- `lum2 = clamp(((lum - 0.5) * 1.35) + 0.5, 0, 1)`
- `mapped = 0.08 + 0.92 * lum2`
- `out = tone * mapped`

高輝度式:
- 元Modの該当 `ingot.png` から、非透過ピクセルの輝度分布を使って `shadow` / `mid` / `highlight` の3色パレットを抽出する。
- ベース画像の非透過ピクセルごとに輝度を算出し、補正後の輝度を使って `shadow -> mid -> highlight` の順に段階補間する。
- 高輝度ピクセルには `highlight` と白寄りの `glint` を追加で混ぜ、元Mod ingot の明るいハイライト感を他フォームへ移す。

補正式（高輝度式）:
- `lum = (0.2126*R + 0.7152*G + 0.0722*B) / 255`
- `lum2 = clamp((pow(lum, 0.82) - 0.08) / 0.92, 0, 1)`
- `base = lerp(shadow, mid, lum2 / 0.58)` または `lerp(mid, highlight, (lum2 - 0.58) / 0.42)`
- `highlight_mix = smoothstep(0.72, 0.94, lum2)`
- `specular_mix = smoothstep(0.88, 1.0, lum2)`
- `final = lerp(lerp(base, highlight, 0.45 * highlight_mix), glint, 0.55 * specular_mix)`

実装・運用:
- 生成スクリプト: `.tmp/add_new_metals.ps1`
- 元Modに明るい固有ハイライトがない場合は通常版を使う。
- 元Modの ingot に明るい固有ハイライトがある場合は高輝度式を使う。
- ハイライト調整時は、パレット抽出の閾値や `highlight_mix` / `specular_mix` の係数を更新して全対象形状を再生成する。
- `cobalt` 金属フォームと `cobaltite` 鉱石テクスチャは TFC Metallum U（`tfc_metallum`）の cobalt / cobaltite テクスチャを元素材として使う。
- `mithril` and `arcane` metal forms use the 高輝度式 wrought-iron recolor workflow sampled from Iron's Spells 'n Spellbooks ingot textures (`mithril_ingot.png` and `arcane_ingot.png`); do not copy Iron's Spells textures into the repository.
- `mithril_matrix` is the graded ore for `mithril`; its ore item and overlay textures use temporary 高輝度式 placeholders until final art is provided.

## 10. 参照コード

- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Metal.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Ore.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Items.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Blocks.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2CompatOres.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Mod.java`

## 11. TFC item_heat implementation

Directory:
- `src/main/resources/data/tfcmu2/tfc/item_heat`

Coverage:
- Metal heat definitions include `block`, `block_slab`, and `block_stairs` separately.
- Graded own ores (`small`, `poor`, `normal`, `rich`) each share one `item_heat/<ore>.json`.
- `tfcorewashing` heating inputs are covered by `item_heat/pellet_briquet/<ore>.json` and `item_heat/powder/<ore>.json`.

Notes:
- `block_slab` and `block_stairs` use direct item IDs, not `c:storage_blocks/*` tags.
- Ore heat values follow the source metal heat values; powder heat uses the source metal heat tier.

## 12. Multi-version build layout

- Supported targets:
  - Minecraft 1.20.1: Forge 47.4.20, TFC 3.2.23, JEI 15.20.0.133, Java 17, under `versions/mc1_20_1/src/main`.
  - Minecraft 1.21.1: NeoForge 21.1.235, TFC 4.2.5, JEI 19.27.0.346, Java 21, under `versions/mc1_21_1/src/main`.
- The Minecraft 1.21.1 NeoForge implementation lives under `versions/mc1_21_1/src/main`.
- The Minecraft 1.20.1 Forge implementation uses ForgeGradle and `META-INF/mods.toml`.
- Shared Java and common resources live under `shared/src/main`; loader metadata stays in each version project.
- Each version project owns its root `pack.mcmeta`; keep `pack_format` matched to that Minecraft target.
- The root `build.gradle` is an aggregator; use `./gradlew build` for all targets, or build one target with `./gradlew :versions:mc1_20_1:build` or `./gradlew :versions:mc1_21_1:build`.
- `./gradlew build` and `./gradlew collectArtifacts` copy release jars into root `build/libs`.
- Common mod metadata stays in root `gradle.properties`; version-specific Minecraft, loader, TFC, JEI, and Java versions stay in each version project's `gradle.properties`.
- Keep the Gradle wrapper on a Gradle 8.x release; ForgeGradle 6 does not support Gradle 9 yet. Configuration cache is disabled for ForgeGradle compatibility.
- Older `src/main/...` path notes above now map to `shared/src/main/...` for common resources/data and `versions/<version>/src/main/...` for loader-specific code.
- Do not add loader-specific sources back under root `src`.
- Keep shared data resources in the Minecraft 1.21 layout; the Minecraft 1.20.1 build converts resource paths, common tag namespaces, and JSON compatibility keys during `processResources`.
- Minecraft 1.20.1 recipe conversion also adapts TFC casting fluids, alloy metal references, and advanced shaped crafting `input_row`.
- Minecraft 1.20.1 generates TFC metal manager JSON from `tfcmu2/tfc/fluid_heat` for alloy recipe compatibility.
- Minecraft 1.20.1 tag conversion rewrites `#c` / `#neoforge` references to `#forge` and removes TFC tuff ore tag entries.

## 13. Molten fluid compatibility

- Own molten fluid registry IDs stay under `tfc:metal/<metal>` for released-world compatibility.
- Minecraft 1.20.1 adds `tfcmu2:bucket/metal/<metal>` bucket items and `tfc:fluid/metal/<metal>` fluid blocks so Forge/TFC creative tab enumeration never receives an empty bucket stack.
