package net.claustra01.tfcmu2;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public final class Tfcmu2Tiers {
    public static final Tier TUNGSTEN_STEEL = new Tier() {
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
        public int getLevel() {
            return 7;
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
