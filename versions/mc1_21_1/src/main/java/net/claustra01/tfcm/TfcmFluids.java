package net.claustra01.tfcm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import net.dries007.tfc.common.fluids.FluidHolder;
import net.dries007.tfc.common.fluids.MoltenFluid;
import net.dries007.tfc.common.blocks.MoltenFluidBlock;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class TfcmFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, "tfc");
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, "tfc");
    public static final DeferredRegister.Blocks AUXILIARY_FLUID_BLOCKS = DeferredRegister.createBlocks(TfcmMod.MOD_ID);
    public static final DeferredRegister.Items AUXILIARY_BUCKET_ITEMS = DeferredRegister.createItems(TfcmMod.MOD_ID);
    public static final DeferredRegister<FluidType> AUXILIARY_FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, TfcmMod.MOD_ID);
    public static final DeferredRegister<Fluid> AUXILIARY_FLUIDS = DeferredRegister.create(Registries.FLUID, TfcmMod.MOD_ID);
    public static final DeferredBlock<LiquidBlock> MOLTEN_ANDESITE_BLOCK = AUXILIARY_FLUID_BLOCKS.register("fluid/metal/andesite",
        TfcmFluids::createMoltenAndesiteBlock);
    public static final DeferredItem<BucketItem> MOLTEN_ANDESITE_BUCKET = AUXILIARY_BUCKET_ITEMS.register("bucket/metal/andesite",
        TfcmFluids::createMoltenAndesiteBucket);
    public static final FluidHolder<BaseFlowingFluid> MOLTEN_ANDESITE = registerMoltenAndesite();
    public static final Map<TfcmMetal, FluidHolder<BaseFlowingFluid>> METAL_FLUIDS = registerMetalFluids();

    private TfcmFluids() {
    }

    private static Map<TfcmMetal, FluidHolder<BaseFlowingFluid>> registerMetalFluids() {
        final EnumMap<TfcmMetal, FluidHolder<BaseFlowingFluid>> fluids = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String fluidName = "metal/" + metal.getSerializedName();
            final String flowingName = "metal/flowing_" + metal.getSerializedName();
            fluids.put(metal, registerMoltenMetal(fluidName, flowingName, metal));
        }
        return Collections.unmodifiableMap(fluids);
    }

    private static FluidHolder<BaseFlowingFluid> registerMoltenMetal(String fluidName, String flowingName, TfcmMetal metal) {
        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, fluidName, fluidName, flowingName,
            properties -> {
            },
            () -> new FluidType(moltenFluidProperties().descriptionId("fluid.tfc.metal." + metal.getSerializedName()).rarity(metal.rarity())),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new);
    }

    private static FluidHolder<BaseFlowingFluid> registerMoltenAndesite() {
        final TfcmFluidSpec spec = TfcmFluidSpec.MOLTEN_ANDESITE;
        return RegistrationHelpers.registerFluid(
            AUXILIARY_FLUID_TYPES,
            AUXILIARY_FLUIDS,
            "metal/" + spec.serializedName(),
            "metal/" + spec.serializedName(),
            "metal/flowing_" + spec.serializedName(),
            properties -> properties.block(MOLTEN_ANDESITE_BLOCK).bucket(MOLTEN_ANDESITE_BUCKET).explosionResistance(100f),
            () -> new FluidType(moltenFluidProperties().descriptionId("fluid.tfcm.metal." + spec.serializedName()).rarity(spec.rarity())),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new);
    }

    private static LiquidBlock createMoltenAndesiteBlock() {
        return new MoltenFluidBlock(MOLTEN_ANDESITE.source(), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).noLootTable());
    }

    private static BucketItem createMoltenAndesiteBucket() {
        return new BucketItem(MOLTEN_ANDESITE.getSource(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));
    }

    private static FluidType.Properties moltenFluidProperties() {
        return FluidType.Properties.create()
            .adjacentPathType(PathType.LAVA)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
            .lightLevel(15)
            .density(3000)
            .viscosity(6000)
            .temperature(1300)
            .canConvertToSource(false)
            .canDrown(false)
            .canExtinguish(false)
            .canHydrate(false)
            .canPushEntity(false)
            .canSwim(false)
            .supportsBoating(false);
    }
}
