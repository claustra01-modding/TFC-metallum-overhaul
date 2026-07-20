# 金属仕様

この文書は、TFC Metallum (`tfcm`) が追加する金属のゲーム内設定と、レシピを保守するときの基準をまとめたものです。

## 一次情報

- 金属ID、レアリティ、代表色、鍛造tier、tool/anvilの有無: `shared/src/main/java/net/claustra01/tfcm/TfcmMetalSpec.java`
- ツール性能: `shared/src/main/java/net/claustra01/tfcm/TfcmToolTierSpec.java`
- バージョン固有の金属・防具実装: `versions/mc*/src/main/java/net/claustra01/tfcm/`
- 金床加工・溶接レシピ: `shared/src/main/resources/data/tfcm/recipe/{anvil,welding}/`
- 融解温度: `shared/src/main/resources/data/tfcm/recipe/heating/metal/ingot/`
- 合金比率: `shared/src/main/resources/data/tfcm/recipe/alloy/`
- テクスチャの生成規則と素材元: `AGENTS.md`

## Recipe tier規則

- `tfc:anvil` は、結果金属の鍛造tierと同じ値にする。
- `welding/metal/double_ingot/` だけは、結果金属の鍛造tierより1低い値にする。
- それ以外の `tfc:welding` は、結果金属の鍛造tierと同じ値にする。
- tierは `TfcmMetalSpec.forgingTier()` を基準とし、個別JSONに独自のtierを設けない。

## 金属一覧

`double ingot` はdouble ingot溶接に必要なtierです。温度はingotの融解温度（℃）です。

| ID | Rarity | 代表色 | 鍛造 | double ingot | 温度 | Tool/Anvil |
| --- | --- | --- | ---: | ---: | ---: | --- |
| `compressed_iron` | uncommon | `#6F6C6B` | 3 | 2 | 1535 | - |
| `platinum` | rare | `#627C8B` | 3 | 2 | 1730 | - |
| `naquadah` | rare | `#4D5742` | 7 | 6 | 1730 | - |
| `iridium` | uncommon | `#ADC4CE` | 3 | 2 | 1535 | - |
| `osmium` | uncommon | `#A5B4CA` | 3 | 2 | 1535 | - |
| `osmiridium` | uncommon | `#718383` | 3 | 2 | 1500 | - |
| `mithril` | common | `#8DB4BC` | 2 | 1 | 940 | - |
| `arcane` | rare | `#888CCC` | 2 | 1 | 940 | - |
| `refined_glowstone` | uncommon | `#E2B446` | 2 | 1 | 940 | - |
| `refined_obsidian` | rare | `#473752` | 2 | 1 | 940 | - |
| `antimony` | common | `#B4AFBD` | 1 | 0 | 630 | - |
| `titanium` | uncommon | `#898B93` | 5 | 4 | 1700 | Yes |
| `cobalt` | uncommon | `#175AB3` | 3 | 2 | 1700 | - |
| `lithium` | common | `#C5CBD1` | 3 | 2 | 181 | - |
| `aluminum` | common | `#929799` | 3 | 2 | 1740 | - |
| `constantan` | common | `#A45B49` | 1 | 0 | 1250 | - |
| `invar` | common | `#869A97` | 3 | 2 | 1250 | Yes |
| `electrum` | uncommon | `#A67F3A` | 1 | 0 | 1000 | - |
| `lead` | common | `#393D4A` | 1 | 0 | 330 | - |
| `uranium` | uncommon | `#4E5946` | 3 | 2 | 1130 | - |
| `tungsten` | epic | `#585F6B` | 3 | 2 | 1535 | - |
| `solder` | uncommon | `#888888` | 3 | 2 | 400 | - |
| `tungsten_steel` | epic | `#2F353E` | 7 | 6 | 1535 | Yes |
| `netherite` | rare | `#111111` | 6 | 5 | 1535 | Yes |
| `dawnstone` | uncommon | `#B18143` | 1 | 0 | 1000 | - |

## Tool性能

