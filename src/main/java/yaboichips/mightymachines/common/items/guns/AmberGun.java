package yaboichips.mightymachines.common.items.guns;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AmberGun extends MiningGun{
    public AmberGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.fail(stack);
        BlockPos blockLookingAt = this.getBlockLookingAt(player);
        if (blockLookingAt !=null) {
            ItemStack stack2 = world.getBlockState(blockLookingAt).getBlock().asItem().getDefaultInstance();
            world.destroyBlock(blockLookingAt, false);
            ItemEntity item = new ItemEntity(world, blockLookingAt.getX(), blockLookingAt.getY(), blockLookingAt.getZ(), stack2);
            world.addFreshEntity(item);
        }
        return InteractionResultHolder.success(stack);
    }
}
