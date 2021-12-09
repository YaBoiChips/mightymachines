package yaboichips.mightymachines.common.items.guns;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PyriteGun extends MiningGun {
    public PyriteGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.fail(stack);
        if (this.getBlockLookingAt(player) != null) {
            for (int x1 = this.getBlockLookingAt(player).getX() - 1; x1 <= this.getBlockLookingAt(player).getX() + 1; ++x1) {
                for (int y1 = this.getBlockLookingAt(player).getY() - 1; y1 <= this.getBlockLookingAt(player).getY() + 1; ++y1) {
                    for (int z1 = this.getBlockLookingAt(player).getZ() - 1; z1 <= this.getBlockLookingAt(player).getZ() + 1; ++z1) {
                        world.destroyBlock(new BlockPos(x1, y1, z1), true);
                    }
                }
            }
        }
        return InteractionResultHolder.success(stack);
    }
}
