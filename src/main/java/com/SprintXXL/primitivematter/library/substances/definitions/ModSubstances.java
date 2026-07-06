package com.SprintXXL.primitivematter.library.substances.definitions;

import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.shared.Category;
import com.SprintXXL.primitivematter.library.substances.shared.ColorRule;
import com.SprintXXL.primitivematter.library.substances.states.gas.GasState;
import com.SprintXXL.primitivematter.library.substances.states.liquid.LiquidState;
import com.SprintXXL.primitivematter.library.substances.states.plasma.PlasmaState;
import com.SprintXXL.primitivematter.library.substances.states.solid.SolidState;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.basic.BasicForms;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.industrial.IndustrialForms;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.ore.OreVariant;

import static com.SprintXXL.primitivematter.library.substances.registry.SubstanceRegistry.register;
import static com.SprintXXL.primitivematter.library.substances.shared.FormEntries.vanilla;
import static com.SprintXXL.primitivematter.library.substances.shared.FormSelection.ALL;
import static com.SprintXXL.primitivematter.library.substances.states.solid.forms.basic.BasicForm.*;
import static com.SprintXXL.primitivematter.library.substances.states.solid.forms.industrial.IndustrialForm.ROD;

public final class ModSubstances {

    private ModSubstances() {}

    public static void initModSubstances() {

        register(TEST_MATTER);

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
        register(WATER);
        register(LAVA);

        // ORGANIC MATTER \\
        register(BONE);
        register(WOOD);

        // CHEMICAL MATTER \\
        register(OXYGEN);
    }

    public static final Substance TEST_MATTER =
            new Substance(
                    SubstanceIDs.TEST_MATTER,
                    "#805a00",
                    Category.CHEMICAL,
                    new LiquidState("test_liquid"),
                    new GasState("test_gas"),
                    new PlasmaState("test_plasma")
            );

    // METAL MATTER \\
    public static final Substance IRON =
            new Substance(
                    SubstanceIDs.IRON,
                    "#D8D8D8",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "iron_block"),
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
    public static final Substance COPPER =
            new Substance(
                    SubstanceIDs.COPPER,
                    "#E6834D",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    SUBSTANCE_BLOCK,
                                    INGOT,
                                    NUGGET,
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Substance TIN =
            new Substance(
                    SubstanceIDs.TIN,
                    "#8B9294",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    SUBSTANCE_BLOCK,
                                    INGOT,
                                    NUGGET,
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Substance GOLD =
            new Substance(
                    SubstanceIDs.GOLD,
                    "#FFF144",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "gold_block"),
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
    public static final Substance BRONZE =
            new Substance(
                    SubstanceIDs.BRONZE,
                    "#D48648",
                    Category.METAL,
                    new SolidState(
                            new BasicForms(
                                    SUBSTANCE_BLOCK,
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
    public static final Substance REDSTONE =
            new Substance(
                    SubstanceIDs.REDSTONE,
                    "#720000",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "redstone_block"),
                                    vanilla(DUST, "redstone")
                            ),
                            new OreVariant()
                    )
            );
    public static final Substance DIAMOND =
            new Substance(
                    SubstanceIDs.DIAMOND,
                    "#68D0D6",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "diamond_block"),
                                    vanilla(SUBSTANCE_ITEM, "diamond"),
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Substance COAL =
            new Substance(
                    SubstanceIDs.COAL,
                    "#0C0E12",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "coal_block"),
                                    vanilla(SUBSTANCE_ITEM, "coal"),
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Substance LIGNITE_COAL =
            new Substance(
                    SubstanceIDs.LIGNITE_COAL,
                    "#1E100C",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    SUBSTANCE_BLOCK,
                                    SUBSTANCE_ITEM,
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Substance CINNABAR =
            new Substance(
                    SubstanceIDs.CINNABAR,
                    "#9E2A16",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    SUBSTANCE_BLOCK,
                                    SUBSTANCE_ITEM,
                                    DUST
                            ),
                            new OreVariant()
                    )
            );
    public static final Substance FLINT =
            new Substance(
                    SubstanceIDs.FLINT,
                    "#626A73",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_ITEM, "flint"),
                                    DUST
                            )
                    )
            );
    public static final Substance CASSITERITE =
            new Substance(
                    SubstanceIDs.CASSITERITE,
                    "#65584D",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );
    public static final Substance CHALCOPYRITE =
            new Substance(
                    SubstanceIDs.CHALCOPYRITE,
                    "#C77A2C",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );
    public static final Substance MAGNETITE =
            new Substance(
                    SubstanceIDs.MAGNETITE,
                    "#2D394C",
                    Category.MINERAL,
                    new SolidState(
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );
    public static final Substance PYRITE =
            new Substance(
                    SubstanceIDs.PYRITE,
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
    public static final Substance STONE =
            new Substance(
                    SubstanceIDs.STONE,
                    "#7D7D7D",
                    Category.GEOLOGICAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "stone"),
                                    DUST
                            )
                    )
            );
    public static final Substance SAND =
            new Substance(
                    SubstanceIDs.SAND,
                    "#C2B280",
                    Category.GEOLOGICAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "sand"),
                                    DUST
                            )
                    )
            );
    public static final Substance GRAVEL =
            new Substance(
                    SubstanceIDs.GRAVEL,
                    "#847F7F",
                    Category.GEOLOGICAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "gravel"),
                                    DUST
                            )
                    )
            );
    public static final Substance OBSIDIAN =
            new Substance(
                    SubstanceIDs.OBSIDIAN,
                    "#282038",
                    Category.GEOLOGICAL,
                    new SolidState(
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "obsidian"),
                                    DUST
                            )
                    )
            );
    public static final Substance WATER =
            new Substance(
                    SubstanceIDs.WATER,
                    "#858585",
                    Category.GEOLOGICAL,
                    new LiquidState("water").vanilla(),
                    new GasState("steam")
            );
    public static final Substance LAVA =
            new Substance(
                    SubstanceIDs.LAVA,
                    null,
                    Category.GEOLOGICAL,
                    new LiquidState("lava").vanilla()
            );

    // ORGANIC MATTER \\
    public static final Substance BONE =
            new Substance(
                    SubstanceIDs.BONE,
                    "#E4E2D2",
                    Category.ORGANIC,
                    new SolidState(
                            new BasicForms(
                                    SUBSTANCE_BLOCK,
                                    vanilla(SUBSTANCE_ITEM, "bone"),
                                    DUST
                            )
                    )
            );
    public static final Substance WOOD =
            new Substance(
                    SubstanceIDs.WOOD,
                    "#785634",
                    Category.ORGANIC,
                    new SolidState(
                            new IndustrialForms(
                                    vanilla(ROD, "stick")
                            )
                    )
            );

    // CHEMICAL MATTER \\
    public static final Substance OXYGEN =
            new Substance(
                    SubstanceIDs.OXYGEN,
                    "#5d7580",
                    Category.CHEMICAL,
                    new LiquidState("liquid_oxygen"),
                    new GasState("oxygen")
            );
}
