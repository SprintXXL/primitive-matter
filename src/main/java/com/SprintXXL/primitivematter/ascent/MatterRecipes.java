package com.SprintXXL.primitivematter.ascent;

import com.sprintxxl.ascentresourcerecipeindex.recipes.AscentRecipeIDs;
import com.sprintxxl.ascentresourcerecipeindex.recipes.shared.Category;
import com.sprintxxl.ascentresourcerecipeindex.recipes.types.crafting.CraftingRecipe;
import com.sprintxxl.ascentresourcerecipeindex.recipes.types.crafting.data.BasicRecipeData;
import com.sprintxxl.ascentresourcerecipeindex.recipes.types.crafting.shape.ShapedRecipe;

import java.util.Map;

import static com.sprintxxl.ascentresourcerecipeindex.recipes.reciperesource.RecipeResource.resource;
import static com.sprintxxl.ascentresourcerecipeindex.recipes.reciperesource.RemainingBehavior.DAMAGE;
import static com.sprintxxl.ascentresourcerecipeindex.recipes.registry.AscentRecipeRegistry.register;
import static com.sprintxxl.ascentresourcerecipeindex.recipes.types.crafting.shape.ShapedRecipe.Pattern;
import static com.sprintxxl.ascentresourcerecipeindex.resources.definitions.ResourceCatalog.*;

public final class MatterRecipes {
    
    private MatterRecipes() {}
    
    public static void initMatterRecipes() {

        register(ASSEMBLE_IRON_GEAR);
        register(ASSEMBLE_BRONZE_GEAR);
        register(HAMMER_IRON_PLATE);
        register(HAMMER_BRONZE_PLATE);
        register(FILE_IRON_ROD);
        register(FILE_BRONZE_ROD);
        register(SAW_BRONZE_BOLT);
        register(SAW_IRON_BOLT);
        register(FILE_IRON_SCREW);
        register(FILE_BRONZE_SCREW);
        register(MORTAR_IRON_DUST);
        register(MORTAR_BRONZE_DUST);
        register(MIX_BRONZE_DUST);
    }

    public static final CraftingRecipe ASSEMBLE_IRON_GEAR =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.ASSEMBLE_IRON_GEAR,
                    Category.ASSEMBLY,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "PRP",
                                            "RPR",
                                            "PRP"
                                    ),
                                    Map.of(
                                            'P', resource(IRON_PLATE),
                                            'R', resource(IRON_ROD)
                                    )
                            ),
                            resource(IRON_GEAR)
                    )
            );
    public static final CraftingRecipe ASSEMBLE_BRONZE_GEAR =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.ASSEMBLE_BRONZE_GEAR,
                    Category.ASSEMBLY,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "PRP",
                                            "RPR",
                                            "PRP"
                                    ),
                                    Map.of(
                                            'P', resource(BRONZE_PLATE),
                                            'R', resource(BRONZE_ROD)
                                    )
                            ),
                            resource(BRONZE_GEAR)
                    )
            );
    public static final CraftingRecipe HAMMER_IRON_PLATE =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.HAMMER_IRON_PLATE,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            " H ",
                                            "III",
                                            "III"
                                    ),
                                    Map.of(
                                            'H', resource(HAMMER).withBehavior(DAMAGE),
                                            'I', resource(IRON_INGOT)
                                    )
                            ),
                            resource(IRON_PLATE)
                    )
            );
    public static final CraftingRecipe HAMMER_BRONZE_PLATE =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.HAMMER_BRONZE_PLATE,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            " H ",
                                            "III",
                                            "III"
                                    ),
                                    Map.of(
                                            'H', resource(HAMMER).withBehavior(DAMAGE),
                                            'I', resource(BRONZE_INGOT)
                                    )
                            ),
                            resource(BRONZE_PLATE)
                    )
            );
    public static final CraftingRecipe FILE_IRON_ROD =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.FILE_IRON_ROD,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "F  ",
                                            " I ",
                                            "  I"
                                    ),
                                    Map.of(
                                            'F', resource(FILE).withBehavior(DAMAGE),
                                            'I', resource(IRON_INGOT)
                                    )
                            ),
                            resource(IRON_ROD)
                    )
            );
    public static final CraftingRecipe FILE_BRONZE_ROD =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.FILE_BRONZE_ROD,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "F  ",
                                            " I ",
                                            "  I"
                                    ),
                                    Map.of(
                                            'F', resource(FILE).withBehavior(DAMAGE),
                                            'I', resource(BRONZE_INGOT)
                                    )
                            ),
                            resource(BRONZE_ROD)
                    )
            );
    public static final CraftingRecipe SAW_IRON_BOLT =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.SAW_IRON_BOLT,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "S ",
                                            " R"
                                    ),
                                    Map.of(
                                            'S', resource(SAW).withBehavior(DAMAGE),
                                            'R', resource(IRON_ROD)
                                    )
                            ),
                            resource(IRON_BOLT).count(2)
                    )
            );
    public static final CraftingRecipe SAW_BRONZE_BOLT =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.SAW_BRONZE_BOLT,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "S ",
                                            " R"
                                    ),
                                    Map.of(
                                            'S', resource(SAW).withBehavior(DAMAGE),
                                            'R', resource(BRONZE_ROD)
                                    )
                            ),
                            resource(BRONZE_BOLT).count(2)
                    )
            );
    public static final CraftingRecipe FILE_IRON_SCREW =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.FILE_IRON_SCREW,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "FB",
                                            "B "
                                    ),
                                    Map.of(
                                            'F', resource(FILE).withBehavior(DAMAGE),
                                            'B', resource(IRON_BOLT)
                                    )
                            ),
                            resource(IRON_SCREW)
                    )
            );
    public static final CraftingRecipe FILE_BRONZE_SCREW =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.FILE_BRONZE_SCREW,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "FB",
                                            "B "
                                    ),
                                    Map.of(
                                            'F', resource(FILE).withBehavior(DAMAGE),
                                            'B', resource(BRONZE_BOLT)
                                    )
                            ),
                            resource(BRONZE_SCREW)
                    )
            );
    public static final CraftingRecipe MORTAR_IRON_DUST =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.MORTAR_IRON_DUST,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "M",
                                            "I"
                                    ),
                                    Map.of(
                                            'M', resource(MORTAR).withBehavior(DAMAGE),
                                            'I', resource(IRON_INGOT)
                                    )
                            ),
                            resource(IRON_DUST)
                    )
            );
    public static final CraftingRecipe MORTAR_BRONZE_DUST =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.MORTAR_BRONZE_DUST,
                    Category.PROCESSING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "M",
                                            "I"
                                    ),
                                    Map.of(
                                            'M', resource(MORTAR).withBehavior(DAMAGE),
                                            'I', resource(BRONZE_INGOT)
                                    )
                            ),
                            resource(BRONZE_DUST)
                    )
            );
    public static final CraftingRecipe MIX_BRONZE_DUST =
            new CraftingRecipe(
                    AscentRecipeIDs.Matter.MIX_BRONZE_DUST,
                    Category.MIXING,
                    new BasicRecipeData(
                            new ShapedRecipe(
                                    Pattern(
                                            "CT",
                                            "CC"
                                    ),
                                    Map.of(
                                            'C', resource(COPPER_DUST),
                                            'T', resource(TIN_DUST)
                                    )
                            ),
                            resource(BRONZE_DUST)
                    )
            );
}
