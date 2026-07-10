package com.SprintXXL.primitivematter.content.registry;

import com.SprintXXL.primitivematter.content.devices.bucket.ItemBucket;
import com.SprintXXL.primitivematter.content.devices.pipe.BlockPipe;
import com.SprintXXL.primitivematter.content.substances.gas.GasBase;
import com.SprintXXL.primitivematter.content.substances.global.FluidBlockBase;
import com.SprintXXL.primitivematter.content.substances.liquid.LiquidBase;
import com.SprintXXL.primitivematter.content.substances.plasma.PlasmaBase;
import com.SprintXXL.primitivematter.content.substances.solid.BlockBase;
import com.SprintXXL.primitivematter.content.substances.solid.ItemBase;
import com.SprintXXL.primitivematter.content.substances.solid.specific.OreBlockBase;
import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.registry.DeviceRegistry;
import com.SprintXXL.primitivematter.library.devices.types.DeviceCategory;
import com.SprintXXL.primitivematter.library.devices.types.pipe.PipeDevice;
import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.registry.SubstanceRegistry;
import com.SprintXXL.primitivematter.library.substances.shared.FormEntry;
import com.SprintXXL.primitivematter.library.substances.states.SubstanceState;
import com.SprintXXL.primitivematter.library.substances.states.gas.GasState;
import com.SprintXXL.primitivematter.library.substances.states.liquid.LiquidState;
import com.SprintXXL.primitivematter.library.substances.states.plasma.PlasmaState;
import com.SprintXXL.primitivematter.library.substances.states.solid.SolidState;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidForm;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.SolidFormGroup;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.basic.BasicForm;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.ore.OreForm;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.ore.OreVariant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;

import static com.SprintXXL.primitivematter.Reference.MODID;
import static com.SprintXXL.primitivematter.content.client.helpers.ForgeFluidTextureResolver.getLiquidFlowTexture;
import static com.SprintXXL.primitivematter.content.client.helpers.ForgeFluidTextureResolver.getLiquidStillTexture;

public final class ContentRegistry {

    private ContentRegistry() {}

    // ------- \\
    // STORAGE \\
    // ------- \\
    private static final Map<ResourceLocation, Item> ITEMS = new HashMap<>();
    private static final Map<ResourceLocation, Item> CUSTOM_ITEMS = new HashMap<>();

    private static final Map<ResourceLocation, Block> BLOCKS = new HashMap<>();
    private static final Map<ResourceLocation, Block> CUSTOM_BLOCKS = new HashMap<>();

    private static final Map<ResourceLocation, Fluid> FLUIDS = new HashMap<>();
    private static final Map<ResourceLocation, Fluid> CUSTOM_FLUIDS = new HashMap<>();

    public static Collection<Item> getItems() {
        return ITEMS.values();
    }
    public static Collection<Item> getCustomItems() {
        return CUSTOM_ITEMS.values();
    }

    public static Collection<Block> getBlocks() {
        return BLOCKS.values();
    }
    public static Collection<Block> getCustomBlocks() {
        return CUSTOM_BLOCKS.values();
    }

    public static Collection<Fluid> getFluids() {
        return FLUIDS.values();
    }
    public static Collection<Fluid> getCustomFluids() {
        return CUSTOM_FLUIDS.values();
    }

    // ------- \\
    // HELPERS \\
    // ------- \\
    private static Item getItem(ResourceLocation registryName) {

        Item item = ITEMS.get(registryName);

        if (item == null) {
            throw new IllegalStateException("Could not find item: " + registryName);
        }

        return item;
    }

    private static Block getBlock(ResourceLocation registryName) {

        Block block = BLOCKS.get(registryName);

        if (block == null) {
            throw new IllegalStateException("Could not find block: " + registryName);
        }

        return block;
    }

    private static Fluid getFluid(ResourceLocation registryName) {

        Fluid fluid = FLUIDS.get(registryName);

        if (fluid == null) {
            throw new IllegalStateException("Could not find fluid: " + registryName);
        }

        return fluid;
    }

