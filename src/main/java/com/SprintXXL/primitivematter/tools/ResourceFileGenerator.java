package com.SprintXXL.primitivematter.tools;

import com.SprintXXL.primitivematter.library.Matter;
import com.SprintXXL.primitivematter.library.definitions.ModMatter;
import com.SprintXXL.primitivematter.library.registry.MatterRegistry;
import com.SprintXXL.primitivematter.library.shared.FormEntry;
import com.SprintXXL.primitivematter.library.states.MatterState;
import com.SprintXXL.primitivematter.library.states.solid.SolidState;
import com.SprintXXL.primitivematter.library.states.solid.forms.SolidForm;
import com.SprintXXL.primitivematter.library.states.solid.forms.SolidFormGroup;
import com.SprintXXL.primitivematter.library.states.solid.forms.basic.BasicForm;
import com.SprintXXL.primitivematter.library.states.solid.forms.ore.OreForm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.SprintXXL.primitivematter.library.states.solid.forms.basic.BasicForm.MATTER_BLOCK;

public class ResourceFileGenerator {

    static void main() {

        ModMatter.initMatterDefinitions();

        cleanGeneratedResources();

        for (Matter matter : MatterRegistry.getAllMatter()) {

            for (MatterState state : matter.getMatterStates()) {

                if (state instanceof SolidState) {
                    generateSolidState(matter, (SolidState) state);
                }
            }
        }

        generateLang();
    }

    private static void generateSolidState(Matter matter, SolidState state) {

        for (SolidFormGroup group : state.getFormGroups()) {

            for (FormEntry<? extends SolidForm> entry : group.getForms()) {
                generateSolidForm(matter, group, entry);
            }
        }
    }

    private static void generateSolidForm(Matter matter, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        SolidForm form = entry.getForm();

        if (!shouldGenerate(entry)) {
            return;
        }

        if (isBlockForm(form)) {
            generateBlockModel(matter, group, entry);
            generateBlockStateModel(matter, group, entry);
            generateItemBlockModel(matter, group, entry);
        }
        else {
            generateItemModel(matter, group, entry);
        }
    }

    private static void generateBlockModel(Matter matter, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        String registryName = entry.getForm().getName(matter);
        String texturePath = getTexturePath(matter, group, entry);

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/models/block",
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

    private static void generateBlockStateModel(Matter matter, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        String registryName = entry.getForm().getName(matter);

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/blockstates",
                registryName + ".json"
        );

        String json =
                "{\n" +
                        "  \"variants\": {\n" +
                        "    \"normal\": {\n" +
                        "      \"model\": \"primitivematter:" + registryName + "\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n";

        writeFile(path, json);
    }

    private static void generateItemBlockModel(Matter matter, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        String registryName = entry.getForm().getName(matter);

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/models/item",
                registryName + ".json"
        );

        String json =
                "{\n" +
                        "  \"parent\": \"primitivematter:block/" + registryName + "\"\n" +
                        "}\n";

        writeFile(path, json);
    }

    private static void generateItemModel(Matter matter, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {

        String registryName = entry.getForm().getName(matter);
        String texturePath = getTexturePath(matter, group, entry);

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/models/item",
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

    private static void generateLang() {

        Path path = Paths.get(
                "src/main/resources/assets/primitivematter/lang",
                "en_us.lang"
        );

        StringBuilder lang = new StringBuilder();

        for (Matter matter : MatterRegistry.getAllMatter()) {

            for (MatterState state : matter.getMatterStates()) {

                if (state instanceof SolidState) {

                    SolidState solidState = (SolidState) state;

                    for (SolidFormGroup group : solidState.getFormGroups()) {

                        for (FormEntry<? extends SolidForm> entry : group.getForms()) {

                            if (!shouldGenerate(entry)) {
                                continue;
                            }

                            SolidForm form = entry.getForm();
                            String registryName = form.getName(matter);

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

        writeFile(path, lang.toString());
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

    private static boolean shouldGenerate(FormEntry<? extends SolidForm> entry) {
        return !entry.hasBackingRegistryName();
    }

    private static String getTexturePath(Matter matter, SolidFormGroup group, FormEntry<? extends SolidForm> entry) {
        return "primitivematter:generated/";
    }

    private static boolean isBlockForm(SolidForm form) {
        return form == BasicForm.MATTER_BLOCK || form == OreForm.ORE_BLOCK;
    }

    private static void cleanGeneratedResources() {

        deleteDirectoryContents(Paths.get("src/main/resources/assets/primitivematter/models/item"));
        deleteDirectoryContents(Paths.get("src/main/resources/assets/primitivematter/models/block"));
        deleteDirectoryContents(Paths.get("src/main/resources/assets/primitivematter/blockstates"));
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
