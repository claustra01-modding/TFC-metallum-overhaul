# tfcm 開発仕様

## 1. 共通・固有運用ルール

- 仕様、形状、ID、有効化条件、生成規則を変更した場合はこのファイルを更新する。
- 共通実装は `shared/src/main`、Minecraft・loader固有実装は `versions/<version>/src/main` に置く。
- loader固有sourceをrootの `src` へ戻さない。
- 依存Modのjar、展開物、比較画像、一時解析scriptはrootの `.tmp/` に置いてGit管理外にする。
- `tools/textures/` は例外として正式な保守・再生成・検証toolをGit管理する。生成規則は本書とtoolの双方で維持する。
- JSONはBOMなしUTF-8で保存する。
- 既存リリースのregistry IDや名前空間を変更する場合は、ワールド互換性を最優先する。
- 本書は作業規約と実装仕様を兼ねる。数値、ID、条件、例外、優先順位は残し、作業履歴とdataから機械的に得られる重複一覧は載せない。
- READMEは利用者向けの名称、配布先、build入口に絞り、詳細仕様や作業履歴を重複掲載しない。
- ライセンスと第三者表示はrootの `LICENCE` 一つへ統合し、別のlicense/noticeファイルを作らない。
- 対象版の実JAR・公式ソースを確認し、client/server境界、optional依存なしのclass loading、無関係な既存差分を守る。
- release用Mod ID、resource namespace、Java package、class prefixは `tfcm` / `Tfcm` に統一し、旧namespaceとの互換aliasは持たない。

## 2. build構成

対応対象:

| Minecraft | Loader | TFC | JEI | Java | project |
| --- | --- | --- | --- | --- | --- |
| 1.20.1 | Forge 47.4.20 | 3.2.23 | 15.20.0.133 | 17 | `versions/mc1_20_1` |
| 1.21.1 | NeoForge 21.1.235 | 4.2.5 | 19.27.0.346 | 21 | `versions/mc1_21_1` |

- 全対象: `./gradlew build`
- 個別: `./gradlew :versions:mc1_20_1:build` / `./gradlew :versions:mc1_21_1:build`
- `build` と `collectArtifacts` はrelease jarをrootの `build/libs` に集約する。
- root `build.gradle` は集約用。共通Mod metadataはroot `gradle.properties`、対象別versionは各projectの `gradle.properties` に置く。
- Gradle wrapperは8.xを維持する。ForgeGradle 6はGradle 9非対応で、configuration cacheも無効とする。
- 各version projectは対象に合う `pack.mcmeta` とloader metadataを持つ。
- 共通dataはMinecraft 1.21レイアウトで保持する。1.20.1の `processResources` でpath、tag名前空間、recipe JSONを変換する。
- 1.20.1変換はTFC casting fluid、alloy metal reference、advanced shapedの `input_row`、`item_size` -> `item_sizes`、`#c` / `#neoforge` -> `#forge`、tuff ore tag除去を含む。
- 1.20.1では `tfcm/tfc/fluid_heat` からTFC metal manager JSONを生成する。
- 金属・鉱石・tool tier等の仕様値とloader非依存worldgen処理は `shared/src/main/java` に置く。各versionにはregistry holder、loader event、serializer、TFC API constructor等のadapterだけを置く。
- loader差分は各versionの `TfcmPlatform` とworldgen adapterへ閉じ込め、同一ロジックをversion別に複製しない。

## 3. 独自金属

対象金属:

`compressed_iron`, `platinum`, `naquadah`, `iridium`, `osmium`, `osmiridium`, `mithril`, `arcane`, `refined_glowstone`, `refined_obsidian`, `antimony`, `titanium`, `cobalt`, `lithium`, `aluminum`, `constantan`, `invar`, `electrum`, `lead`, `uranium`, `tungsten`, `solder`, `tungsten_steel`, `netherite`, `dawnstone`

常時生成する形状:

- `ingot`, `double_ingot`, `sheet`, `double_sheet`, `rod`
- `block`, `block_slab`, `block_stairs`
- 特殊: `metal/ingot/high_carbon_tungsten_steel`

基本ID:

