package net.claustra01.tfcm;

import java.util.ArrayList;
import java.util.List;

public final class TfcmCompatOres {
    public static final List<String> TFC_ORES = TfcmContentNames.TFC_ORES;
    public static final List<String> FIRMALIFE_ORES = TfcmContentNames.FIRMALIFE_ORES;

    public static List<String> getLoadedOreNames() {
        final List<String> ores = new ArrayList<>(TFC_ORES);
        if (TfcmPlatform.isModLoaded(TfcmMod.FIRMALIFE_MOD_ID)) {
            ores.addAll(FIRMALIFE_ORES);
        }
        return ores;
    }

    private TfcmCompatOres() {
    }
}