| ID | Level | Durability | Speed | Attack bonus | Enchant | Repair metal |
| --- | ---: | ---: | ---: | ---: | ---: | --- |
| `invar` | 3 | 2200 | 8 | 4.75 | 12 | `invar` |
| `titanium` | 5 | 3300 | 11 | 7 | 17 | `titanium` |
| `netherite` | 6 | 6500 | 12 | 9 | 22 | `netherite` |
| `tungsten_steel` | 7 | 8125 | 13 | 10.5 | 25 | `tungsten_steel` |

## テクスチャ形状ベース

色と輝度分布は各元素材のingotから取得し、ここで指定する金属は形状と陰影位置のベースにだけ使います。標準形状はTFC、More Items形状はTFC More Itemsのtextureです。

| ID | 標準形状・block・ingot pile・anvil | More Items形状 | 分類・対応 |
| --- | --- | --- | --- |
| `compressed_iron` | wrought iron | wrought iron | 加工鉄 |
| `platinum` | silver | silver | 貴金属 |
| `naquadah` | black steel | black steel | 高度・異種合金 |
| `iridium` | silver | silver | 白金族・貴金属 |
| `osmium` | nickel | nickel | 白金族・遷移金属 |
| `osmiridium` | sterling silver | sterling silver | 白金族合金 |
| `mithril` | silver | silver | 魔法系貴金属 |
| `arcane` | sterling silver | sterling silver | 魔法系貴金属合金 |
| `refined_glowstone` | gold | gold | 発光性貴金属 |
| `refined_obsidian` | black steel | black steel | 高度合金 |
| `antimony` | bismuth | bismuth | 卑金属・半金属 |
| `titanium` | steel | stainless steel | 高度構造金属 |
| `cobalt` | nickel | nickel | 遷移金属 |
| `lithium` | zinc | zinc | 軽卑金属 |
| `aluminum` | zinc | aluminum | 軽卑金属 |
| `constantan` | brass | constantan | 銅系合金 |
| `invar` | steel | steel | 鉄ニッケル合金 |
| `electrum` | rose gold | electrum | 金銀系貴金属合金 |
| `lead` | bismuth | lead | 重卑金属 |
| `uranium` | nickel | uranium | 重遷移金属 |
| `tungsten` | steel | steel | 高融点構造金属 |
| `solder` | tin | tin | 低融点合金 |
| `tungsten_steel` | black steel | black steel | 高度鉄系合金 |
| `high_carbon_tungsten_steel` | high carbon steel | - | 高炭素鉄系合金 |
| `netherite` | black steel | black steel | 高度合金 |
| `dawnstone` | bronze | bronze | 銅系合金 |

`high_carbon_tungsten_steel` はingotだけを生成します。Ore Washingは鉱石textureをパレット元、graphite/chromiumを形状元とする従来ルールを維持します。

防具はInvarがwrought iron、Titaniumがblack steel、Netheriteがred steel相当です。Tungsten steelはred steelを基準に、耐久1.25倍、防御+1、toughness+1、knockback resistance+0.05、enchantability 25とします。1.21.1では同じ意図の値を `TfcmArmorMaterials` に静的に記載しています。

## 合金

| Result | Contents |
| --- | --- |
| `constantan` | copper 40-60%, nickel 40-60% |
| `electrum` | gold 40-60%, silver 40-60% |
| `invar` | wrought iron 60-70%, nickel 30-40% |
| `osmiridium` | osmium 60-80%, iridium 20-40% |
| `solder` | antimony 40-60%, bismuth 20-40%, tin 20-40% |

NaquadahとDawnstoneには、このmod独自のalloying recipeを追加しません。

## 金床

金床を持つ金属はInvar (tier 3)、Titanium (tier 5)、Netherite (tier 6)、Tungsten steel (tier 7)です。各金床はdouble ingot 7個でクラフトし、融解すると1400 mBへ戻ります。BlockItemのrarityは同じ金属のingotと共通です。

1.20.1では追加金床専用の `TfcmBlockEntities.ANVIL` を使用します。1.21.1では `BlockEntityTypeAddBlocksEvent` でTFCのanvil block entityへ追加金床を登録します。金床を増やす場合は、ブロック、BlockItem、block entity対象、タグ、モデル、loot table、crafting/heating recipeを両バージョンで確認してください。
