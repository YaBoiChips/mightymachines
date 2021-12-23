package yaboichips.mightymachines.common.items.guns;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class RubyGun extends MiningGun{

    public RubyGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.fail(stack);
        BlockPos blockLookingAt = this.getBlockLookingAt(player);
        if (blockLookingAt !=null) {
            ItemStack stack2 = world.getBlockState(blockLookingAt).getBlock().asItem().getDefaultInstance();
            Optional<SmeltingRecipe> optional = world.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack2), world);
            if (optional.isPresent()){
                ItemStack result = optional.get().getResultItem();
                world.destroyBlock(blockLookingAt, false);
                ItemEntity item = new ItemEntity(world, blockLookingAt.getX(), blockLookingAt.getY(), blockLookingAt.getZ(), result);
                world.addFreshEntity(item);
            }
            else {
                world.destroyBlock(blockLookingAt, true);
            }
        }
        return InteractionResultHolder.success(stack);
    }
}
