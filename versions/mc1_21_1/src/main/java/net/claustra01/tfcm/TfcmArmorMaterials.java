package net.claustra01.tfcm;

import java.util.EnumMap;
import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TfcmArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, TfcmMod.MOD_ID);
    public static final ArmorSet INVAR = register("invar", 429, 495, 528, 370, 1, 4, 5, 2, 12, 0f, 0f);
    public static final ArmorSet TITANIUM = register("titanium", 650, 750, 800, 550, 2, 5, 6, 2, 17, 2f, 0.05f);
    public static final ArmorSet TUNGSTEN_STEEL = register("tungsten_steel", 1105, 1275, 1360, 935, 4, 7, 9, 4, 25, 4f, 0.15f);

    private static ArmorSet register(String name, int bootsDurability, int leggingsDurability, int chestplateDurability, int helmetDurability,
                                     int bootsDefense, int leggingsDefense, int chestplateDefense, int helmetDefense,
                                     int enchantmentValue, float toughness, float knockbackResistance) {
        final EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        defense.put(ArmorItem.Type.BOOTS, bootsDefense);
        defense.put(ArmorItem.Type.LEGGINGS, leggingsDefense);
        defense.put(ArmorItem.Type.CHESTPLATE, chestplateDefense);
        defense.put(ArmorItem.Type.HELMET, helmetDefense);
        defense.put(ArmorItem.Type.BODY, chestplateDefense);
        final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(TfcmMod.MOD_ID, name);
        final DeferredHolder<ArmorMaterial, ArmorMaterial> holder = ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
            defense,
            enchantmentValue,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> repairIngredient(name),
            List.of(new ArmorMaterial.Layer(texture)),
            toughness,
            knockbackResistance
        ));
        return new ArmorSet(holder, bootsDurability, leggingsDurability, chestplateDurability, helmetDurability);
    }

    static Ingredient repairIngredient(String metal) {
        final TagKey<Item> tag = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "ingots/" + metal));
        return Ingredient.of(tag);
    }

    public record ArmorSet(DeferredHolder<ArmorMaterial, ArmorMaterial> material, int bootsDurability, int leggingsDurability,
                           int chestplateDurability, int helmetDurability) {
        public int durability(ArmorItem.Type type) {
            return switch (type) {
                case BOOTS -> bootsDurability;
                case LEGGINGS -> leggingsDurability;
                case CHESTPLATE, BODY -> chestplateDurability;
                case HELMET -> helmetDurability;
            };
        }
    }

    private TfcmArmorMaterials() {
    }
}
