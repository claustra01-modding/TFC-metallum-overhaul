package net.claustra01.tfcm;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public final class TfcmTiers {
    public static final Tier INVAR = create(TfcmToolTierSpec.INVAR);
    public static final Tier TITANIUM = create(TfcmToolTierSpec.TITANIUM);
    public static final Tier TUNGSTEN_STEEL = create(TfcmToolTierSpec.TUNGSTEN_STEEL);

    private TfcmTiers() {
    }

    private static Tier create(TfcmToolTierSpec spec) {
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
                return TfcmArmorMaterials.repairIngredient(spec.repairMetal());
            }
        };
    }
}
