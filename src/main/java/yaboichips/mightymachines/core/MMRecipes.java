package yaboichips.mightymachines.core;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.recipes.MachineBuildingRecipe;

import java.util.Optional;

public class MMRecipes {
    public static final RecipeType<MachineBuildingRecipe> BUILDING = new RecipeType<MachineBuildingRecipe>() {
        @Override
        public <C extends Container> Optional<MachineBuildingRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((MachineBuildingRecipe) recipe) : Optional.empty();
        }
    };


    static {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MightyMachines.MOD_ID, "building"), BUILDING);
    }
}