- item: `tfcm:metal/<form>/<metal>`
- block: `tfcm:metal/block/<metal>`
- slab: `tfcm:metal/block/<metal>_slab`
- stairs: `tfcm:metal/block/<metal>_stairs`

`tfc_items` 導入時のみ生成する形状:

`foil`, `gear`, `heavy_sheet`, `nail`, `ring`, `rivet`, `screw`, `stamen`, `wire`

追加形状のIDも `tfcm:metal/<form>/<metal>` とする。

工具・防具対応金属:

- `invar`: wrought ironと同等
- `titanium`: 鍛冶tierと耐久値はsteel相当の4 / 3300、それ以外はblack steelと同等
- `tungsten_steel`: red / blue steelより上位。tool level 7、耐久8125、採掘速度13、攻撃補正10.5、enchantment 25とする。
- `netherite`: 工具・防具性能はred / blue steel相当。tool level 6、耐久6500、採掘速度12、攻撃補正9、enchantment 22とし、金属色、rarity、鍛冶tierはblack steel相当の`0x111111`、`RARE`、5とする。

上記4金属はTFC標準の工具、工具頭、shears、tuyere、fishing rod、shield、horse armor、防具、中間防具をすべて持つ。IDは `tfcm:metal/<tfc_item_type>/<metal>` とする。

## 4. 独自鉱石・Gem

品位あり鉱石:

`native_platinum`, `native_naquadah`, `native_iridium`, `native_osmium`, `rutile`, `cobaltite`, `spodumene`, `bauxite`, `galena`, `uraninite`, `mithril_matrix`, `stibnite`, `wolframite`

品位なし鉱石:

`fluorite`, `quartz`

ID規則:

- 品位ありitem: `tfcm:ore/{poor|normal|rich}_<ore>`
- 品位なしitem: `tfcm:ore/<ore>`
- 母岩内block: item IDに `/<tfc_rock>`、`/netherrack`、`/endstone` を付ける。全独自鉱石はTFC母岩版に加えてNether/End版を持つ。
- 地表サンプル: `tfcm:ore/small_<ore>`
- `small_fluorite` と `small_quartz` はgroundcover blockのみでblock itemを持たない。
- QuartzはTFCのRuby等と同じ非品位宝石鉱石として、全TFC母岩、Nether、Endの `tfcm:ore/quartz/<rock_or_stone>` blockと `tfcm:ore/small_quartz` groundcover blockを持つ。
- Quartz block modelは `tfcm:block/ore/quartz` overlayを参照する。overlay PNGは別途提供される正式素材を使用する。
- 全Nether鉱石blockはBeneathと同じ硬度3、爆発耐性10とする。End鉱石blockはVanilla End Stone相当の硬度3、爆発耐性9とする。

`tfcorewashing` 導入時のみ、全品位あり鉱石へ次の形状を追加する。

`pellet`, `briquet`, `chunks`, `rocky_chunks`, `dirty_dust`, `dirty_pile`, `powder`

IDは `tfcm:metal/<form>/<ore>` とする。

Gem関連:

- raw Quartz item/block: `tfcm:ore/quartz` / `tfcm:ore/quartz/<tfc_rock>`、tag `c:ores/quartz`
- Cut Quartz: `tfcm:gem/cut_quartz`、tag `c:gems/quartz`
- Quartzをsandpaperで研磨してCut Quartzにする。
- Quartz晶洞は `tfcm:mineral/{quartz_block,budding_quartz,quartz_cluster,large_quartz_bud,medium_quartz_bud,small_quartz_bud}` で構成する。外殻はTFC hardened basalt、中間層はraw quartzite、内層はbudding quartz : quartz block : raw quartzite = 2 : 5 : 1とする。
- Quartz晶洞はoverworldのvein配置へ追加し、Y -48から32に平均300chunkに1回生成する。Quartz Clusterは `tfcm:gem/cut_quartz` を2個落とす。
- Amethyst晶洞とCertus Quartz晶洞もQuartz晶洞と同じ外殻、中間層、内層比率、成長段階、配置範囲、頻度を使う。各block IDは `tfcm:mineral/<name>` とする。
- Amethyst Clusterは `tfc:gem/amethyst` を2個落とす。blockとbudのmodelはVanilla Amethyst textureを直接参照する。
- Certus Quartz Crystalは `tfcm:gem/certus_quartz`、tagは `c:gems/certus_quartz` とする。Certus Quartz Clusterは同itemを2個落とす。
- Fluorite Powder: `tfcm:powder/fluorite`
- Quartz Powder: `tfcm:powder/quartz`
- FluoriteまたはQuartzをquernで粉砕して対応するPowderを4個生成し、両方を `tfc:gem_powders` と `tfc:bowl_powders` に含める。

