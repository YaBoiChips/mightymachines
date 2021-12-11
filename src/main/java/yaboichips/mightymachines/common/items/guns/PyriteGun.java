package yaboichips.mightymachines.common.items.guns;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
        BlockPos pos = this.getBlockLookingAt(player);
        if (pos != null) {
            for (Direction facing : Direction.values()) {
                BlockPos relative = pos.relative(facing);
                world.destroyBlock(relative, true);
                world.destroyBlock(pos, true);
            }
        }
        return InteractionResultHolder.success(stack);
    }
}
