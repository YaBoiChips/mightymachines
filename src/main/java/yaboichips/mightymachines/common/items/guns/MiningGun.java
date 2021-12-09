package yaboichips.mightymachines.common.items.guns;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class MiningGun extends Item {
    public MiningGun(Properties properties) {
        super(properties);
    }

    public BlockPos getBlockLookingAt(Player player) {
        HitResult block = player.pick(30.0D, 0.0F, false);

        if (block.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult) block).getBlockPos();
            if (player.level.getBlockState(pos) != Blocks.BEDROCK.defaultBlockState()) {
                return ((BlockHitResult) block).getBlockPos();
            }
        }
        return null;
    }
}
