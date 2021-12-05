package yaboichips.mightymachines.core;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.recipes.CuttingRecipe;
import yaboichips.mightymachines.common.recipes.SmashingRecipe;

import java.util.Optional;

public class MMRecipes {
    public static final RecipeType<SmashingRecipe> SMASHING = new RecipeType<SmashingRecipe>() {
        @Override
        public <C extends Container> Optional<SmashingRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((SmashingRecipe) recipe) : Optional.empty();
        }
    };
    public static final RecipeType<CuttingRecipe> CUTTING = new RecipeType<CuttingRecipe>() {
        @Override
        public <C extends Container> Optional<CuttingRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((CuttingRecipe) recipe) : Optional.empty();
        }
    };

    static {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MightyMachines.MOD_ID, "smashing"), SMASHING);
        Registry.register(Registry.RECIPE_TYPE, MightyMachines.createResource("cutting"), CUTTING);
    }
}