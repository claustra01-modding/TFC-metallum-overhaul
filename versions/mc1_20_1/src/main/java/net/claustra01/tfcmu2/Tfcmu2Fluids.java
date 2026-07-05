package net.claustra01.tfcmu2;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import net.dries007.tfc.common.fluids.FluidRegistryObject;
import net.dries007.tfc.common.fluids.MoltenFluid;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class Tfcmu2Fluids {
    private static final ResourceLocation MOLTEN_STILL = Helpers.identifier("block/molten_still");
    private static final ResourceLocation MOLTEN_FLOW = Helpers.identifier("block/molten_flow");

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, "tfc");
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, "tfc");
    public static final Map<Tfcmu2Metal, FluidRegistryObject<MoltenFluid>> METAL_FLUIDS = registerMetalFluids();

    private Tfcmu2Fluids() {
    }

    private static Map<Tfcmu2Metal, FluidRegistryObject<MoltenFluid>> registerMetalFluids() {
        final EnumMap<Tfcmu2Metal, FluidRegistryObject<MoltenFluid>> fluids = new EnumMap<>(Tfcmu2Metal.class);
        for (Tfcmu2Metal metal : Tfcmu2Metal.values()) {
            final String fluidName = "metal/" + metal.getSerializedName();
            final String flowingName = "metal/flowing_" + metal.getSerializedName();
            fluids.put(metal, registerMoltenMetal(fluidName, flowingName, metal));
        }
        return Collections.unmodifiableMap(fluids);
    }

    private static FluidRegistryObject<MoltenFluid> registerMoltenMetal(String fluidName, String flowingName, Tfcmu2Metal metal) {
        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, fluidName, fluidName, flowingName,
            Tfcmu2Fluids::configureMoltenFluid,
            () -> new Tfcmu2MoltenFluidType(moltenFluidProperties().descriptionId("fluid.tfc.metal." + metal.getSerializedName()).rarity(metal.rarity()), metal),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new);
    }

    private static void configureMoltenFluid(ForgeFlowingFluid.Properties properties) {
    }

    private static FluidType.Properties moltenFluidProperties() {
        return FluidType.Properties.create()
            .adjacentPathType(BlockPathTypes.LAVA)
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

    private static final class Tfcmu2MoltenFluidType extends FluidType {
        private final int tintColor;

        private Tfcmu2MoltenFluidType(Properties properties, Tfcmu2Metal metal) {
            super(properties);
            tintColor = 0xFF000000 | metal.color();
        }

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
            consumer.accept(new IClientFluidTypeExtensions() {
                @Override
                public int getTintColor() {
                    return tintColor;
                }

                @Override
                public ResourceLocation getStillTexture() {
                    return MOLTEN_STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return MOLTEN_FLOW;
                }
            });
        }
    }
}
