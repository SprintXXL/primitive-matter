package com.SprintXXL.primitivematter.content;

import com.SprintXXL.primitivematter.content.base.BlockBase;
import com.SprintXXL.primitivematter.content.base.ItemBase;
import com.SprintXXL.primitivematter.content.base.specific.OreBlockBase;
import com.SprintXXL.primitivematter.library.Matter;
import com.SprintXXL.primitivematter.library.registry.MatterRegistry;
import com.SprintXXL.primitivematter.library.shared.FormEntry;
import com.SprintXXL.primitivematter.library.states.MatterState;
import com.SprintXXL.primitivematter.library.states.solid.SolidState;
import com.SprintXXL.primitivematter.library.states.solid.forms.SolidForm;
import com.SprintXXL.primitivematter.library.states.solid.forms.SolidFormGroup;
import com.SprintXXL.primitivematter.library.states.solid.forms.basic.BasicForm;
import com.SprintXXL.primitivematter.library.states.solid.forms.ore.OreForm;
import com.SprintXXL.primitivematter.library.states.solid.forms.ore.OreVariant;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class MatterContent {

    private MatterContent() {}

    private static final Map<SolidForm, Map<Matter, Item>> SOLID_ITEMS = new HashMap<>();
    private static final Map<SolidForm, Map<Matter, Item>> CUSTOM_SOLID_ITEMS = new HashMap<>();

    private static final Map<SolidForm, Map<Matter, Block>> SOLID_BLOCKS = new HashMap<>();
    private static final Map<SolidForm, Map<Matter, Block>> CUSTOM_SOLID_BLOCKS = new HashMap<>();

    public static void createContent() {

        for (Matter matter : MatterRegistry.getAllMatter()) {

            for (MatterState state : matter.getMatterStates()) {

                if (state instanceof SolidState) {
                    createSolidContent(matter, (SolidState) state);
                }
            }
        }
    }

    private static void createSolidContent(Matter matter, SolidState state) {

        for (SolidFormGroup group : state.getFormGroups()) {

            for (FormEntry<? extends SolidForm> entry : group.getForms()) {
                createSolidForm(matter, group, entry);
            }
        }
    }

    private static void createSolidForm(Matter matter, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        if (isBlockForm(form)) {
            createSolidBlock(matter, group, entry);
        }
        else {
            createSolidItem(matter, entry);
        }
    }

    private static void createSolidBlock(Matter matter, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        if (entry.hasBackingRegistryName()) {
            resolveVanillaSolidBlock(matter, entry);
            return;
        }

        if (group instanceof OreVariant && entry.getForm() == OreForm.ORE_BLOCK) {
            createCustomOreBlock(matter, (OreVariant) group, entry);
        }

        else {
            createCustomSolidBlock(matter, entry);
        }
    }

    private static void resolveVanillaSolidBlock(Matter matter, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        Block block = ForgeRegistries.BLOCKS.getValue(entry.getBackingRegistryName());

        if (block == null) {
            throw new IllegalStateException("Could not resolve block: " + entry.getBackingRegistryName());
        }

        SOLID_BLOCKS.computeIfAbsent(form, f -> new HashMap<>()).put(matter, block);
    }

    private static void createCustomOreBlock(Matter matter, OreVariant oreVariant, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        Item drop = getSolidItem(matter, oreVariant.getDrop());

        Block block = new OreBlockBase(form.getName(matter), drop, oreVariant.getHarvestLevel());

        SOLID_BLOCKS.computeIfAbsent(form, f -> new HashMap<>()).put(matter, block);

        CUSTOM_SOLID_BLOCKS.computeIfAbsent(form, f -> new HashMap<>()).put(matter, block);
    }

    private static void createCustomSolidBlock(Matter matter, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        Block block = new BlockBase(form.getName(matter));

        SOLID_BLOCKS.computeIfAbsent(form, f -> new HashMap<>()).put(matter, block);

        CUSTOM_SOLID_BLOCKS.computeIfAbsent(form, f -> new HashMap<>()).put(matter, block);
    }

    public static Collection<Block> getCustomSolidBlocks() {
        Collection<Block> blocks = new ArrayList<>();

        for (Map<Matter, Block> blockMap : CUSTOM_SOLID_BLOCKS.values()) {
            blocks.addAll(blockMap.values());
        }

        return blocks;
    }

    private static void createSolidItem(Matter matter, FormEntry<? extends SolidForm> entry) {

        if (entry.hasBackingRegistryName()) {
            resolveVanillaSolidItem(matter, entry);
        }
        else {
            createCustomSolidItem(matter, entry);
        }
    }

    private static void resolveVanillaSolidItem(Matter matter, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        Item item = ForgeRegistries.ITEMS.getValue(entry.getBackingRegistryName());

        if (item == null) {
            throw new IllegalStateException("Could not resolve item: " + entry.getBackingRegistryName());
        }

        SOLID_ITEMS.computeIfAbsent(form, f -> new HashMap<>()).put(matter, item);
    }

    private static void createCustomSolidItem(Matter matter, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        Item item = new ItemBase(form.getName(matter));

        SOLID_ITEMS.computeIfAbsent(form, f -> new HashMap<>()).put(matter, item);

        CUSTOM_SOLID_ITEMS.computeIfAbsent(form, f -> new HashMap<>()).put(matter, item);
    }

    public static Collection<Item> getCustomSolidItems() {
        Collection<Item> items = new ArrayList<>();

        for (Map<Matter, Item> itemMap : CUSTOM_SOLID_ITEMS.values()) {
            items.addAll(itemMap.values());
        }

        return items;
    }

    private static boolean isBlockForm(SolidForm form) {
        return form == BasicForm.MATTER_BLOCK || form == OreForm.ORE_BLOCK;
    }

    private static Item getSolidItem(Matter matter, SolidForm form) {

        Map<Matter, Item> itemMap = SOLID_ITEMS.get(form);

        if (itemMap == null || !itemMap.containsKey(matter)) {
            throw new IllegalStateException("Could not find solid item for: " + matter.getID() + " / " + form);
        }

        return itemMap.get(matter);
    }
}
