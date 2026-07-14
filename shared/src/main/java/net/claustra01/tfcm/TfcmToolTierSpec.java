package net.claustra01.tfcm;

public record TfcmToolTierSpec(
    int level,
    int uses,
    float speed,
    float attackDamageBonus,
    int enchantmentValue,
    String repairMetal
) {
    public static final TfcmToolTierSpec INVAR = new TfcmToolTierSpec(3, 2200, 8f, 4.75f, 12, "invar");
    public static final TfcmToolTierSpec TITANIUM = new TfcmToolTierSpec(5, 3300, 11f, 7f, 17, "titanium");
    public static final TfcmToolTierSpec TUNGSTEN_STEEL = new TfcmToolTierSpec(7, 8125, 13f, 10.5f, 25, "tungsten_steel");
}
