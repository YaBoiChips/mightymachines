package yaboichips.mightymachines.common.capabilities;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

public class PackerCap implements IPackerCapability {

    public Player player;

    public PackerCap(Player player) {
        this.player = player;
        PACKING_BLOCKS.put(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.DIRT_PATH.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.BLACK_CONCRETE_POWDER.defaultBlockState(), Blocks.BLACK_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.RED_CONCRETE_POWDER.defaultBlockState(), Blocks.RED_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.WHITE_CONCRETE_POWDER.defaultBlockState(), Blocks.WHITE_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.BLUE_CONCRETE_POWDER.defaultBlockState(), Blocks.BLUE_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.GREEN_CONCRETE_POWDER.defaultBlockState(), Blocks.GREEN_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.LIME_CONCRETE_POWDER.defaultBlockState(), Blocks.LIME_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.PINK_CONCRETE_POWDER.defaultBlockState(), Blocks.PINK_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.LIGHT_BLUE_CONCRETE_POWDER.defaultBlockState(), Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.ORANGE_CONCRETE_POWDER.defaultBlockState(), Blocks.ORANGE_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.BROWN_CONCRETE_POWDER.defaultBlockState(), Blocks.BROWN_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.GRAY_CONCRETE_POWDER.defaultBlockState(), Blocks.GRAY_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.LIGHT_GRAY_CONCRETE_POWDER.defaultBlockState(), Blocks.LIGHT_GRAY_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.YELLOW_CONCRETE_POWDER.defaultBlockState(), Blocks.YELLOW_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.CYAN_CONCRETE_POWDER.defaultBlockState(), Blocks.CYAN_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.PURPLE_CONCRETE_POWDER.defaultBlockState(), Blocks.PURPLE_CONCRETE.defaultBlockState());
        PACKING_BLOCKS.put(Blocks.MAGENTA_CONCRETE_POWDER.defaultBlockState(), Blocks.MAGENTA_CONCRETE.defaultBlockState());
    }

    @Override
    public Player getPlayer() {
        return player;
    }
}
