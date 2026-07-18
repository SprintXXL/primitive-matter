package com.SprintXXL.primitivematter.tools;

import com.SprintXXL.primitivematter.library.devices.Device;
import com.SprintXXL.primitivematter.library.devices.registry.DeviceRegistry;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.SprintXXL.primitivematter.library.devices.definitions.ModDevices.initDeviceDefinitions;
import static com.SprintXXL.primitivematter.library.substances.definitions.ModSubstances.initSubstanceDefinitions;

public class ResourceFileGenerator {

    static void main() {

        initSubstanceDefinitions(SubstanceRegistry::register);
        initDeviceDefinitions(DeviceRegistry::register);

        cleanGeneratedResources();

        for (Substance substance : SubstanceRegistry.getAllSubstances()) {

            for (SubstanceState state : substance.getSubstanceStates()) {

                if (state instanceof SolidState) {
                    generateSolidState(substance, (SolidState) state);
                }

                if (state instanceof LiquidState) {
                    generateLiquidState(substance, (LiquidState) state);
                }

                if (state instanceof GasState) {
                    generateGasState(substance, (GasState) state);
                }

                if (state instanceof PlasmaState) {
                    generatePlasmaState(substance, (PlasmaState) state);
                }
            }
        }

        for (Device device : DeviceRegistry.getAllDevices()) {

                generateDeviceModel(device);
        }

        generateLang();
    }

    // -------------- \\
    // LANG GENERATOR \\
    // -------------- \\
    private static void generateLang() {

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/lang",
                "en_us.lang"
        );

        StringBuilder lang = new StringBuilder();

        for (Substance substance : SubstanceRegistry.getAllSubstances()) {

            for (SubstanceState state : substance.getSubstanceStates()) {

                if (state instanceof SolidState) {

                    SolidState solidState = (SolidState) state;

                    for (SolidFormGroup group : solidState.getFormGroups()) {

                        for (FormEntry<? extends SolidForm> entry : group.getForms()) {

                            if (!shouldGenerate(entry)) {
                                continue;
                            }

                            SolidForm form = entry.getForm();
                            String registryName = form.getName(substance);

                            if (isBlockForm(form)) {
                                lang.append("tile.primitivematter.");
                            }

                            else {
                                lang.append("item.primitivematter.");
                            }

                            lang.append(registryName)
                                    .append(".name=")
                                    .append(toDisplayName(registryName))
                                    .append("\n");
                        }
                    }
                }
            }
        }

        for (Device device : DeviceRegistry.getAllDevices()) {

            if (device.createsBlock()) {
                lang.append("tile.primitivematter.");
            } else {
                lang.append("item.primitivematter.");
            }

            lang.append(device.getID())
                    .append(".name=")
                    .append(toDisplayName(device.getID()))
                    .append("\n");
        }

