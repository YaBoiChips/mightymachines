package yaboichips.mightymachines.core;

import net.minecraft.world.item.crafting.RecipeSerializer;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.recipes.MachineBuildingRecipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MMRecipeSerializers {
    public static List<RecipeSerializer<?>> serializers = new ArrayList<>();

    public static final RecipeSerializer<MachineBuildingRecipe> BUILDING = register("building", new MachineBuildingRecipe.Serializer());


    static @Nonnull
    <T extends RecipeSerializer<?>> T register(String id, @Nonnull T recipes) {
        recipes.setRegistryName(MightyMachines.createResource(id));
        serializers.add(recipes);
        return recipes;
    }

    public static void init() {
    }
}
