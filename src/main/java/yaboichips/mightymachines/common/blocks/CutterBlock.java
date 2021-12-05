package yaboichips.mightymachines.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import yaboichips.mightymachines.common.tile.CutterTE;
import yaboichips.mightymachines.core.MMBlockEntities;
import yaboichips.mightymachines.core.MMItems;

import javax.annotation.Nullable;

public class CutterBlock extends BaseEntityBlock {
    public CutterBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return MMBlockEntities.CUTTER.create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (player.getItemInHand(hand).getItem() == MMItems.CRANK) {
            CutterTE tile = (CutterTE) world.getBlockEntity(pos);
            tile.setWork(tile.getWork() + 1);
            return InteractionResult.SUCCESS;
        } else {
            if (!world.isClientSide) {
                BlockEntity tile = world.getBlockEntity(pos);
                if (tile instanceof CutterTE) {
                    player.openMenu((CutterTE) tile);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity te = worldIn.getBlockEntity(pos);
            if (te instanceof CutterTE) {
                Containers.dropContents(worldIn, pos, ((CutterTE) te).getItems());
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> type) {
        return createTickerHelper(type, MMBlockEntities.CUTTER, CutterTE::tick);
    }
}