    private static ResourceLocation getFluidRegistryName(String modid, Fluid fluid) {
        return new ResourceLocation(modid, fluid.getName());
    }

    // ---- \\
    // MAIN \\
    // ---- \\
    public static void createContent() {

        for (Substance substance : SubstanceRegistry.getAllSubstances()) {

            for (SubstanceState state : substance.getSubstanceStates()) {

                if (state instanceof SolidState) {
                    createSolidContent(substance, (SolidState) state);
                }

                if (state instanceof LiquidState) {
                    createLiquidContent(substance, (LiquidState) state);
                }

                if (state instanceof GasState) {
                    createGasContent((GasState) state);
                }

                if (state instanceof PlasmaState) {
                    createPlasmaContent((PlasmaState) state);
                }
            }
        }

        for (Device device : DeviceRegistry.getAllDevices()) {

            if (device.getCategory() == DeviceCategory.BUCKET) {
                createBucketContent(device);
            }

            if (device.getCategory() == DeviceCategory.PIPE) {
                createPipeContent(device);
            }
        }
    }

    // ------------- \\
    // SOLID CONTENT \\
    // ------------- \\
    private static void createSolidContent(Substance substance, SolidState state) {

        for (SolidFormGroup group : state.getFormGroups()) {

            for (FormEntry<? extends SolidForm> entry : group.getForms()) {
                createSolidForm(substance, group, entry);
            }
        }
    }

    private static void createSolidForm(Substance substance, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        if (isBlockForm(form)) {
            createSolidBlock(substance, group, entry);
        }
        else {
            createSolidItem(substance, entry);
        }
    }

    // SOLID BLOCKS \\
    private static void createSolidBlock(Substance substance, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        if (entry.hasBackingRegistryName()) {
            resolveVanillaSolidBlock(entry);
            return;
        }

        if (group instanceof OreVariant && entry.getForm() == OreForm.ORE_BLOCK) {
            createCustomOreBlock(substance, (OreVariant) group, entry);
        }

        else {
            createCustomSolidBlock(substance, entry);
        }
    }

    private static void resolveVanillaSolidBlock(FormEntry<? extends SolidForm> entry) {

        Block block = ForgeRegistries.BLOCKS.getValue(entry.getBackingRegistryName());

        if (block == null) {
            throw new IllegalStateException("Could not resolve block: " + entry.getBackingRegistryName());
        }

        BLOCKS.put(block.getRegistryName(), block);
    }

    private static void createCustomOreBlock(Substance substance, OreVariant oreVariant, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        Item drop = getSolidItem(substance, oreVariant.getDrop());

        Block block = new OreBlockBase(form.getName(substance), drop, oreVariant.getHarvestLevel());

        BLOCKS.put(block.getRegistryName(), block);

        CUSTOM_BLOCKS.put(block.getRegistryName(), block);
    }

    private static void createCustomSolidBlock(Substance substance, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        Block block = new BlockBase(form.getName(substance));

        BLOCKS.put(block.getRegistryName(), block);

        CUSTOM_BLOCKS.put(block.getRegistryName(), block);
    }

    // SOLID ITEMS \\
    private static void createSolidItem(Substance substance, FormEntry<? extends SolidForm> entry) {

        if (entry.hasBackingRegistryName()) {
            resolveVanillaSolidItem(entry);
        }
        else {
            createCustomSolidItem(substance, entry);
        }
    }

    private static void resolveVanillaSolidItem(FormEntry<? extends SolidForm> entry) {

        Item item = ForgeRegistries.ITEMS.getValue(entry.getBackingRegistryName());

        if (item == null) {
            throw new IllegalStateException("Could not resolve item: " + entry.getBackingRegistryName());
        }

        ITEMS.put(item.getRegistryName(), item);
    }

