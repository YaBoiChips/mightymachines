package yaboichips.mightymachines.core;

import net.minecraft.world.item.crafting.RecipeSerializer;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.recipes.CuttingRecipe;
import yaboichips.mightymachines.common.recipes.SmashingRecipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MMRecipeSerializers {
    public static List<RecipeSerializer<?>> serializers = new ArrayList<>();

    public static final RecipeSerializer<SmashingRecipe> SMASHING = register("smashing", new SmashingRecipe.Serializer());
    public static final RecipeSerializer<CuttingRecipe> CUTTING = register("cutting", new CuttingRecipe.Serializer());


    static @Nonnull
    <T extends RecipeSerializer<?>> T register(String id, @Nonnull T recipes) {
        recipes.setRegistryName(MightyMachines.createResource(id));
        serializers.add(recipes);
        return recipes;
    }

    public static void init() {
    }
}
