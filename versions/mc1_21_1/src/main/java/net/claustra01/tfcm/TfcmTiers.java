package net.claustra01.tfcm;

import net.dries007.tfc.common.LevelTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public final class TfcmTiers {
    public static final LevelTier INVAR = create(BlockTags.INCORRECT_FOR_IRON_TOOL, TfcmToolTierSpec.INVAR);
    public static final LevelTier TITANIUM = create(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, TfcmToolTierSpec.TITANIUM);
    public static final LevelTier TUNGSTEN_STEEL = create(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, TfcmToolTierSpec.TUNGSTEN_STEEL);

    private TfcmTiers() {
    }

    private static LevelTier create(TagKey<Block> incorrectBlocks, TfcmToolTierSpec spec) {
        return new LevelTier() {
            @Override
            public int level() {
                return spec.level();
            }

            @Override
            public int getUses() {
                return spec.uses();
            }

            @Override
            public float getSpeed() {
                return spec.speed();
            }

            @Override
            public float getAttackDamageBonus() {
                return spec.attackDamageBonus();
            }

            @Override
            public TagKey<Block> getIncorrectBlocksForDrops() {
                return incorrectBlocks;
            }

            @Override
            public int getEnchantmentValue() {
                return spec.enchantmentValue();
            }

            @Override
            public Ingredient getRepairIngredient() {
                return TfcmArmorMaterials.repairIngredient(spec.repairMetal());
            }
        };
    }
}
