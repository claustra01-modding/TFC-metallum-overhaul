package net.claustra01.tfcmu2;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class Tfcmu2ClientEvents {
    private Tfcmu2ClientEvents() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(Tfcmu2ClientEvents::clientSetup);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        // Ore models rely on transparent overlay textures. If rendered as solid, the transparent pixels show up as black.
        event.enqueueWork(() -> {
            final RenderType cutout = RenderType.cutoutMipped();

            // Regular ores (non-graded)
            Tfcmu2Blocks.ORES.values().forEach(oresByRock ->
                oresByRock.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout)));

            // Graded ores in TFC rocks
            Tfcmu2Blocks.GRADED_ORES.values().forEach(oresByRock ->
                oresByRock.values().forEach(oresByGrade ->
                    oresByGrade.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout))));

            // Graded ores in vanilla stones (netherrack/endstone)
            Tfcmu2Blocks.VANILLA_GRADED_ORES.values().forEach(oresByStone ->
                oresByStone.values().forEach(oresByGrade ->
                    oresByGrade.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout))));

            // Compat ores in vanilla stones (netherrack/endstone)
            Tfcmu2Blocks.COMPAT_VANILLA_ORES.values().forEach(oresByStone ->
                oresByStone.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout)));

            // Loose small ores
            Tfcmu2Blocks.SMALL_ORES.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout));

            // Loose ore pieces that have no native surface sample blocks (e.g. graphite)
            Tfcmu2Blocks.COMPAT_SMALL_ORE_PIECES.values().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block.get(), cutout));
        });
    }
}
