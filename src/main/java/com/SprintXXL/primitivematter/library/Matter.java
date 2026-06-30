package com.SprintXXL.primitivematter.library;

import com.SprintXXL.primitivematter.library.shared.Category;
import com.SprintXXL.primitivematter.library.states.MatterState;

import java.util.Arrays;
import java.util.List;

public class Matter {

    private final String id;
    private final String color;
    private final Category category;
    private final List<MatterState> matterStates;

    public Matter(
            String id,
            String color,
            Category category,
            MatterState... matterStates
    ) {
        this.id = id;
        this.color = color;
        this.category = category;
        this.matterStates = Arrays.asList(matterStates);
    }

    public String getID() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Category getCategory() {
        return category;
    }

    public List<MatterState> getMatterStates() {
        return matterStates;
    }
}
