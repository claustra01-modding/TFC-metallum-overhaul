package net.claustra01.tfcm;

import java.util.Comparator;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TfcmCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TfcmMod.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tfcm"))
            .icon(() -> TfcmItems.METAL_INGOTS.get(TfcmMetal.TUNGSTEN_STEEL).get().getDefaultInstance())
            .displayItems((parameters, output) -> TfcmItems.ITEMS.getEntries().stream()
                .filter(item -> TfcmItems.isOptionalCompatItemEnabled(item.getId()))
                .sorted(Comparator.comparing(item -> item.getId().toString()))
                .map(item -> item.get())
                .forEach(output::accept))
            .build());

    private TfcmCreativeTabs() {
    }
}