## 5. IE由来コンテンツ

次のコンテンツはTFC IE Crossoverを実行時依存にせず、TFCMの独自コンテンツとして常時登録する。

- 金属: `aluminum`, `constantan`, `electrum`, `lead`, `uranium`
- 鉱石: `bauxite` -> `aluminum`, `galena` -> `lead`, `uraninite` -> `uranium`
- Electrum alloy: Gold 40-60% + Silver 40-60%
- Constantan alloy: Copper 40-60% + Nickel 40-60%

鉱石block、small ore、鉱脈featureは必ず `tfcm` 名前空間を使う。TFC IE CrossoverのIDをconfigやloader dependencyへ追加しない。Crossover由来素材を使用してよいのはテクスチャ生成時だけである。

## 6. compat鉱石

TFC由来:

- `TfcmCompatOres.TFC_ORES` の鉱石へ `netherrack` / `endstone` blockを追加する。
- ID: `tfcm:ore/<ore_name>/{netherrack|endstone}`
- 一部の非品位鉱石pieceへ `small_<ore_piece_name>` groundcover blockを追加する。block itemは持たない。

Firmalife由来:

- 対象: `normal_chromite`, `poor_chromite`, `rich_chromite`
- `firmalife` 導入時のみ `netherrack` / `endstone` blockを追加する。
- ID: `tfcm:ore/<ore_name>/{netherrack|endstone}`

compat層はingot等の金属形状や専用加工recipeを追加しない。単体鉱石itemを持つのはTFCM独自鉱石だけである。

### 6.1 optional Mod連携

- 対象金属は `invar`, `titanium`, `tungsten_steel`, `netherite` とする。
- `tfc_metal_tools` は1.20.1/1.21.1のoptional dependency。導入時は `crossguard`, `pommel` を有効化し、標準工具の組立recipeを同Mod方式へ切り替える。
- `tfchotornot` は1.21.1のみoptional dependency。導入時は `tongs`, `tong_part` を有効化する。tongsは同Mod本来の `TongsItem` として生成し、`tfchotornot:tongs` tagへ含める。
- 1.21.1で両Modを導入した場合もtongsはHot or Not本来のrecipeを維持し、shearsのみTFC Metal Tools方式で組み立てる。
- Metal Toolsの互換recipe、item heat、tag、assetは `shared/src/main/resources`、Hot or Not固有分は `versions/mc1_21_1/src/main/resources` に置く。
- optional item IDはresource reload時の未登録参照を避けるため対応versionのregistryへ常設し、対象Mod未導入時はcreative tabとrecipeから隠す。IDは `tfcm:metal/<form>/<metal>` とする。

## 7. data・model規約

主要resource root:

- data: `shared/src/main/resources/data`
- assets: `shared/src/main/resources/assets`

金属recipe:

- `casting/ingot`
- `anvil/metal/{sheet,rod}`
- `welding/metal/{double_ingot,double_sheet}`
- `crafting/metal/block`
- `heating/metal/{ingot,double_ingot,sheet,double_sheet,rod,block}`
- 工具・防具対応金属: `anvil/metal/<part>`, `crafting/metal/<tool>`, `welding/metal/<armor_or_shears>`, `heating/metal/<tool_or_armor>`

`tfc_items` 条件付きrecipe:

- `anvil/more_items/{foil,gear,nail,ring,rivet,screw,stamen,wire}`
- `welding/more_items/heavy_sheet`
- `heating/more_items/{foil,gear,heavy_sheet,nail,ring,rivet,screw,stamen,wire}`

鉱石・Ore Washing recipe:

