package net.claustra01.tfcmu2;

import net.dries007.tfc.common.LevelTier;
import net.dries007.tfc.common.TFCTiers;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public final class Tfcmu2Tiers {
    public static final LevelTier TUNGSTEN_STEEL = new LevelTier() {
        @Override
        public int level() {
            return 7;
        }

        @Override
        public int getUses() {
            return 8125;
        }

        @Override
        public float getSpeed() {
            return 13f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 10.5f;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return TFCTiers.RED_STEEL.getIncorrectBlocksForDrops();
        }

        @Override
        public int getEnchantmentValue() {
            return 25;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Tfcmu2ArmorMaterials.repairIngredient("tungsten_steel");
        }
    };

    private Tfcmu2Tiers() {
    }
}
