package net.claustra01.tfcmu2;

public record Tfcmu2ToolTierSpec(
    int level,
    int uses,
    float speed,
    float attackDamageBonus,
    int enchantmentValue,
    String repairMetal
) {
    public static final Tfcmu2ToolTierSpec INVAR = new Tfcmu2ToolTierSpec(3, 2200, 8f, 4.75f, 12, "invar");
    public static final Tfcmu2ToolTierSpec TITANIUM = new Tfcmu2ToolTierSpec(5, 3300, 11f, 7f, 17, "titanium");
    public static final Tfcmu2ToolTierSpec TUNGSTEN_STEEL = new Tfcmu2ToolTierSpec(7, 8125, 13f, 10.5f, 25, "tungsten_steel");
}