- `heating/ore/{poor,normal,rich,small}`
- `ore_washing/ores/*`
- `ore_washing/chunks/{quern,milling,crusher}`
- `ore_washing/rocky_chunks/splashing` と `ore_washing/dirt_dusts/splashing` は `create` 併用条件を持つ。
- `crafting/ore_washing/{pellet,briquet,dirty_dust,uncompress_pellet,uncompress_briquet,uncompress_dust}`
- `tfc/heating/ore_washing/{pellet,briquet,powder}`
- `tfcorewashing` 導入時は独自品位あり鉱石の `poor` / `normal` / `rich` 直接溶融を無効化する。`small` の直接溶融は維持する。

model pathはregistry IDと同じ階層を基本とする。

- item metal: `assets/tfcm/models/item/metal/<form>`
- 工具・防具modelもregistry IDと同じ `assets/tfcm/models/item/metal/<tfc_item_type>` に置く。
- metal block: `assets/tfcm/models/block/metal/block`
- ore item: `assets/tfcm/models/item/ore/<ore_or_grade>`
- ore block: `assets/tfcm/models/block/ore/<ore_or_grade>/<rock_or_stone>`
- groundcover: `assets/tfcm/models/block/ore/small_*`

## 8. tag・TFC属性

- 独自鉱石とcompat鉱石blockは `#c:ores` を介して `minecraft:mineable/pickaxe` に含める。
- これにより通常pickaxeとTFC Mining HammerのSledgehammerの対象にする。
- TFC標準のsize/weightを継承させるため、トップレベルcommon item tagにTFCMの金属別tagを含める。
- 金属block、slab、stairs、TFC More Items形状、Ore Washing形状には明示的なitem sizeを定義する。
- item sizeの正本は `shared/src/main/resources/data/tfc/tfc/item_size`。1.20.1 buildで `item_sizes` へ変換する。
- item heatは `shared/src/main/resources/data/tfcm/tfc/item_heat` に置く。
- 金属block、slab、stairsは個別heat定義を持ち、slab/stairsはitem IDを直接指定する。
- 品位あり鉱石は鉱石ごとに `small`, `poor`, `normal`, `rich` を一つのheat定義で扱う。
- Ore Washingは `pellet_briquet/<ore>.json` と `powder/<ore>.json` を使う。
- TFC propickはblock translation keyの末尾に `.prospected` を付けたkeyを表示する。
- 独自鉱石の全品位・全母岩blockと、TFC/Firmalife由来Nether/End blockへ `en_us.json` の `.prospected` を定義する。
- `.prospected` の値は元Modと一致させ、品位・母岩名を含めない。

## 9. 溶融fluid互換性

- 独自molten fluidのregistry IDは既存ワールド互換性のため `tfc:metal/<metal>` を維持する。
- 1.20.1ではcreative tabが空bucket stackを受け取らないよう、`tfcm:bucket/metal/<metal>` itemと `tfc:fluid/metal/<metal>` blockを登録する。

## 10. カスタム鉱脈config

- 詳細仕様は `config_doc.md` を正本とする。
- bundled configは `shared/src/main/resources/tfcm/overworld.yaml`。
- 出力は `blocks` listで定義し、各要素に `block` と省略可能な `weight`（既定値1）を指定する。
- `tier` は品位名から整数weightへのmappingとする。
- 既存config互換のため、parserは旧 `block` とlist風 `tier` も受け入れる。
- YAML parser本体は `shared/src/main/java`、TFC API差分は各versionの `TfcmVeinPlatform` に限定する。
- bauxite、galena、uraniniteの鉱脈はCrossoverではなく `tfcm:ore/*` と `tfcm:ore/small_*` を生成する。
- `overworld.yaml`、`nether.yaml`、`end.yaml` はルート `geodes` で晶洞も定義できる。晶洞定義はtype、Y範囲、rarity、outer、middle、weightedなinner/filling/inner_placementsを持つ。
- custom worldgen有効時はdata pack由来のTFCM晶洞を除外し、configの `geodes` に定義された晶洞だけを対象ディメンションへ追加する。`geodes` がない、空、または有効な定義がない場合は晶洞を生成しない。

## 11. テクスチャ生成

### 11.1 共通方式: 分位パレット転写

金属、More Items、Ore Washingの再着色はすべて `tools/textures/color_transfer.py` の同一方式を使う。平均色乗算、通常版/高輝度版の分類、事前の淡色化は使用しない。

