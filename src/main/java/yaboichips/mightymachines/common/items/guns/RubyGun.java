package yaboichips.mightymachines.common.items.guns;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RubyGun extends MiningGun{
    public RubyGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.fail(stack);
        if (this.getBlockLookingAt(player) !=null) {
            world.destroyBlock(this.getBlockLookingAt(player), true);
        }
        return InteractionResultHolder.success(stack);
    }
}
