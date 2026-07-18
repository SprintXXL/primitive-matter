package com.SprintXXL.primitivematter.library.substances.definitions;

import com.SprintXXL.primitivematter.library.substances.Substance;
import com.SprintXXL.primitivematter.library.substances.shared.SubstanceCategory;
import com.SprintXXL.primitivematter.library.substances.shared.ColorRule;
import com.SprintXXL.primitivematter.library.substances.states.StateProperties;
import com.SprintXXL.primitivematter.library.substances.states.gas.GasState;
import com.SprintXXL.primitivematter.library.substances.states.liquid.LiquidState;
import com.SprintXXL.primitivematter.library.substances.states.plasma.PlasmaState;
import com.SprintXXL.primitivematter.library.substances.states.solid.SolidState;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.basic.BasicForms;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.industrial.IndustrialForms;
import com.SprintXXL.primitivematter.library.substances.states.solid.forms.ore.OreVariant;
import com.sprintxxl.ascenthub.definitions.DefinitionRegistrar;

import static com.SprintXXL.primitivematter.library.substances.shared.FormEntries.vanilla;
import static com.SprintXXL.primitivematter.library.substances.shared.FormSelection.ALL;
import static com.SprintXXL.primitivematter.library.substances.states.solid.forms.basic.BasicForm.*;
import static com.SprintXXL.primitivematter.library.substances.states.solid.forms.industrial.IndustrialForm.ROD;

public final class ModSubstances {

    private ModSubstances() {}

    private static final int NEGATIVE_INFINITY = Integer.MIN_VALUE;
    private static final int POSITIVE_INFINITY = Integer.MAX_VALUE;

    public static void initSubstanceDefinitions(DefinitionRegistrar<Substance> registrar) {

        registrar.register(TEST_MATTER);

        // METAL MATTER \\
        registrar.register(IRON);
        registrar.register(COPPER);
        registrar.register(TIN);
        registrar.register(GOLD);
        registrar.register(BRONZE);
        registrar.register(STEEL);

        // MINERAL MATTER \\
        registrar.register(REDSTONE);
        registrar.register(DIAMOND);
        registrar.register(COAL);
        registrar.register(LIGNITE_COAL);
        registrar.register(CINNABAR);
        registrar.register(FLINT);
        registrar.register(CASSITERITE);
        registrar.register(CHALCOPYRITE);
        registrar.register(MAGNETITE);
        registrar.register(PYRITE);

        // GEOLOGICAL MATTER \\
        registrar.register(STONE);
        registrar.register(SAND);
        registrar.register(GRAVEL);
        registrar.register(OBSIDIAN);
        registrar.register(WATER);
        registrar.register(LAVA);
        registrar.register(CLAY);

        // ORGANIC MATTER \\
        registrar.register(BONE);
        registrar.register(WOOD);

        // CHEMICAL MATTER \\
        registrar.register(OXYGEN);
    }

    public static final Substance TEST_MATTER =
            new Substance(
                    SubstanceIDs.TEST_MATTER,
                    "#805a00",
                    SubstanceCategory.CHEMICAL,
                    new LiquidState(
                            new StateProperties(
                                    NEGATIVE_INFINITY,
                                    999
                            ),
                            "test_liquid"
                    ),
                    new GasState(
                            new StateProperties(
                                    1000,
                                    1999
                            ),
                            "test_gas"
                    ),
                    new PlasmaState(
                            new StateProperties(
                                    2000,
                                    POSITIVE_INFINITY
                            ),
                            "test_plasma"
                    )
            );