1. 元素材の非透過pixelを相対輝度順に並べ、RGB列 `S` を作る。
2. 形状ベースの非透過pixelの相対輝度列 `B` を作る。
3. 各base pixel `x` の輝度 `L(x)` について、同輝度pixelの中央順位を使って分位 `p` を求める。
4. `u = p * (|S| - 1)` とし、`S[floor(u)]` と `S[ceil(u)]` のRGBを線形補間する。
5. alphaとpixel位置は形状ベースのものを維持する。

式:

```text
lum(rgb) = (0.2126 R + 0.7152 G + 0.0722 B) / 255
p = (lower_bound(B, L(x)) + upper_bound(B, L(x)) - 1) / (2 * (|B| - 1))
out.rgb = lerp(S[floor(u)].rgb, S[ceil(u)].rgb, fract(u))
out.a = x.a
```

この方式はTFC側の形状と陰影位置を保ちながら、元素材のshadow、mid、highlight、彩度、色相変化の分布を移す。元素材と生成物の輝度分位・平均彩度を比較し、分布が大きく崩れていないことを確認する。

plated blockとingot pileは面積の大きい模様でコントラストが過剰にならないよう、`p' = 0.5 + (p - 0.5) * 0.55` として元素材パレットの中央55%を使う。item、More Items、Ore Washingにはこの圧縮を適用しない。

### 11.2 金属・More Items

形状ベース:

- `block`, `smooth`, `ingot`, `double_ingot`, `sheet`, `double_sheet`, `rod` は、卑金属、貴金属、鉄系・高度合金などの属性に応じて近いTFC金属を使う。
- `foil`, `gear`, `heavy_sheet`, `nail`, `ring`, `rivet`, `screw`, `stamen`, `wire` は同じ分類を使い、TFC More Itemsに同名金属がある場合はその形状を優先する。
- 金属ごとの割り当ては `docs/metals.md` の「テクスチャ形状ベース」を正本とし、`tools/textures/regenerate_metals.py` のmappingと一致させる。
- 元Modから直接取得して無加工で使うtextureは再生成対象に含めない。`high_carbon_tungsten_steel` はTFC `high_carbon_steel` の形状へ元素材のパレットを転写する。

色・パレット元:

- Immersive Engineering: `aluminum`, `constantan`, `electrum`, `lead`, `uranium`
- Iron's Spells 'n Spellbooks: `mithril`, `arcane`
- PneumaticCraft: Repressurized: `compressed_iron`
- Mekanism Extras: `naquadah`
- Mekanism: `osmium`, `refined_glowstone`, `refined_obsidian`
- Thermal Foundation: `invar`
- TFC Metallum U: `antimony`, `cobalt`, `high_carbon_tungsten_steel`, `iridium`, `osmiridium`, `platinum`, `solder`, `titanium`, `tungsten`, `tungsten_steel`
- TFC Metallum 1.12.2: `lithium`
- Minecraft: `netherite`
- Embers Re-Ignited: `dawnstone`

Metallumが他Modの素材を移植している場合は、Metallum版ではなく移植元Mod本体のingotを優先する。全金属にsourceを明示し、現在の生成済みtextureをsourceへフォールバックさせない。

生成script:

```bash
python3 tools/textures/regenerate_metals.py
```

