package net.claustra01.tfcm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import net.dries007.tfc.common.blocks.MoltenFluidBlock;
import net.dries007.tfc.common.fluids.FluidRegistryObject;
import net.dries007.tfc.common.fluids.MoltenFluid;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class TfcmFluids {
    private static final ResourceLocation MOLTEN_STILL = Helpers.identifier("block/molten_still");
    private static final ResourceLocation MOLTEN_FLOW = Helpers.identifier("block/molten_flow");

    public static final DeferredRegister<Block> FLUID_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "tfc");
    public static final DeferredRegister<Item> BUCKET_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TfcmMod.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, "tfc");
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, "tfc");
    public static final Map<TfcmMetal, RegistryObject<LiquidBlock>> METAL_FLUID_BLOCKS = registerMetalFluidBlocks();
    public static final Map<TfcmMetal, RegistryObject<BucketItem>> METAL_FLUID_BUCKETS = registerMetalFluidBuckets();
    public static final Map<TfcmMetal, FluidRegistryObject<MoltenFluid>> METAL_FLUIDS = registerMetalFluids();

    private TfcmFluids() {
    }

    private static Map<TfcmMetal, FluidRegistryObject<MoltenFluid>> registerMetalFluids() {
        final EnumMap<TfcmMetal, FluidRegistryObject<MoltenFluid>> fluids = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            final String fluidName = "metal/" + metal.getSerializedName();
            final String flowingName = "metal/flowing_" + metal.getSerializedName();
            fluids.put(metal, registerMoltenMetal(fluidName, flowingName, metal));
        }
        return Collections.unmodifiableMap(fluids);
    }

    private static Map<TfcmMetal, RegistryObject<LiquidBlock>> registerMetalFluidBlocks() {
        final EnumMap<TfcmMetal, RegistryObject<LiquidBlock>> blocks = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            blocks.put(metal, FLUID_BLOCKS.register("fluid/metal/" + metal.getSerializedName(),
                () -> new MoltenFluidBlock(METAL_FLUIDS.get(metal).source(), BlockBehaviour.Properties.copy(Blocks.LAVA).noLootTable())));
        }
        return Collections.unmodifiableMap(blocks);
    }

    private static Map<TfcmMetal, RegistryObject<BucketItem>> registerMetalFluidBuckets() {
        final EnumMap<TfcmMetal, RegistryObject<BucketItem>> buckets = new EnumMap<>(TfcmMetal.class);
        for (TfcmMetal metal : TfcmMetal.values()) {
            buckets.put(metal, BUCKET_ITEMS.register("bucket/metal/" + metal.getSerializedName(),
                () -> new BucketItem(() -> METAL_FLUIDS.get(metal).getSource(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))));
        }
        return Collections.unmodifiableMap(buckets);
    }

    private static FluidRegistryObject<MoltenFluid> registerMoltenMetal(String fluidName, String flowingName, TfcmMetal metal) {
        return RegistrationHelpers.registerFluid(FLUID_TYPES, FLUIDS, fluidName, fluidName, flowingName,
            properties -> configureMoltenFluid(metal, properties),
            () -> new TfcmMoltenFluidType(moltenFluidProperties().descriptionId("fluid.tfc.metal." + metal.getSerializedName()).rarity(metal.rarity()), metal),
            MoltenFluid.Source::new,
            MoltenFluid.Flowing::new);
    }

    private static void configureMoltenFluid(TfcmMetal metal, ForgeFlowingFluid.Properties properties) {
        properties.block(METAL_FLUID_BLOCKS.get(metal))
            .bucket(METAL_FLUID_BUCKETS.get(metal))
            .explosionResistance(100f);
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

    private static final class TfcmMoltenFluidType extends FluidType {
        private final int tintColor;

        private TfcmMoltenFluidType(Properties properties, TfcmMetal metal) {
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
