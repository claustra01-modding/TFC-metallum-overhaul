# OreGen

Worldgen summary for added ore-related `configured_feature` + `placed_feature` JSONs under `src/main/resources/data/**/worldgen/**`.

Notes:
- Values are copied as-is from JSON.
- `Placement` is only listed when the placed feature has non-empty `placement`.
- `Host` lists `replace` targets (typically TFC raw rock blocks).

## Overview

# Vein Type
- (S): surface vein
- (N): normal vein
- (R): rich vein
- (D): deep vein

# Vein Shape
- (cluster): cluster
- 

### Generated Ores
- [P]: poor ores
- [M]: normal ores
- [R]: rich ores
- [A]: all (poor/normal/rich) ores

### Params
- Y: depth
- R: rarity
- D: density
- S: size

// ここに表を追加

## Amethyst (tfc)

- Feature: `tfc:vein/amethyst`
- Gen: `tfc:disc_vein` (`Y=40..60 rarity=25 density=0.2 size=8 height=4`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, gneiss, limestone, marble, phyllite, quartzite, schist, shale, slate, tuff`
- Output: `tfc:ore/amethyst/<host rock> (chalk, chert, claystone, conglomerate, dolomite, gneiss, limestone, marble, phyllite, quartzite, schist, shale, slate, tuff)`

## Bauxite (tfcm)

### Surface

- Feature: `tfcm:vein/surface_bauxite`
- Gen: `tfc:cluster_vein` (`Y=48..100 rarity=40 density=0.3 size=15`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, gneiss, limestone, marble, phyllite, quartzite, schist, shale, slate, tuff`
- Output: `tfcm:ore/{poor,normal,rich}_bauxite/<host rock> (chalk, chert, claystone, conglomerate, dolomite, gneiss, limestone, marble, phyllite, quartzite, schist, shale, slate, tuff)` (weights poor/normal/rich=70/25/5)
- Indicator: `rarity=12 depth=35 underground_rarity=1 underground_count=0 blocks=tfcm:ore/small_bauxite`

### Normal

- Feature: `tfcm:vein/normal_bauxite`
- Gen: `tfc:cluster_vein` (`Y=-64..32 rarity=60 density=0.6 size=25`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, gneiss, limestone, marble, phyllite, quartzite, schist, shale, slate, tuff`
- Output: `tfcm:ore/{poor,normal,rich}_bauxite/<host rock> (chalk, chert, claystone, conglomerate, dolomite, gneiss, limestone, marble, phyllite, quartzite, schist, shale, slate, tuff)` (weights poor/normal/rich=15/25/60)
- Indicator: `rarity=0 depth=35 underground_rarity=1 underground_count=4 blocks=tfcm:ore/small_bauxite`

## Bismuthinite (tfc)

### Surface

- Feature: `tfc:vein/surface_bismuthinite`
- Gen: `tfc:cluster_vein` (`Y=40..130 rarity=32 density=0.3 size=20`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff`
- Output: `tfc:ore/{poor,normal,rich}_bismuthinite/<host rock> (chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff)` (weights poor/normal/rich=70/25/5)
- Indicator: `rarity=14 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_bismuthinite`

### Normal

- Feature: `tfc:vein/normal_bismuthinite`
- Gen: `tfc:cluster_vein` (`Y=-80..20 rarity=45 density=0.6 size=40`)
- Host: `diorite, gabbro, granite`
- Output: `tfc:ore/{poor,normal,rich}_bismuthinite/<host rock> (diorite, gabbro, granite)` (weights poor/normal/rich=15/25/60)
- Indicator: `rarity=0 depth=35 underground_rarity=1 underground_count=4 blocks=tfc:ore/small_bismuthinite`

## Bituminous Coal (tfc)

- Feature: `tfc:vein/bituminous_coal`
- Gen: `tfc:disc_vein` (`Y=-35..-12 rarity=210 density=0.9 size=50 height=3 project=true project_offset=true`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff`
- Output: `tfc:bituminous_coal`

## Borax (tfc)

- Feature: `tfc:vein/borax`
- Gen: `tfc:disc_vein` (`Y=40..100 rarity=40 density=0.2 size=23 height=3`)
- Host: `claystone, limestone, shale`
- Output: `tfc:ore/borax/<host rock> (claystone, limestone, shale)`

## Cassiterite (tfc)

### Surface

- Feature: `tfc:vein/surface_cassiterite`
- Gen: `tfc:cluster_vein` (`Y=80..180 rarity=5 density=0.4 size=15`)
- Host: `diorite, gabbro, granite`
- Output: `tfc:ore/{poor,normal,rich}_cassiterite/<host rock> (diorite, gabbro, granite)` (weights poor/normal/rich=35/40/25) + tfc:deposit/cassiterite/<host rock>=10
- Indicator: `rarity=12 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_cassiterite`

## Chromite (firmalife)

### Normal

- Feature: `firmalife:vein/normal_chromite`
- Gen: `tfc:cluster_vein` (`Y=40..130 rarity=50 density=0.25 size=8`)
- Host: `andesite, basalt, dacite, gneiss, marble, phyllite, quartzite, rhyolite, schist, slate`
- Output: `firmalife:ore/{poor,normal,rich}_chromite/<host rock> (andesite, basalt, dacite, gneiss, marble, phyllite, quartzite, rhyolite, schist, slate)` (weights poor/normal/rich=70/25/5)
- Indicator: `rarity=14 depth=35 underground_rarity=1 underground_count=0 blocks=firmalife:ore/small_chromite`

### Deep

- Feature: `firmalife:vein/deep_chromite`
- Gen: `tfc:cluster_vein` (`Y=-80..20 rarity=90 density=0.6 size=15`)
- Host: `diorite, gabbro, gneiss, granite, marble, phyllite, quartzite, schist, slate`
- Output: `firmalife:ore/{poor,normal,rich}_chromite/<host rock> (diorite, gabbro, gneiss, granite, marble, phyllite, quartzite, schist, slate)` (weights poor/normal/rich=15/25/60)
- Indicator: `rarity=0 depth=35 underground_rarity=1 underground_count=4 blocks=firmalife:ore/small_chromite`

## Cinnabar (tfc)

- Feature: `tfc:vein/cinnabar`
- Gen: `tfc:cluster_vein` (`Y=-70..10 rarity=14 density=0.6 size=18`)
- Host: `gneiss, phyllite, quartzite, schist`
- Output: `tfc:ore/cinnabar/<host rock> (gneiss, phyllite, quartzite, schist)`

## Cryolite (tfc)

- Feature: `tfc:vein/cryolite`
- Gen: `tfc:cluster_vein` (`Y=-70..-10 rarity=16 density=0.7 size=18`)
- Host: `diorite, granite`
- Output: `tfc:ore/cryolite/<host rock> (diorite, granite)`

## Diamond (tfc)

- Feature: `tfc:vein/diamond`
- Gen: `tfc:pipe_vein` (`Y=-64..100 rarity=30 density=0.15 height=60 radius=5 skew=5..13 slant=0..2 sign=0`)
- Host: `gabbro`
- Output: `tfc:ore/diamond/gabbro`

## Dike (tfc)

### Gabbro

- Feature: `tfc:vein/gabbro_dike`
- Gen: `tfc:pipe_vein` (`Y=-64..180 rarity=300 density=0.98 height=150 radius=18 skew=7..20 slant=2..5 sign=0`)
- Host: `raw: 21 rocks; gravel: 21 rocks; hardened: 21 rocks`
- Output: `tfc:rock/hardened|raw/<rock> (gabbro)`

### Granite

- Feature: `tfc:vein/granite_dike`
- Gen: `tfc:pipe_vein` (`Y=-64..180 rarity=300 density=0.98 height=150 radius=18 skew=7..20 slant=2..5 sign=0`)
- Host: `raw: 21 rocks; gravel: 21 rocks; hardened: 21 rocks`
- Output: `tfc:rock/hardened|raw/<rock> (granite)`

### Diorite

- Feature: `tfc:vein/diorite_dike`
- Gen: `tfc:pipe_vein` (`Y=-64..180 rarity=300 density=0.98 height=150 radius=18 skew=7..20 slant=2..5 sign=0`)
- Host: `raw: 21 rocks; gravel: 21 rocks; hardened: 21 rocks`
- Output: `tfc:rock/hardened|raw/<rock> (diorite)`

## Emerald (tfc)

- Feature: `tfc:vein/emerald`
- Gen: `tfc:pipe_vein` (`Y=-64..100 rarity=80 density=0.15 height=60 radius=5 skew=5..13 slant=0..2 sign=0`)
- Host: `diorite, gabbro, granite`
- Output: `tfc:ore/emerald/<host rock> (diorite, gabbro, granite)`

## Galena (tfcm)

### Surface

- Feature: `tfcm:vein/surface_galena`
- Gen: `tfc:cluster_vein` (`Y=32..75 rarity=40 density=0.3 size=15`)
- Host: `andesite, basalt, dacite, diorite, gabbro, gneiss, granite, marble, phyllite, quartzite, rhyolite, schist, slate`
- Output: `tfcm:ore/{poor,normal,rich}_galena/<host rock> (andesite, basalt, dacite, diorite, gabbro, gneiss, granite, marble, phyllite, quartzite, rhyolite, schist, slate)` (weights poor/normal/rich=70/25/5)
- Indicator: `rarity=12 depth=35 underground_rarity=1 underground_count=0 blocks=tfcm:ore/small_galena`

### Normal

- Feature: `tfcm:vein/normal_galena`
- Gen: `tfc:cluster_vein` (`Y=-80..20 rarity=60 density=0.6 size=25`)
- Host: `andesite, basalt, dacite, diorite, gabbro, gneiss, granite, marble, phyllite, quartzite, rhyolite, schist, slate`
- Output: `tfcm:ore/{poor,normal,rich}_galena/<host rock> (andesite, basalt, dacite, diorite, gabbro, gneiss, granite, marble, phyllite, quartzite, rhyolite, schist, slate)` (weights poor/normal/rich=15/25/60)
- Indicator: `rarity=0 depth=35 underground_rarity=1 underground_count=4 blocks=tfcm:ore/small_galena`

## Garnierite (tfc)

### Normal

- Feature: `tfc:vein/normal_garnierite`
- Gen: `tfc:cluster_vein` (`Y=-80..0 rarity=25 density=0.3 size=18`)
- Host: `diorite, gabbro, granite`
- Output: `tfc:ore/{poor,normal,rich}_garnierite/<host rock> (diorite, gabbro, granite)` (weights poor/normal/rich=35/40/25)
- Indicator: `rarity=12 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_garnierite`

### Gabbro

- Feature: `tfc:vein/gabbro_garnierite`
- Gen: `tfc:cluster_vein` (`Y=-80..0 rarity=20 density=0.6 size=30`)
- Host: `gabbro`
- Output: `tfc:ore/{poor,normal,rich}_garnierite/<host rock> (gabbro)` (weights poor/normal/rich=15/25/60)
- Indicator: `rarity=0 depth=35 underground_rarity=1 underground_count=7 blocks=tfc:ore/small_garnierite`

## Graphite (tfc)

- Feature: `tfc:vein/graphite`
- Gen: `tfc:cluster_vein` (`Y=-30..60 rarity=20 density=0.4 size=20`)
- Host: `gneiss, marble, quartzite, schist`
- Output: `tfc:ore/graphite/<host rock> (gneiss, marble, quartzite, schist)`

## Gravel (tfc)

- Feature: `tfc:vein/gravel`
- Gen: `tfc:disc_vein` (`Y=-64..100 rarity=30 density=0.98 size=44 height=2`)
- Host: `andesite, basalt, chalk, chert, claystone, conglomerate, dacite, diorite, dolomite, gabbro, gneiss, granite, limestone, marble, phyllite, quartzite, rhyolite, schist, shale, slate, tuff`
- Output: `tfc:rock/gravel/<rock> (andesite, basalt, chalk, chert, claystone, conglomerate, dacite, diorite, dolomite, gabbro, gneiss, granite, limestone, marble, phyllite, quartzite, rhyolite, schist, shale, slate, tuff)`

## Gypsum (tfc)

- Feature: `tfc:vein/gypsum`
- Gen: `tfc:disc_vein` (`Y=40..100 rarity=70 density=0.3 size=25 height=5`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff`
- Output: `tfc:ore/gypsum/<host rock> (chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff)`

## Halite (tfc)

- Feature: `tfc:vein/halite`
- Gen: `tfc:disc_vein` (`Y=-45..-12 rarity=110 density=0.85 size=35 height=4 project=true project_offset=true`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff`
- Output: `tfc:halite`

## Hematite (tfc)

### Surface

- Feature: `tfc:vein/surface_hematite`
- Gen: `tfc:cluster_vein` (`Y=10..90 rarity=45 density=0.4 size=20`)
- Host: `andesite, basalt, dacite, rhyolite`
- Output: `tfc:ore/{poor,normal,rich}_hematite/<host rock> (andesite, basalt, dacite, rhyolite)` (weights poor/normal/rich=35/40/25)
- Indicator: `rarity=24 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_hematite`

## Kaolin (tfc)

### Disc

- Feature: `tfc:vein/kaolin_disc`
- Gen: `tfc:kaolin_disc_vein` (`Y=-7..1 rarity=50 density=1.0 size=18 height=6 project=true`)
- Indicator: `rarity=10 depth=35 spread=5 underground_rarity=1 underground_count=0 blocks=tfc:plant/blood_lily`
- Placement: `tfc:climate(min_temperature=18 min_groundwater=300)`

## Lapis Lazuli (tfc)

- Feature: `tfc:vein/lapis_lazuli`
- Gen: `tfc:cluster_vein` (`Y=-20..80 rarity=30 density=0.12 size=30`)
- Host: `limestone, marble`
- Output: `tfc:ore/lapis_lazuli/<host rock> (limestone, marble)`

## Lignite (tfc)

- Feature: `tfc:vein/lignite`
- Gen: `tfc:disc_vein` (`Y=-20..-8 rarity=160 density=0.85 size=40 height=2 project=true project_offset=true`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff`
- Output: `tfc:lignite`

## Limonite (tfc)

### Surface

- Feature: `tfc:vein/surface_limonite`
- Gen: `tfc:cluster_vein` (`Y=10..90 rarity=90 density=0.4 size=20`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff`
- Output: `tfc:ore/{poor,normal,rich}_limonite/<host rock> (chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff)` (weights poor/normal/rich=35/40/25)
- Indicator: `rarity=24 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_limonite`

## Magnetite (tfc)

### Surface

- Feature: `tfc:vein/surface_magnetite`
- Gen: `tfc:cluster_vein` (`Y=10..90 rarity=90 density=0.4 size=20`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff`
- Output: `tfc:ore/{poor,normal,rich}_magnetite/<host rock> (chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff)` (weights poor/normal/rich=35/40/25)
- Indicator: `rarity=24 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_magnetite`

## Malachite (tfc)

### Surface

- Feature: `tfc:vein/surface_malachite`
- Gen: `tfc:cluster_vein` (`Y=40..130 rarity=32 density=0.25 size=20`)
- Host: `chalk, dolomite, limestone, marble`
- Output: `tfc:ore/{poor,normal,rich}_malachite/<host rock> (chalk, dolomite, limestone, marble)` (weights poor/normal/rich=70/25/5)
- Indicator: `rarity=14 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_malachite`

### Normal

- Feature: `tfc:vein/normal_malachite`
- Gen: `tfc:cluster_vein` (`Y=-30..70 rarity=45 density=0.5 size=30`)
- Host: `chalk, dolomite, limestone, marble`
- Output: `tfc:ore/{poor,normal,rich}_malachite/<host rock> (chalk, dolomite, limestone, marble)` (weights poor/normal/rich=35/40/25)
- Indicator: `rarity=25 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_malachite`

## Native Copper (tfc)

### Surface

- Feature: `tfc:vein/surface_native_copper`
- Gen: `tfc:cluster_vein` (`Y=40..130 rarity=24 density=0.25 size=20`)
- Host: `andesite, basalt, dacite, rhyolite`
- Output: `tfc:ore/{poor,normal,rich}_native_copper/<host rock> (andesite, basalt, dacite, rhyolite)` (weights poor/normal/rich=70/25/5) + tfc:deposit/native_copper/<host rock>=10
- Indicator: `rarity=14 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_native_copper`

## Native Gold (tfc)

### Normal

- Feature: `tfc:vein/normal_native_gold`
- Gen: `tfc:cluster_vein` (`Y=0..70 rarity=90 density=0.25 size=15`)
- Host: `andesite, basalt, dacite, diorite, gabbro, granite, rhyolite`
- Output: `tfc:ore/{poor,normal,rich}_native_gold/<host rock> (andesite, basalt, dacite, diorite, gabbro, granite, rhyolite)` (weights poor/normal/rich=35/40/25)
- Indicator: `rarity=40 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_native_gold`

### Rich

- Feature: `tfc:vein/rich_native_gold`
- Gen: `tfc:cluster_vein` (`Y=-80..20 rarity=50 density=0.5 size=40`)
- Host: `diorite, gabbro, granite`
- Output: `tfc:ore/{poor,normal,rich}_native_gold/<host rock> (diorite, gabbro, granite)` (weights poor/normal/rich=15/25/60)
- Indicator: `rarity=0 depth=35 underground_rarity=1 underground_count=4 blocks=tfc:ore/small_native_gold`

## Native Silver (tfc)

### Surface

- Feature: `tfc:vein/surface_native_silver`
- Gen: `tfc:cluster_vein` (`Y=90..180 rarity=15 density=0.2 size=10`)
- Host: `diorite, granite`
- Output: `tfc:ore/{poor,normal,rich}_native_silver/<host rock> (diorite, granite)` (weights poor/normal/rich=70/25/5)
- Indicator: `rarity=12 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_native_silver`

### Normal

- Feature: `tfc:vein/normal_native_silver`
- Gen: `tfc:cluster_vein` (`Y=-80..20 rarity=25 density=0.6 size=25`)
- Host: `diorite, gneiss, granite, schist`
- Output: `tfc:ore/{poor,normal,rich}_native_silver/<host rock> (diorite, gneiss, granite, schist)` (weights poor/normal/rich=15/25/60)
- Indicator: `rarity=0 depth=35 underground_rarity=1 underground_count=9 blocks=tfc:ore/small_native_silver`

## Opal (tfc)

- Feature: `tfc:vein/opal`
- Gen: `tfc:disc_vein` (`Y=40..60 rarity=25 density=0.2 size=8 height=4`)
- Host: `andesite, basalt, chalk, chert, claystone, conglomerate, dacite, dolomite, limestone, rhyolite, shale, tuff`
- Output: `tfc:ore/opal/<host rock> (andesite, basalt, chalk, chert, claystone, conglomerate, dacite, dolomite, limestone, rhyolite, shale, tuff)`

## Pyrite (tfc)

### Fake Native Gold

- Feature: `tfc:vein/fake_native_gold`
- Gen: `tfc:cluster_vein` (`Y=-50..70 rarity=16 density=0.35 size=15`)
- Host: `andesite, basalt, dacite, diorite, gabbro, granite, rhyolite`
- Output: `tfc:ore/pyrite/<host rock> (andesite, basalt, dacite, diorite, gabbro, granite, rhyolite)`

## Ruby (tfc)

- Feature: `tfc:vein/ruby`
- Gen: `tfc:cluster_vein` (`Y=-70..-10 rarity=12 density=0.2 size=22`)
- Host: `gneiss, schist`
- Output: `tfc:ore/ruby/<host rock> (gneiss, schist)`

## Saltpeter (tfc)

- Feature: `tfc:vein/saltpeter`
- Gen: `tfc:disc_vein` (`Y=40..100 rarity=110 density=0.4 size=35 height=5`)
- Host: `chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff`
- Output: `tfc:ore/saltpeter/<host rock> (chalk, chert, claystone, conglomerate, dolomite, limestone, shale, tuff)`

## Sphalerite (tfc)

### Surface

- Feature: `tfc:vein/surface_sphalerite`
- Gen: `tfc:cluster_vein` (`Y=40..130 rarity=30 density=0.3 size=20`)
- Host: `andesite, basalt, dacite, rhyolite`
- Output: `tfc:ore/{poor,normal,rich}_sphalerite/<host rock> (andesite, basalt, dacite, rhyolite)` (weights poor/normal/rich=70/25/5)
- Indicator: `rarity=12 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_sphalerite`

### Normal

- Feature: `tfc:vein/normal_sphalerite`
- Gen: `tfc:cluster_vein` (`Y=-80..20 rarity=45 density=0.6 size=40`)
- Host: `diorite, gabbro, granite`
- Output: `tfc:ore/{poor,normal,rich}_sphalerite/<host rock> (diorite, gabbro, granite)` (weights poor/normal/rich=15/25/60)
- Indicator: `rarity=0 depth=35 underground_rarity=1 underground_count=5 blocks=tfc:ore/small_sphalerite`

## Sulfur (tfc)

### Default

- Feature: `tfc:vein/sulfur`
- Gen: `tfc:disc_vein` (`Y=-64..-45 rarity=4 density=0.25 size=18 height=5`)
- Host: `diorite, gabbro, gneiss, granite, marble, phyllite, quartzite, schist, slate`
- Output: `tfc:ore/sulfur/<host rock> (diorite, gabbro, gneiss, granite, marble, phyllite, quartzite, schist, slate)`

### Tuff

- Feature: `tfc:vein/tuff_sulfur`
- Gen: `tfc:disc_vein` (`Y=40..120 rarity=4 density=0.45 size=18 height=4`)
- Host: `tuff`
- Output: `tfc:ore/sulfur/tuff`

## Sylvite (tfc)

- Feature: `tfc:vein/sylvite`
- Gen: `tfc:disc_vein` (`Y=40..100 rarity=60 density=0.35 size=35 height=5`)
- Host: `chert, claystone, shale`
- Output: `tfc:ore/sylvite/<host rock> (chert, claystone, shale)`

## Tetrahedrite (tfc)

### Surface

- Feature: `tfc:vein/surface_tetrahedrite`
- Gen: `tfc:cluster_vein` (`Y=90..170 rarity=7 density=0.25 size=20`)
- Host: `gneiss, marble, phyllite, quartzite, schist, slate`
- Output: `tfc:ore/{poor,normal,rich}_tetrahedrite/<host rock> (gneiss, marble, phyllite, quartzite, schist, slate)` (weights poor/normal/rich=70/25/5)
- Indicator: `rarity=8 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_tetrahedrite`

### Normal

- Feature: `tfc:vein/normal_tetrahedrite`
- Gen: `tfc:cluster_vein` (`Y=-30..70 rarity=40 density=0.5 size=30`)
- Host: `gneiss, marble, phyllite, quartzite, schist, slate`
- Output: `tfc:ore/{poor,normal,rich}_tetrahedrite/<host rock> (gneiss, marble, phyllite, quartzite, schist, slate)` (weights poor/normal/rich=35/40/25)
- Indicator: `rarity=25 depth=35 underground_rarity=1 underground_count=0 blocks=tfc:ore/small_tetrahedrite`

## Uraninite (tfcm)

### Normal

- Feature: `tfcm:vein/normal_uraninite`
- Gen: `tfc:cluster_vein` (`Y=-80..20 rarity=50 density=0.6 size=25`)
- Host: `andesite, basalt, dacite, gneiss, marble, phyllite, quartzite, rhyolite, schist, slate`
- Output: `tfcm:ore/{poor,normal,rich}_uraninite/<host rock> (andesite, basalt, dacite, gneiss, marble, phyllite, quartzite, rhyolite, schist, slate)` (weights poor/normal/rich=15/25/60)
- Indicator: `rarity=0 depth=35 underground_rarity=1 underground_count=4 blocks=tfcm:ore/small_uraninite`
