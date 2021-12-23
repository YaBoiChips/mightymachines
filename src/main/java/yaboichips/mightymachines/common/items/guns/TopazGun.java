package yaboichips.mightymachines.common.items.guns;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class TopazGun extends MiningGun{
    public TopazGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.fail(stack);
        if (this.getBlockLookingAt(player) !=null) {
            BlockPos block = getBlockLookingAt(player);
            world.explode(null, block.getX(), block.getY(), block.getZ(), 5.5F, Explosion.BlockInteraction.BREAK);
        }
        return InteractionResultHolder.success(stack);
    }
}