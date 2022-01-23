package yaboichips.mightymachines.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class InventoryUtils {

    public static IItemHandler getInventory (Level world, BlockPos pos, Direction side) {

        final BlockEntity tileEntity = world.getBlockEntity(pos);

        if (tileEntity != null) {

            final LazyOptional<IItemHandler> inventoryCap = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            return inventoryCap.orElse(EmptyHandler.INSTANCE);
        }

        else {

            // Some blocks like composters are not tile entities so their inv can not be
            // accessed through the normal capability system.
            final BlockState state = world.getBlockState(pos);

            if (state.getBlock() instanceof WorldlyContainerHolder) {

                final WorldlyContainerHolder inventoryProvider = (WorldlyContainerHolder) state.getBlock();
                final WorldlyContainer inventory = inventoryProvider.getContainer(state, world, pos);

                if (inventory != null) {

                    return new SidedInvWrapper(inventory, side);
                }
            }
        }

        return EmptyHandler.INSTANCE;
    }
}
