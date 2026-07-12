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
- TFC propickは鉱石blockの通常translation keyではなく、末尾に `.prospected` を付けたキーを表示する。独自鉱石の全品位・全母岩blockと、TFC / Firmalife / TFC IE Addon由来のNether・End鉱石blockについて `en_us.json` にこのキーを定義する。値は元Modの `.prospected` と一致させ、品位・母岩名を含めない（例: diamondは `Kimberlite`、pyriteは `Native Gold?`）。

## 9. テクスチャ生成方法

対象:
- 新規金属の `block` / `ingot` / `double_ingot` / `sheet` / `double_sheet` / `rod`
- `tfc_items` 由来追加形状（`foil`, `gear`, `heavy_sheet`, `nail`, `ring`, `rivet`, `screw`, `stamen`, `wire`）
- TFC ingot pile 用 soft texture: `assets/tfc/textures/block/metal/smooth/<metal>.png`

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
- `mithril` と `arcane` の金属形状は、Iron's Spells 'n Spellbooks の ingot テクスチャ（`mithril_ingot.png`, `arcane_ingot.png`）から抽出した色を使い、高輝度式で wrought iron ベースを再着色する。Iron's Spells のテクスチャ自体はリポジトリへコピーしない。
- `mithril_matrix` は `mithril` 対応の品位あり鉱石。鉱石アイテムと overlay テクスチャは、正式素材が用意されるまで高輝度式の仮素材を使う。
- ingot / double ingot の pile は、TFC 側の soft metal texture lookup により `tfc:block/metal/smooth/<metal>` を参照する。この `smooth` テクスチャは `tfc` 名前空間に置き、金属テクスチャと同じ方式で生成する。対象 TFC バージョンが sheet pile に対応しない限り、sheet pile 用アセットは追加しない。
- TFC Ore Washing 系アイテム（`pellet`, `briquet`, `chunks`, `rocky_chunks`, `dirty_dust`, `dirty_pile`, `powder`）は TFC `item/ore/graphite` の輝度をベースにし、先に graphite 輝度を白方向へ淡色化してから、各対象鉱石の代表色で通常版（単色式）補正を行う。
- tfcorewashing に graphite 版がある Ore Washing 形状は graphite 版を形状元にする。graphite 版がない `pellet` / `briquet` は chromium 版を形状マスクとしてのみ使い、輝度を TFC `item/ore/graphite` へ再マップしてから補正する。`powder` は TFC `item/powder/graphite` を形状元にする。
- `rocky_chunks` の item model は、鉱石部分だけのベーステクスチャに `tfcmu2:item/metal/rocky_chunks/_rocky_overlay` を重ねる。固定色の岩部分は個別 PNG ではなく overlay 側に保持する。

## 10. 参照コード

- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Metal.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Ore.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Items.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Blocks.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2CompatOres.java`
- `src/main/java/net/claustra01/tfcmu2/Tfcmu2Mod.java`

## 11. TFC item_heat 実装

ディレクトリ:
- `src/main/resources/data/tfcmu2/tfc/item_heat`

対象:
- 金属の heat 定義は `block`, `block_slab`, `block_stairs` を個別に持つ。
- 独自の品位あり鉱石（`small`, `poor`, `normal`, `rich`）は、鉱石ごとに同じ `item_heat/<ore>.json` を共有する。
- `tfcorewashing` の加熱入力は `item_heat/pellet_briquet/<ore>.json` と `item_heat/powder/<ore>.json` で扱う。

補足:
- `block_slab` と `block_stairs` は `c:storage_blocks/*` タグではなく、直接 item ID を使う。
- 鉱石の heat 値は対応する金属の heat 値に合わせる。powder の heat は対応金属の heat tier を使う。

## 12. TFC item_size 実装

ディレクトリ:
- `shared/src/main/resources/data/tfc/tfc/item_size`

対象:
- TFC 標準の item size が金属形状に一致するように、トップレベルの common item tag（`c:ingots`, `c:sheets`, `c:foils` など）には、このModの金属別タグを含める。
- TFMCU2 の金属ブロック、slab、stairs、TFC More Items 形状、tfcorewashing 形状には明示的な item size 定義を追加する。
- 共有 item size リソースは Minecraft 1.21 の `item_size` パスに置く。Minecraft 1.20.1 ビルドでは、このパスを `item_sizes` へ変換する。

## 13. マルチバージョン build 構成

- 対応対象:
  - Minecraft 1.20.1: Forge 47.4.20, TFC 3.2.23, JEI 15.20.0.133, Java 17。実装は `versions/mc1_20_1/src/main` 配下。
  - Minecraft 1.21.1: NeoForge 21.1.235, TFC 4.2.5, JEI 19.27.0.346, Java 21。実装は `versions/mc1_21_1/src/main` 配下。
- Minecraft 1.21.1 NeoForge 実装は `versions/mc1_21_1/src/main` に置く。
- Minecraft 1.20.1 Forge 実装は ForgeGradle と `META-INF/mods.toml` を使う。
- 共有 Java と共通リソースは `shared/src/main` に置き、loader metadata は各 version project 側に置く。
- 各 version project は自身の root `pack.mcmeta` を持つ。`pack_format` は対象 Minecraft バージョンに合わせる。
- root `build.gradle` は集約用。全対象は `./gradlew build`、個別対象は `./gradlew :versions:mc1_20_1:build` または `./gradlew :versions:mc1_21_1:build` で build する。
- `./gradlew build` と `./gradlew collectArtifacts` は release jar を root `build/libs` へコピーする。
- 共通 mod metadata は root `gradle.properties` に置き、Minecraft / loader / TFC / JEI / Java のバージョン指定は各 version project の `gradle.properties` に置く。
- Gradle wrapper は Gradle 8.x に維持する。ForgeGradle 6 は Gradle 9 に未対応。ForgeGradle 互換性のため configuration cache は無効化する。
- 上記の古い `src/main/...` パス表記は、共通リソース/data では `shared/src/main/...`、loader 固有コードでは `versions/<version>/src/main/...` と読み替える。
- loader 固有 source を root `src` 配下へ戻さない。
- 共有 data リソースは Minecraft 1.21 レイアウトで保持する。Minecraft 1.20.1 build は `processResources` 中にリソースパス、common tag namespace、JSON 互換 key を変換する。
- Minecraft 1.20.1 の recipe 変換では、TFC casting fluid、alloy metal reference、advanced shaped crafting の `input_row` も変換する。
- Minecraft 1.20.1 では alloy recipe 互換性のため、`tfcmu2/tfc/fluid_heat` から TFC metal manager JSON を生成する。
- Minecraft 1.20.1 の tag 変換では、`#c` / `#neoforge` 参照を `#forge` へ書き換え、TFC `item_size` パスを `item_sizes` へ変換し、TFC tuff ore tag entry を削除する。

## 14. 溶融 fluid 互換性

- 独自 molten fluid の registry ID は、既存リリース済みワールドとの互換性維持のため `tfc:metal/<metal>` のままにする。
- Minecraft 1.20.1 では Forge/TFC の creative tab 列挙が空 bucket stack を受け取らないように、`tfcmu2:bucket/metal/<metal>` bucket item と `tfc:fluid/metal/<metal>` fluid block を追加する。

## 15. カスタム鉱脈設定

- 詳細仕様は `config_doc.md` を参照する。
- 鉱石出力は `blocks` リストで定義し、各要素に `block` と省略可能な `weight`（既定値 `1`）を指定する。
- `tier` はリストではなく、品位名から整数weightへのマッピングとして記述する。
- 読み込み時は、既存configとの互換性のため旧 `block` とリスト風 `tier` も受け入れる。
- YAMLパーサー本体は `shared/src/main/java` に置き、TFC API差分は各versionの `Tfcmu2VeinPlatform` に限定する。
