package yaboichips.mightymachines.common.items.guns;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Random;

public class OpalGun extends MiningGun {
    public OpalGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.fail(stack);
        BlockPos blockLookingAt = this.getBlockLookingAt(player);
        if (blockLookingAt != null) {
            world.destroyBlock(blockLookingAt, true);
            ItemEntity item = new ItemEntity(world, blockLookingAt.getX(), blockLookingAt.getY(), blockLookingAt.getZ(), getRandomOre(world.random));
            world.addFreshEntity(item);
        }
        return InteractionResultHolder.success(stack);
    }

    public ItemStack getRandomOre(Random rand) {
        int i = rand.nextInt(20);
        if (i <= 2) {
            return Items.IRON_INGOT.getDefaultInstance();
        }
        if (i == 3) {
            return Items.EMERALD.getDefaultInstance();
        }
        if (i == 4) {
            return Items.DIAMOND.getDefaultInstance();
        }
        if (i == 5) {
            return Items.GOLD_INGOT.getDefaultInstance();
        }
        if (i >= 7 && i <=9) {
            return Items.COAL.getDefaultInstance();
        }
        return ItemStack.EMPTY;
    }
}
