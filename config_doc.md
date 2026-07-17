# カスタムworldgen設定仕様

TFCMは、Minecraftのconfigディレクトリにある次のファイルからカスタム鉱脈と晶洞を読み込む。

- `config/tfcm/overworld.yaml`
- `config/tfcm/nether.yaml`
- `config/tfcm/end.yaml`

ファイルが存在しない場合はMod同梱の初期設定がコピーされる。既存ファイルは自動更新・上書きされない。
旧Overworld設定の `config/tfcm/veins.yaml` も、`overworld.yaml` が存在しない場合に限り読み込まれる。

## 有効化

common configの次の値を有効にする。

```toml
[worldgen]
enableCustomVeinGeneration = true
```

設定変更後はゲームを完全に再起動する。

- Overworldでは、TFCの通常鉱脈を `overworld.yaml` の鉱脈で置き換える。砂利や岩脈など、同じfeature tag内の鉱脈・TFCM晶洞以外の要素は維持される。
- custom worldgen有効時は、data packで定義されたTFCM標準晶洞を生成しない。各ファイルの `geodes` に定義した晶洞だけを生成する。
- NetherとEndでは、それぞれのディメンションのbiomeへ設定した鉱脈と晶洞を追加する。
- 設定が空、または有効な鉱脈が1つもない場合、OverworldはTFCの標準鉱脈へフォールバックする。

## 基本構造

ルートキーは `veins` とし、その下を鉱脈IDのリストにする。
`veins` と後述の `geodes` の記述順序は問わない。空の節は `veins: []` / `geodes: []` とも記述できる。

```yaml
veins:
  - tfc:vein/surface_native_copper:
      blocks:
        - block: tfc:ore/{tier}_native_copper/{rock}
          weight: 0.9
        - block: tfc:ore/{tier}_native_gold/{rock}
          weight: 0.1
      type: tfc:cluster_vein
      ymin: 40
      ymax: 130
      rarity: 24
      density: 0.25
      size: 20
      random_name: surface_native_copper
      rocks:
        - rhyolite
        - basalt
        - andesite
        - dacite
      tier:
        poor: 70
        normal: 25
        rich: 5
      indicator:
        block: tfc:ore/small_native_copper
        rarity: 14
        depth: 35
        underground_rarity: 1
        underground_count: 0
```

鉱脈IDの名前空間は入力元の識別や依存Mod判定に使われる。実行時に登録するfeature IDは、パスを維持した `tfcm:<path>` へ変換される。同じ変換後IDを持つ鉱脈が複数ある場合、2件目以降は読み飛ばされる。

## 鉱石ブロック

`blocks` は1件以上のリストで、各要素は次の値を持つ。

| キー | 必須 | 既定値 | 内容 |
| --- | --- | --- | --- |
| `block` | はい | なし | 生成するブロックIDまたはテンプレート |
| `weight` | いいえ | `1` | このブロック候補の相対weight。有限かつ `0` より大きい数値 |

`weight` は相対値なので、合計を `1` や `100` に揃える必要はない。例えば `0.9` と `0.1` は90対10、`9` と `1` も同じ比率になる。

後方互換のため、旧形式の `block: <template>` もweight `1` の単一候補として読み込める。ただし、同梱設定と新規設定では `blocks` を使用する。1つの鉱脈で `blocks` と旧 `block` を併記してはならない。

### テンプレート

`block` では次のプレースホルダーを使用できる。

- `{rock}`: 現在処理している `rocks` の岩石名へ置換する。
- `{tier}`: 現在処理している `tier` のキーへ置換する。

`blocks` と `tier` を併用した場合、両方のweightを乗算する。上の例では次のweightになる。

| ブロック | 計算 | 最終weight |
| --- | --- | ---: |
| poor native copper | `0.9 * 70` | `63` |
| normal native copper | `0.9 * 25` | `22.5` |
| rich native copper | `0.9 * 5` | `4.5` |
| poor native gold | `0.1 * 70` | `7` |
| normal native gold | `0.1 * 25` | `2.5` |
| rich native gold | `0.1 * 5` | `0.5` |

## 岩石と品位

### `rocks`

置換対象となる岩石のリスト。

```yaml
rocks:
  - granite
  - diorite
  - gabbro
```

- 名前空間のない値は `tfc:rock/raw/<rock>` として解決する。
- `netherrack` は `minecraft:netherrack`、`endstone` と `end_stone` は `minecraft:end_stone` として解決する。
- 完全なブロックIDも指定できる。`{rock}` には、そのIDの最後のパス要素が入る。
- `overworld.yaml` では必須。
- `nether.yaml` で省略した場合は `netherrack`、`end.yaml` で省略した場合は `endstone` を使用する。

### `tier`

`{tier}` の候補と相対weightをマッピングで指定する。

```yaml
tier:
  poor: 70
  normal: 25
  rich: 5
```

キーは `{tier}` に入る文字列、値は整数weightである。`0` 以下の候補は生成対象にならない。いずれかの `blocks[].block` が `{tier}` を含む場合、空でない `tier` が必要になる。

旧設定との互換性のため、`- poor: 70` のようなリスト風表記も読み込めるが、新規設定ではマッピング形式を使用する。

## 共通項目

