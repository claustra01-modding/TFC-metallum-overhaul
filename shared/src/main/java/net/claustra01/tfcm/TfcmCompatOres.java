package net.claustra01.tfcm;

import java.util.ArrayList;
import java.util.List;

public final class TfcmCompatOres {
    public static final List<String> TFC_ORES = TfcmContentNames.TFC_ORES;
    public static final List<String> FIRMALIFE_ORES = TfcmContentNames.FIRMALIFE_ORES;

    public static List<String> getLoadedOreNames() {
        final List<String> ores = new ArrayList<>();
        TFC_ORES.stream()
            .filter(TfcmEnableContent::isOreEnabled)
            .forEach(ores::add);
        if (TfcmPlatform.isModLoaded(TfcmMod.FIRMALIFE_MOD_ID)) {
            FIRMALIFE_ORES.stream()
                .filter(TfcmEnableContent::isOreEnabled)
                .forEach(ores::add);
        }
        return ores;
    }

    private TfcmCompatOres() {
    }
}
