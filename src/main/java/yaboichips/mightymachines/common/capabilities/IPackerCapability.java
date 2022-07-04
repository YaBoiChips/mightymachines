package yaboichips.mightymachines.common.capabilities;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;

public interface IPackerCapability{

    HashMap<BlockState, BlockState> PACKING_BLOCKS = new HashMap<>();

    public Player getPlayer();
}
