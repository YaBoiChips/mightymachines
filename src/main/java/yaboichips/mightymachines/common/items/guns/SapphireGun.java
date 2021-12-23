package yaboichips.mightymachines.common.items.guns;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SapphireGun extends MiningGun {

    public SapphireGun(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.fail(stack);
        BlockState state = world.getBlockState(getBlockLookingAt(player));
        if (state.getBlock() instanceof OreBlock) {
            world.destroyBlock(getBlockLookingAt(player), false);
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel) world)).withRandom(world.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(getBlockLookingAt(player))).withParameter(LootContextParams.TOOL, stack);
            List<ItemStack> list = state.getDrops(lootcontext$builder);
            for (ItemStack stack2 : list) {
                spawnItemAtBlock(world, player, stack2);
                spawnItemAtBlock(world, player, stack2);
            }
        } else {
            world.destroyBlock(getBlockLookingAt(player), true);
        }
        return InteractionResultHolder.success(stack);
    }


    public void spawnItemAtBlock(Level world, Player player, ItemStack stack) {
        BlockPos blockLookingAt = getBlockLookingAt(player);
        if (blockLookingAt != null) {
            ItemEntity item = new ItemEntity(world, blockLookingAt.getX(), blockLookingAt.getY(), blockLookingAt.getZ(), stack);
            world.addFreshEntity(item);
        }
    }
}
