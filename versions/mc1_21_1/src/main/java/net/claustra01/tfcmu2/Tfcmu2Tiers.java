package net.claustra01.tfcmu2;

import net.dries007.tfc.common.LevelTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public final class Tfcmu2Tiers {
    public static final LevelTier INVAR = create(BlockTags.INCORRECT_FOR_IRON_TOOL, Tfcmu2ToolTierSpec.INVAR);
    public static final LevelTier TITANIUM = create(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, Tfcmu2ToolTierSpec.TITANIUM);
    public static final LevelTier TUNGSTEN_STEEL = create(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, Tfcmu2ToolTierSpec.TUNGSTEN_STEEL);

    private Tfcmu2Tiers() {
    }

    private static LevelTier create(TagKey<Block> incorrectBlocks, Tfcmu2ToolTierSpec spec) {
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
                return Tfcmu2ArmorMaterials.repairIngredient(spec.repairMetal());
            }
        };
    }
}
