package net.claustra01.tfcmu2;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.ModList;

public final class Tfcmu2CompatOres {
    public static final List<String> TFC_ORES = Tfcmu2ContentNames.TFC_ORES;
    public static final List<String> FIRMALIFE_ORES = Tfcmu2ContentNames.FIRMALIFE_ORES;
    public static final List<String> TFC_IE_ADDON_ORES = Tfcmu2ContentNames.TFC_IE_ADDON_ORES;

    private Tfcmu2CompatOres() {
    }

    public static List<String> getLoadedOreNames() {
        final ArrayList<String> ores = new ArrayList<>(TFC_ORES);
        if (ModList.get().isLoaded(Tfcmu2Mod.FIRMALIFE_MOD_ID)) {
            ores.addAll(FIRMALIFE_ORES);
        }
        if (ModList.get().isLoaded(Tfcmu2Mod.TFC_IE_ADDON_MOD_ID)) {
            ores.addAll(TFC_IE_ADDON_ORES);
        }
        return ores;
    }
}