| キー | 必須 | 既定値 | 内容 |
| --- | --- | --- | --- |
| `blocks` | はい | なし | 生成ブロック候補のリスト。旧 `block` も互換入力として使用可能 |
| `type` | はい | なし | 鉱脈feature type |
| `ymin` | はい | なし | 生成高度の下限。`min_y` も使用可能 |
| `ymax` | はい | なし | 生成高度の上限。`max_y` も使用可能 |
| `rarity` | はい | なし | 鉱脈の出現間隔を制御する値 |
| `density` | はい | なし | 鉱脈範囲内でブロックを置換する密度 |
| `size` | type依存 | なし | cluster/discの水平規模。pipeでは省略可能 |
| `rocks` | dimension依存 | なし | 置換対象ブロック。Overworldでは必須 |
| `tier` | いいえ | 空 | `{tier}` 候補とweightのマッピング |
| `random_name` | いいえ | 鉱脈IDの最後のパス要素 | 決定的な乱数seedの生成名 |
| `seed` | いいえ | `random_name` から生成 | 乱数seedをlong整数で直接指定 |
| `project` | いいえ | `false` | TFCのsurface projectionを有効化 |
| `project_offset` | いいえ | `false` | projection時のoffsetを有効化 |
| `near_lava` | いいえ | `false` | 溶岩付近に限定するTFC条件を有効化 |
| `indicator` | いいえ | なし | 地表・地下indicatorの設定 |

真偽値は `true` / `false` のほか、`1` / `0`、`yes` / `no` を使用できる。

## Feature type

使用できる `type` は次の4種類。

### Cluster vein

```yaml
type: tfc:cluster_vein
size: 20
```

`size` が必須。

### Disc vein

```yaml
type: tfc:disc_vein
size: 40
height: 2
```

`tfc:disc_vein` と `tfc:kaolin_disc_vein` を使用できる。`size` が必須で、`height` の既定値は `0`。

### Pipe vein

```yaml
type: tfc:pipe_vein
height: 60
radius: 5
min_skew: 5
max_skew: 13
min_slant: 0
max_slant: 2
sign: 0
```

pipeでは `height` を指定する。`radius`、`min_skew`、`max_skew`、`min_slant`、`max_slant` を省略した場合は `0`、`sign` の既定値は `1.0`。各項目は `pipe_height`、`pipe_radius`、`pipe_min_skew`、`pipe_max_skew`、`pipe_min_slant`、`pipe_max_slant` の別名でも指定できる。

## Indicator

`indicator` を指定すると、鉱脈に対応する地表・地下indicatorを生成する。

```yaml
indicator:
  block: tfc:ore/small_native_copper
  rarity: 14
  depth: 35
  underground_rarity: 1
  underground_count: 0
```

| キー | 必須 | 既定値 | 内容 |
| --- | --- | --- | --- |
| `block` | はい | なし | indicatorとして配置するブロックID |
| `rarity` | いいえ | `0` | 地表indicatorの頻度 |
| `depth` | いいえ | `0` | 地表indicatorが参照する深さ |
| `underground_rarity` | いいえ | `0` | 地下indicatorの頻度 |
| `underground_count` | いいえ | `0` | 地下indicatorの配置数 |

`indicator.block` がない場合、その `indicator` セクションは無効として扱われる。

## 晶洞

晶洞は各ファイルのルートキー `geodes` に定義する。省略または空にしたディメンションでは、custom worldgen有効時にTFCM晶洞を生成しない。

```yaml
geodes:
  - tfcm:quartz_geode:
      type: tfcm:quartz_geode
      ymin: -48
      ymax: 32
      rarity: 300
      outer: tfc:rock/hardened/basalt
      middle: tfc:rock/raw/quartzite
      inner:
        - block: tfcm:mineral/budding_quartz
          weight: 2
        - block: tfcm:mineral/quartz_block
          weight: 5
        - block: tfc:rock/raw/quartzite
      filling:
        - block: minecraft:air
      inner_placements:
        - block: tfcm:mineral/quartz_cluster
          weight: 1
        - block: tfcm:mineral/large_quartz_bud
          weight: 3
        - block: tfcm:mineral/medium_quartz_bud
          weight: 5
        - block: tfcm:mineral/small_quartz_bud
          weight: 8
```

| キー | 必須 | 既定値 | 内容 |
| --- | --- | --- | --- |
| `type` | はい | なし | 晶洞feature type。現在は `tfcm:quartz_geode` |
| `ymin` | はい | なし | 配置高度の下限。`min_y` も使用可能 |
| `ymax` | はい | なし | 配置高度の上限。`max_y` も使用可能 |
| `rarity` | はい | なし | 平均何chunkに1回配置を試行するか。`0` より大きい整数 |
| `outer` | はい | なし | 外殻のブロックID |
| `middle` | はい | なし | 中間層のブロックID |
| `inner` | はい | なし | 内層のブロック候補 |
| `filling` | はい | なし | 晶洞内部を満たすブロック候補。空洞は `minecraft:air` |
| `inner_placements` | はい | なし | 内壁へ配置するcluster・bud等の候補 |

`inner`、`filling`、`inner_placements` は1件以上のリストで、各要素は `block` と省略可能な整数 `weight` を持つ。`weight` の既定値は `1` で、`0` より大きい必要がある。相対weightなので合計を揃える必要はない。

晶洞IDは重複検出とログ表示に使われる。ブロックIDは完全なresource locationで指定し、鉱脈の `{rock}` / `{tier}` テンプレートは使用しない。参照先Modが未導入、type・ブロックが未登録、必須値や数値が不正な定義は読み飛ばされる。

## 依存Modとエラー処理

- `blocks[].block` または `indicator.block` が参照する名前空間のModがロードされていない場合、その鉱脈全体を読み飛ばす。
- 晶洞のtypeまたはいずれかのブロックが参照する名前空間のModがロードされていない場合、その晶洞全体を読み飛ばす。
- 存在しない岩石、出力ブロック、indicatorブロックは警告を記録し、その候補を生成しない。
- 必須値の欠落、不正な数値、未対応typeを含む鉱脈は警告を記録して読み飛ばす。他の有効な鉱脈は引き続き読み込まれる。
- `#` 以降はコメントとして扱われる。インデントにはスペースを使用する。
