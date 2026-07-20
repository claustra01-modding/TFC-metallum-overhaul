package net.claustra01.tfcm;

import net.dries007.tfc.common.blockentities.AnvilBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class TfcmBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TfcmMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<AnvilBlockEntity>> ANVIL =
        BLOCK_ENTITY_TYPES.register("anvil", () -> BlockEntityType.Builder.of(
            TfcmBlockEntities::createAnvil,
            TfcmBlocks.METAL_ANVILS.values().stream()
                .map(RegistryObject::get)
                .toArray(Block[]::new))
            .build(null));

    private TfcmBlockEntities() {
    }

    private static AnvilBlockEntity createAnvil(BlockPos pos, BlockState state) {
        return new AnvilBlockEntity(
            ANVIL.get(),
            pos,
            state,
            AnvilBlockEntity.AnvilInventory::new,
            Component.translatable("tfc.block_entity.anvil"));
    }
}