        writeFile(path, lang.toString());
    }

    // --------------- \\
    // SOLID GENERATOR \\
    // --------------- \\
    private static void generateSolidState(Substance substance, SolidState state) {

        for (SolidFormGroup group : state.getFormGroups()) {

            for (FormEntry<? extends SolidForm> entry : group.getForms()) {
                generateSolidForm(substance, group, entry);
            }
        }
    }

    private static void generateSolidForm(Substance substance, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        if (!shouldGenerate(entry)) {
            return;
        }

        if (isBlockForm(form)) {
            generateBlockModel(substance, group, entry);
            generateBlockStateModel(substance, group, entry);
            generateItemBlockModel(substance, group, entry);
        }
        else {
            generateItemModel(substance, group, entry);
        }
    }

    private static void generateBlockModel(Substance substance, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        String registryName = entry.getForm().getName(substance);
        String texturePath = getTexturePath(substance, group, entry);

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/models/block/generated",
                registryName + ".json"
        );

        String json =
                "{\n" +
                        "  \"parent\": \"block/cube_all\",\n" +
                        "  \"textures\": {\n" +
                        "    \"all\": \"" + texturePath + registryName + "\"\n" +
                        "  }\n" +
                        "}";

        writeFile(path, json);
    }

    private static void generateBlockStateModel(Substance substance, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        String registryName = entry.getForm().getName(substance);

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/blockstates",
                registryName + ".json"
        );

        String json =
                "{\n" +
                        "  \"variants\": {\n" +
                        "    \"normal\": {\n" +
                        "      \"model\": \"primitivematter:generated/" + registryName + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n";

        writeFile(path, json);
    }

    private static void generateItemBlockModel(Substance substance, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        String registryName = entry.getForm().getName(substance);

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/models/item/generated",
                registryName + ".json"
        );

        String json =
                "{\n" +
                        "  \"parent\": \"primitivematter:block/generated/" + registryName + "\"\n" +
                        "}\n";

        writeFile(path, json);
    }

    private static void generateItemModel(Substance substance, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        String registryName = entry.getForm().getName(substance);
        String texturePath = getTexturePath(substance, group, entry);

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/models/item/generated",
                registryName + ".json"
        );

        String json =
                "{\n" +
                        "  \"parent\": \"item/generated\",\n" +
                        "  \"textures\": {\n" +
                        "    \"layer0\": \"" + texturePath + registryName + "\"\n" +
                        "  }\n" +
                        "}\n";

        writeFile(path, json);
    }

    // SOLID HELPERS \\
    private static boolean shouldGenerate(FormEntry<? extends SolidForm> entry) {
        return !entry.hasBackingRegistryName();
    }

    private static String getTexturePath(Substance substance, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        if (entry.getForm() == BasicForm.SUBSTANCE_ITEM) {
            return "primitivematter:handmade/";
        }
        else {
            return "primitivematter:generated/";
        }
    }

    private static boolean isBlockForm(SolidForm form) {
        return form == BasicForm.SUBSTANCE_BLOCK || form == OreForm.ORE_BLOCK;
    }

    // ---------------- \\
    // LIQUID GENERATOR \\
    // ---------------- \\
    private static void generateLiquidState(Substance substance, LiquidState state) {

        if (state.isVanilla()) {
            return;
        }

        generateLiquidBlockState(substance, state);
    }

    private static void generateLiquidBlockState(Substance substance, LiquidState state) {

        String registryName = state.getRegistryName();

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/blockstates",
                registryName + ".json"
        );

        String json =
                "{\n" +
                        "  \"forge_marker\": 1,\n" +
                        "  \"defaults\": {\n" +
                        "    \"model\": \"forge:fluid\",\n" +
                        "    \"custom\": {\n" +
                        "      \"fluid\": \"" + registryName + "\"\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"variants\": {\n" +
                        "    \"level=0\": [{}],\n" +
                        "    \"level=1\": [{}],\n" +
                        "    \"level=2\": [{}],\n" +
                        "    \"level=3\": [{}],\n" +
                        "    \"level=4\": [{}],\n" +
                        "    \"level=5\": [{}],\n" +
                        "    \"level=6\": [{}],\n" +
                        "    \"level=7\": [{}],\n" +
                        "    \"level=8\": [{}],\n" +
                        "    \"level=9\": [{}],\n" +
                        "    \"level=10\": [{}],\n" +
                        "    \"level=11\": [{}],\n" +
                        "    \"level=12\": [{}],\n" +
                        "    \"level=13\": [{}],\n" +
                        "    \"level=14\": [{}],\n" +
                        "    \"level=15\": [{}]\n" +
                        "  }\n" +
                        "}\n";

        writeFile(path, json);
    }

    // ------------- \\
    // GAS GENERATOR \\
    // ------------- \\
    private static void generateGasState(Substance substance, GasState state) {}

    // ---------------- \\
    // PLASMA GENERATOR \\
    // ---------------- \\
    private static void generatePlasmaState(Substance substance, PlasmaState state) {}

    // ---------------- \\
    // DEVICE GENERATOR \\
    // ---------------- \\
    private static void generateDeviceModel(Device device) {

        String registryName = device.getID();

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/models/item/generated",
                registryName + ".json"
        );

        String json =
                "{\n" +
                        "  \"parent\": \"item/generated\",\n" +
                        "  \"textures\": {\n" +
                        "    \"layer0\": \"primitivematter:generated/" + registryName + "\"\n" +
                        "  }\n" +
                        "}\n";

        writeFile(path, json);
    }

    // -------------- \\
    // GLOBAL HELPERS \\
    // -------------- \\

    private static String getTextureName(Substance substance, String state, String form) {
        return state + "_" + substance.getID() + "_" + form;
    }

    private static String toDisplayName(String registryName) {

        String[] parts = registryName.split("_");

        StringBuilder name = new StringBuilder();

        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }

            name.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1))
                    .append(" ");
        }

        return name.toString().trim();
    }

    private static void writeFile(Path path, String contents) {

        try {
            Files.createDirectories(path.getParent());

            Files.write(
                    path,
                    contents.getBytes(StandardCharsets.UTF_8)
            );

            System.out.println("Generated: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cleanGeneratedResources() {

        deleteDirectoryContents(Paths.get("src/main/resources/assets/primitivematter/models/item/generated"));
        deleteDirectoryContents(Paths.get("src/main/resources/assets/primitivematter/models/block/generated"));
        deleteDirectoryContents(Paths.get("src/main/resources/assets/primitivematter/lang"));
    }

    private static void deleteDirectoryContents(Path directory) {

        if (!Files.exists(directory)) {
            return;
        }

        try {
            Files.walk(directory)
                    .filter(path -> !path.equals(directory))
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            System.out.println("Deleted: " + path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