- Python 3とPillowが必要。
- 依存jarとIron's Spellsの抽出画像は `.tmp` に置き、リポジトリへコピーしない。
- Ore Washingの形状ベースと生成規則は金属形状のmappingから独立させ、変更しない。
- ingot pileと金床が共通参照する `assets/tfc/textures/block/metal/smooth/<metal>.png` も同時生成する。
- InvarのalloyはWrought Iron 60-70% + Nickel 30-40%。
- Dawnstoneは工具とalloy recipeを追加しない。
- `invar`、`titanium`、`tungsten_steel`、`netherite` のtool tierはTFC本体を直接参照せず、各versionの `TfcmTiers` に固定値で定義する。InvarはWrought Iron相当、Titaniumは耐久値3300（Steel相当）以外をBlack Steel相当とし、鍛冶tierはBlack Steelより1段低い4とする。Netheriteは工具・防具性能をRed / Blue Steel相当、鍛冶tierと金属色をBlack Steel相当とする。
- 対象TFC versionがsheet pileに対応しない限り、sheet pile assetは追加しない。
- 工具・防具の形状元はTFC 1.21.1のtexture/modelを正本とする。工具とjavelinは `invar` がwrought iron、`titanium` が通常steel、`tungsten_steel` と `netherite` がred steelを使う。shield、防具、中間防具、horse armor、防具layerは `invar` がwrought iron、`titanium` がblack steel、`tungsten_steel` が通常steel、`netherite` がblack bronzeを使う。Netheriteのこれらのmodel JSONもblack bronzeを正本とする。
- 完成工具、shears、javelin projectileは、`invar` / `titanium` ではwrought ironと通常steel、`tungsten_steel` / `netherite` ではred steelとblue steelの同色pixelを固定材maskとして扱う。木柄・紐・支点などは再着色せず、金属部分だけにパレット転写を適用する。
- horse armorはTFC 1.21.1の全金属texture間で同色のpixelを固定材maskとし、サドル部分を再着色しない。
- 完成knife/javelinとknife blade/javelin headのitem textureはTFC 1.21.1の形状を生成時に水平反転し、両versionで同じ共有textureを使う。version別の追加反転は行わない。javelin projectileは両versionとも反転しない。
- optional連携のcrossguard/pommelはTFC Metal Toolsの形状を使ってshared assetへ、tongs/tong partはTFC Hot or Notの形状を使って1.21.1固有assetへ生成する。いずれも同じ金属パレット転写を使い、tongsとtong partは元Mod内の比較金属間で同色のpixelを固定材として保持する。

### 11.3 Ore Washing

- 色・パレット元は各鉱石の `assets/tfcm/textures/item/ore/normal_<ore>.png`。
- `chunks`, `rocky_chunks`, `dirty_dust`, `dirty_pile` はtfcorewashingのgraphite形状を使う。
- `pellet`, `briquet` はgraphite形状がないためchromium形状をmaskとして使う。
- `powder` はTFC `item/powder/graphite` の形状を使う。
- `rocky_chunks` は岩部分を転写対象から除外し、item modelの `layer1` に固定色 `_rocky_overlay.png` を重ねる。
- `mithril_matrix` は正式素材が提供されるまで現在の仮鉱石textureをパレット元とする。

生成script:

```bash
python3 tools/textures/regenerate_ore_washing.py
```

### 11.4 鉱石・Gem補助texture

- `bauxite`, `galena`, `uraninite` の鉱石item/block overlayとCut QuartzはTFC IE Crossover由来素材を使用する。
- Cut QuartzはCrossoverのQuartz Shardをそのまま使う。
- Quartz晶洞のblock/bud textureとmodel、生成形状はTFC IE Crossover 1.21.xを正本とする。
- Certus Quartzのitem/block/bud textureはApplied Energistics 2 1.21.1を正本とする。非成長blockは枠・中心紋・芽模様のない `quartz_block_empty`、成長blockは `flawless_budding_quartz` を使い、加工済み `quartz_block` は使わない。必要な第三者表示はルート `LICENCE` 内へ統合する。
- raw QuartzはTFC `item/ore/amethyst` 形状へCut Quartzのパレットを転写する。
- Fluorite PowderとQuartz PowderはTFC `item/powder/amethyst` 形状へ各鉱石itemのパレットを転写する。
- `cobaltite` と `spodumene` の鉱石素材は、それぞれTFC Metallum UとTFC Metallum 1.12.2由来とする。

生成script:

```bash
python3 tools/textures/regenerate_misc.py
```

## 12. 主要コード

- 共通名一覧: sharedの `TfcmContentNames.java`
- 金属・鉱石仕様: sharedの `TfcmMetalSpec.java`, `TfcmOre.java`
- item/block登録: 各versionの `TfcmMetal.java`, `TfcmItems.java`, `TfcmBlocks.java`
- compat鉱石: sharedの `TfcmCompatOres.java`
- Mod entry: 各versionの `TfcmMod.java`
- 鉱脈parser: sharedの `worldgen/TfcmVeinsYamlParser.java`
- 鉱脈platform: 各versionの `TfcmVeinPlatform.java`
