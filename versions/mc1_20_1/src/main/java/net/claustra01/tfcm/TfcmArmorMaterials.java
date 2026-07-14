package net.claustra01.tfcm;

import net.dries007.tfc.common.TFCArmorMaterials;
import net.dries007.tfc.util.PhysicalDamageType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public enum TfcmArmorMaterials implements ArmorMaterial, PhysicalDamageType.Multiplier {
    INVAR("invar", TFCArmorMaterials.WROUGHT_IRON, 1f, 0),
    TITANIUM("titanium", TFCArmorMaterials.BLACK_STEEL, 1f, 0),
    TUNGSTEN_STEEL("tungsten_steel", TFCArmorMaterials.RED_STEEL, 1.25f, 1);

    private final String name;
    private final TFCArmorMaterials delegate;
    private final float durabilityScale;
    private final int defenseBonus;

    TfcmArmorMaterials(String name, TFCArmorMaterials delegate, float durabilityScale, int defenseBonus) {
        this.name = name;
        this.delegate = delegate;
        this.durabilityScale = durabilityScale;
        this.defenseBonus = defenseBonus;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return Math.round(delegate.getDurabilityForType(type) * durabilityScale);
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return delegate.getDefenseForType(type) + defenseBonus;
    }

    @Override
    public int getEnchantmentValue() {
        return this == TUNGSTEN_STEEL ? 25 : delegate.getEnchantmentValue();
    }

    @Override
    public SoundEvent getEquipSound() {
        return delegate.getEquipSound();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient(name);
    }

    @Override
    public String getName() {
        return TfcmMod.MOD_ID + ":" + name;
    }

    @Override
    public float getToughness() {
        return delegate.getToughness() + (this == TUNGSTEN_STEEL ? 1f : 0f);
    }

    @Override
    public float getKnockbackResistance() {
        return delegate.getKnockbackResistance() + (this == TUNGSTEN_STEEL ? 0.05f : 0f);
    }

    @Override
    public float crushing() {
        return delegate.crushing();
    }

    @Override
    public float piercing() {
        return delegate.piercing();
    }

    @Override
    public float slashing() {
        return delegate.slashing();
    }

    static Ingredient repairIngredient(String metal) {
        final TagKey<Item> tag = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "ingots/" + metal));
        return Ingredient.of(tag);
    }
}
