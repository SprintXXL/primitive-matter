package com.SprintXXL.primitivematter.library.registry;

import com.SprintXXL.primitivematter.library.Matter;

import java.util.*;

import static com.SprintXXL.primitivematter.content.MatterContent.createContent;
import static com.SprintXXL.primitivematter.library.definitions.ModMatter.initMatterDefinitions;

public final class MatterRegistry {

    private MatterRegistry() {}

    private static final List<Matter> ALL_MATTER = new ArrayList<>();

    private static final Map<String, Matter> MATTER_MAP = new HashMap<>();

    public static Matter getMatter(String id) {
        return MATTER_MAP.get(id);
    }

    public static List<Matter> getAllMatter() {
        return Collections.unmodifiableList(ALL_MATTER);
    }

    public static void register(Matter matter) {
        ALL_MATTER.add(matter);
        MATTER_MAP.put(matter.getID(), matter);
    }

    public static void initAllMatter() {

        initMatterDefinitions();
        createContent();
    }
}