    // METAL MATTER \\
    public static final Substance IRON =
            new Substance(
                    SubstanceIDs.IRON,
                    "#808080",
                    SubstanceCategory.METAL,
                    new SolidState(
                            new StateProperties(
                                    NEGATIVE_INFINITY,
                                    1499
                            ),
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
                    ),
                    new LiquidState(
                            new StateProperties(
                                    1500,
                                    POSITIVE_INFINITY
                            ),
                            "molten_iron"
                    )
            );
    public static final Substance COPPER =
            new Substance(
                    SubstanceIDs.COPPER,
                    "#80482a",
                    SubstanceCategory.METAL,
                    new SolidState(
                            new StateProperties(
                                    -1,
                                    999
                            ),
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
                    "#787e80",
                    SubstanceCategory.METAL,
                    new SolidState(
                            new StateProperties(
                                    -1,
                                    999
                            ),
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
                    "#807a22",
                    SubstanceCategory.METAL,
                    new SolidState(
                            new StateProperties(
                                    -1,
                                    999
                            ),
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
                    "#80512b",
                    SubstanceCategory.METAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
    public static final Substance STEEL =
            new Substance(
                    SubstanceIDs.STEEL,
                    "#807a7a",
                    SubstanceCategory.METAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
                    "#800000",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
                    "#3e7c80",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
                    "#808080",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
                    "#804433",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
                    "#802312",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
                    "#6c7580",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            new BasicForms(
                                    vanilla(SUBSTANCE_ITEM, "flint"),
                                    DUST
                            )
                    )
            );
    public static final Substance CASSITERITE =
            new Substance(
                    SubstanceIDs.CASSITERITE,
                    "#806f61",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );
    public static final Substance CHALCOPYRITE =
            new Substance(
                    SubstanceIDs.CHALCOPYRITE,
                    "#804e1c",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );
    public static final Substance MAGNETITE =
            new Substance(
                    SubstanceIDs.MAGNETITE,
                    "#2d394c",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            new BasicForms(
                                    DUST
                            ),
                            new OreVariant().colorRule(ColorRule.DEFAULT)
                    )
            );
    public static final Substance PYRITE =
            new Substance(
                    SubstanceIDs.PYRITE,
                    "#806525",
                    SubstanceCategory.MINERAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
                    "#808080",
                    SubstanceCategory.GEOLOGICAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "stone"),
                                    DUST
                            )
                    )
            );
    public static final Substance SAND =
            new Substance(
                    SubstanceIDs.SAND,
                    "#807554",
                    SubstanceCategory.GEOLOGICAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "sand"),
                                    DUST
                            )
                    )
            );
    public static final Substance GRAVEL =
            new Substance(
                    SubstanceIDs.GRAVEL,
                    "#807a7a",
                    SubstanceCategory.GEOLOGICAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "gravel"),
                                    DUST
                            )
                    )
            );
    public static final Substance OBSIDIAN =
            new Substance(
                    SubstanceIDs.OBSIDIAN,
                    "#5b4980",
                    SubstanceCategory.GEOLOGICAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "obsidian"),
                                    DUST
                            )
                    )
            );
    public static final Substance WATER =
            new Substance(
                    SubstanceIDs.WATER,
                    "#7c7e80",
                    SubstanceCategory.GEOLOGICAL,
                    new LiquidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            "water"
                    ).vanilla(),
                    new GasState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            "steam"
                    )
            );
    public static final Substance LAVA =
            new Substance(
                    SubstanceIDs.LAVA,
                    null,
                    SubstanceCategory.GEOLOGICAL,
                    new LiquidState(
                            new StateProperties(
                                    1000,
                                    3000
                            ),
                            "lava"
                    ).vanilla()
            );
    public static final Substance CLAY =
            new Substance(
                    SubstanceIDs.CLAY,
                    "#606c80",
                    SubstanceCategory.GEOLOGICAL,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            new BasicForms(
                                    vanilla(SUBSTANCE_BLOCK, "clay_block"),
                                    vanilla(SUBSTANCE_ITEM, "clay")
                            )
                    )
            );

    // ORGANIC MATTER \\
    public static final Substance BONE =
            new Substance(
                    SubstanceIDs.BONE,
                    "#807e75",
                    SubstanceCategory.ORGANIC,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
                    "#805b37",
                    SubstanceCategory.ORGANIC,
                    new SolidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
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
                    SubstanceCategory.CHEMICAL,
                    new LiquidState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            "liquid_oxygen"
                    ),
                    new GasState(
                            new StateProperties(
                                    0,
                                    0
                            ),
                            "oxygen"
                    )
            );
}
