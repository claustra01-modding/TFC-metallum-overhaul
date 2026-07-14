package net.claustra01.tfcmu2;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public final class Tfcmu2Tiers {
    public static final Tier INVAR = create(Tfcmu2ToolTierSpec.INVAR);
    public static final Tier TITANIUM = create(Tfcmu2ToolTierSpec.TITANIUM);
    public static final Tier TUNGSTEN_STEEL = create(Tfcmu2ToolTierSpec.TUNGSTEN_STEEL);

    private Tfcmu2Tiers() {
    }

    private static Tier create(Tfcmu2ToolTierSpec spec) {
        return new Tier() {
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
            public int getLevel() {
                return spec.level();
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
