package net.claustra01.tfcm;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class TfcmClientEvents {
    private TfcmClientEvents() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(TfcmClientEvents::clientSetup);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        // Ore models rely on transparent overlay textures. If rendered as solid, the transparent pixels show up as black.
        event.enqueueWork(() -> {
            final RenderType cutout = RenderType.cutoutMipped();

            // Regular ores (non-graded)
            TfcmBlocks.ORES.values().forEach(oresByRock ->
                oresByRock.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout)));

            // Graded ores in TFC rocks
            TfcmBlocks.GRADED_ORES.values().forEach(oresByRock ->
                oresByRock.values().forEach(oresByGrade ->
                    oresByGrade.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout))));

            // Graded ores in vanilla stones (netherrack/endstone)
            TfcmBlocks.VANILLA_GRADED_ORES.values().forEach(oresByStone ->
                oresByStone.values().forEach(oresByGrade ->
                    oresByGrade.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout))));

            // Compat ores in vanilla stones (netherrack/endstone)
            TfcmBlocks.COMPAT_VANILLA_ORES.values().forEach(oresByStone ->
                oresByStone.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout)));

            // Loose small ores
            TfcmBlocks.SMALL_ORES.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout));

            // Loose ore pieces that have no native surface sample blocks (e.g. graphite)
            TfcmBlocks.COMPAT_SMALL_ORE_PIECES.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout));
        });
    }
}