    private static void createCustomSolidItem(Substance substance, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        Item item = new ItemBase(form.getName(substance));

        ITEMS.put(item.getRegistryName(), item);

        CUSTOM_ITEMS.put(item.getRegistryName(), item);
    }

    // SOLID HELPERS \\
    private static Item getSolidItem(Substance substance, SolidForm form) {

        ResourceLocation registryName = new ResourceLocation(
                MODID,
                form.getName(substance)
        );

        return getItem(registryName);
    }
    private static Block getSolidBlock(Substance substance, SolidForm form) {

        ResourceLocation registryName = new ResourceLocation(
                MODID,
                form.getName(substance)
        );

        return getBlock(registryName);
    }
    private static boolean isBlockForm(SolidForm form) {
        return form == BasicForm.SUBSTANCE_BLOCK || form == OreForm.ORE_BLOCK;
    }

    // -------------- \\
    // LIQUID CONTENT \\
    // -------------- \\
    private static void createLiquidContent(Substance substance, LiquidState state) {

        if (state.isVanilla()) {
            resolveVanillaLiquid(state);
        }
        else {
            createCustomLiquid(substance, state);
        }
    }

    private static void resolveVanillaLiquid(LiquidState state) {

        Fluid fluid = FluidRegistry.getFluid(state.getRegistryName());

        if (fluid == null) {
            throw new IllegalStateException("Could not find vanilla liquid: " + state.getRegistryName());
        }

        ResourceLocation registryName = getFluidRegistryName("minecraft", fluid);

        FLUIDS.put(registryName, fluid);
    }

    private static void createCustomLiquid(Substance substance, LiquidState state) {

        Fluid fluid = new LiquidBase(state.getRegistryName(), getLiquidStillTexture(substance), getLiquidFlowTexture(substance));

        ResourceLocation registryName = getFluidRegistryName(MODID, fluid);

        FLUIDS.put(registryName, fluid);
        CUSTOM_FLUIDS.put(registryName, fluid);
    }

    public static void createCustomFluidBlocks() {

        for (Fluid fluid : CUSTOM_FLUIDS.values()) {

            if (!(fluid instanceof LiquidBase)) {
                continue;
            }

            Block block = new FluidBlockBase(fluid);

            fluid.setBlock(block);

            BLOCKS.put(block.getRegistryName(), block);
            CUSTOM_BLOCKS.put(block.getRegistryName(), block);
        }
    }

    // ----------- \\
    // GAS CONTENT \\
    // ----------- \\
    private static void createGasContent(GasState state) {

        Fluid fluid = new GasBase(state.getRegistryName(), null, null);

        ResourceLocation registryName = getFluidRegistryName(MODID, fluid);

        FLUIDS.put(registryName, fluid);
        CUSTOM_FLUIDS.put(registryName, fluid);
    }

    // -------------- \\
    // PLASMA CONTENT \\
    // -------------- \\
    private static void createPlasmaContent(PlasmaState state) {

        Fluid fluid = new PlasmaBase(state.getRegistryName(), null, null);

        ResourceLocation registryName = getFluidRegistryName(MODID, fluid);

        FLUIDS.put(registryName, fluid);
        CUSTOM_FLUIDS.put(registryName, fluid);
    }

    // -------------- \\
    // DEVICE CONTENT \\
    // -------------- \\

    // Bucket \\
    private static void createBucketContent(Device device) {

        Item item = new ItemBucket(device.getID());

        ITEMS.put(item.getRegistryName(), item);
        CUSTOM_ITEMS.put(item.getRegistryName(), item);
    }

    // Pipe \\
    private static void createPipeContent(Device device) {

        if (!(device instanceof PipeDevice)) {
            throw new IllegalStateException("Pipe device is not PipeDevice: " + device.getID());
        }

        PipeDevice pipe = (PipeDevice) device;

        Block block = new BlockPipe(pipe);

        BLOCKS.put(block.getRegistryName(), block);
        CUSTOM_BLOCKS.put(block.getRegistryName(), block);
    }
}
