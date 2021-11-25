package yaboichips.mightymachines.core;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import yaboichips.mightymachines.MightyMachines;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MMBlocks {
    public static List<Block> blocks = new ArrayList<>();



    static @Nonnull Block createOreBlock(String id) {
        Block stone = new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE));
        return registerBlock(id, stone);
    }

    static @Nonnull
    <T extends Block> T registerBlock(String id, @Nonnull T block) {
        block.setRegistryName(MightyMachines.createResource(id));

        blocks.add(block);

        return block;
    }

    public static void init() {
    }
}
