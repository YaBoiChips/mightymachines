package yaboichips.mightymachines.common.recipes;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import yaboichips.mightymachines.core.MMRecipeSerializers;
import yaboichips.mightymachines.core.MMRecipes;

public class SmashingRecipe implements Recipe<Container> {

    private final Ingredient base;
    private final ItemStack result;
    private final ResourceLocation recipeId;

    public SmashingRecipe(ResourceLocation recipeId, Ingredient base, ItemStack result) {
        this.recipeId = recipeId;
        this.base = base;
        this.result = result;
    }

    @Override
    public boolean matches(Container inv, Level world) {
        return this.base.test(inv.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundtag = container.getItem(0).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }
        return itemstack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MMRecipeSerializers.SMASHING;
    }

    @Override
    public RecipeType<?> getType() {
        return MMRecipes.SMASHING;
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SmashingRecipe>{

        @Override
        public SmashingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new SmashingRecipe(recipeId, ingredient, itemstack);
        }

        @Override
        public SmashingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            return new SmashingRecipe(resourceLocation, ingredient, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SmashingRecipe recipe) {
            recipe.base.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
