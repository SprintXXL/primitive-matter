package com.SprintXXL.primitivematter.library.definitions;

import com.SprintXXL.primitivematter.library.Matter;
import com.SprintXXL.primitivematter.library.shared.Category;
import com.SprintXXL.primitivematter.library.shared.ColorRule;
import com.SprintXXL.primitivematter.library.states.solid.SolidState;
import com.SprintXXL.primitivematter.library.states.solid.forms.basic.BasicForms;
import com.SprintXXL.primitivematter.library.states.solid.forms.industrial.IndustrialForms;
import com.SprintXXL.primitivematter.library.states.solid.forms.ore.OreVariant;

import static com.SprintXXL.primitivematter.library.registry.MatterRegistry.register;
import static com.SprintXXL.primitivematter.library.shared.FormEntries.vanilla;
import static com.SprintXXL.primitivematter.library.shared.FormSelection.ALL;
import static com.SprintXXL.primitivematter.library.states.solid.forms.basic.BasicForm.*;
import static com.SprintXXL.primitivematter.library.states.solid.forms.industrial.IndustrialForm.ROD;

public final class ModMatter {

    private ModMatter() {}

    public static void initMatterDefinitions() {

        // METAL MATTER \\
        register(IRON);
        register(COPPER);
        register(TIN);
        register(GOLD);
        register(BRONZE);

        // MINERAL MATTER \\
        register(REDSTONE);
        register(DIAMOND);
        register(COAL);
        register(LIGNITE_COAL);
        register(CINNABAR);
        register(FLINT);
        register(CASSITERITE);
        register(CHALCOPYRITE);
        register(MAGNETITE);
        register(PYRITE);

        // GEOLOGICAL MATTER \\
        register(STONE);
        register(SAND);
        register(GRAVEL);
        register(OBSIDIAN);

        // ORGANIC MATTER \\
        register(BONE);
        register(WOOD);

        // CHEMICAL MATTER \\
    }

    // METAL MATTER \\
    public static final Matter IRON =
            new Matter(
                    MatterIDs.IRON,
                    "#979797",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_BLOCK, "iron_block"),
                                    vanilla(INGOT, "iron_ingot"),
                                    vanilla(NUGGET, "iron_nugget"),
                                    DUST
                            ),
                            new IndustrialForms(
                                    ALL
                            ),
                            new OreVariant()
                    )
            );
    public static final Matter COPPER =
            new Matter(
                    MatterIDs.COPPER,
                    "#E6834D",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    MATTER_BLOCK,
                                    INGOT,
                                    NUGGET,
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Matter TIN =
            new Matter(
                    MatterIDs.TIN,
                    "#8B9294",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    MATTER_BLOCK,
                                    INGOT,
                                    NUGGET,
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Matter GOLD =
            new Matter(
                    MatterIDs.GOLD,
                    "#C87D0A",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_BLOCK, "gold_block"),
                                    vanilla(INGOT, "gold_ingot"),
                                    vanilla(NUGGET, "gold_nugget"),
                                    DUST
                            ),
                            new IndustrialForms(
                                    ALL
                            ),
                            new OreVariant()
                    )
            );
    public static final Matter BRONZE =
            new Matter(
                    MatterIDs.BRONZE,
                    "#D48648",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    MATTER_BLOCK,
                                    INGOT,
                                    NUGGET,
                                    DUST
                            ),
                            new IndustrialForms(
                                    ALL
                            )
                    )
            );

    // MINERAL MATTER \\
    public static final Matter REDSTONE =
            new Matter(
                    MatterIDs.REDSTONE,
                    "#BB1414",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_BLOCK, "redstone_block"),
                                    vanilla(MATTER_ITEM, "redstone"),
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Matter DIAMOND =
            new Matter(
                    MatterIDs.DIAMOND,
                    "#68D0D6",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_BLOCK, "diamond_block"),
                                    vanilla(MATTER_ITEM, "diamond"),
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Matter COAL =
            new Matter(
                    MatterIDs.COAL,
                    "#0C0E12",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_BLOCK, "coal_block"),
                                    vanilla(MATTER_ITEM, "coal"),
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Matter LIGNITE_COAL =
            new Matter(
                    MatterIDs.LIGNITE_COAL,
                    "#1E100C",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    MATTER_BLOCK,
                                    MATTER_ITEM,
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Matter CINNABAR =
            new Matter(
                    MatterIDs.CINNABAR,
                    "#9E2A16",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    MATTER_BLOCK,
                                    MATTER_ITEM,
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Matter FLINT =
            new Matter(
                    MatterIDs.FLINT,
                    "#626A73",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_ITEM, "flint"),
                                    DUST
                            )
                    )
            );
    public static final Matter CASSITERITE =
            new Matter(
                    MatterIDs.CASSITERITE,
                    "#65584D",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );
    public static final Matter CHALCOPYRITE =
            new Matter(
                    MatterIDs.CHALCOPYRITE,
                    "#C77A2C",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );
    public static final Matter MAGNETITE =
            new Matter(
                    MatterIDs.MAGNETITE,
                    "#2D394C",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );
    public static final Matter PYRITE =
            new Matter(
                    MatterIDs.PYRITE,
                    "#BE9637",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );

    // GEOLOGICAL MATTER \\
    public static final Matter STONE =
            new Matter(
                    MatterIDs.STONE,
                    "#7D7D7D",
                    Category.GEOLOGICAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_BLOCK, "stone"),
                                    DUST
                            )
                    )
            );
    public static final Matter SAND =
            new Matter(
                    MatterIDs.SAND,
                    "#C2B280",
                    Category.GEOLOGICAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_BLOCK, "sand"),
                                    DUST
                            )
                    )
            );
    public static final Matter GRAVEL =
            new Matter(
                    MatterIDs.GRAVEL,
                    "#847F7F",
                    Category.GEOLOGICAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_BLOCK, "gravel"),
                                    DUST
                            )
                    )
            );
    public static final Matter OBSIDIAN =
            new Matter(
                    MatterIDs.OBSIDIAN,
                    "#282038",
                    Category.GEOLOGICAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(MATTER_BLOCK, "obsidian"),
                                    DUST
                            )
                    )
            );

    // ORGANIC MATTER \\
    public static final Matter BONE =
            new Matter(
                    MatterIDs.BONE,
                    "#E4E2D2",
                    Category.ORGANIC,
                    new SolidState(
                            new BasicForms(
                                    MATTER_BLOCK,
                                    vanilla(MATTER_ITEM, "bone"),
                                    DUST
                            )
                    )
            );
    public static final Matter WOOD =
            new Matter(
                    MatterIDs.WOOD,
                    "#785634",
                    Category.ORGANIC,
                    new SolidState(
                            new IndustrialForms(
                                    vanilla(ROD, "stick")
                            )
                    )
            );

    // CHEMICAL MATTER \\
}
