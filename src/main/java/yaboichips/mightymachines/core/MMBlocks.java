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

    //ores
    public static final Block RUBY_ORE = createOreBlock("ruby_ore");
    public static final Block AMETHYST_ORE = createOreBlock("amethyst_ore");
    public static final Block SAPPHIRE_ORE = createOreBlock("sapphire_ore");
    public static final Block TOPAZ_ORE = createOreBlock("topaz_ore");
    public static final Block OPAL_ORE = createOreBlock("opal_ore");
    public static final Block AMBER_ORE = createOreBlock("amber_ore");
    public static final Block ONYX_ORE = createOreBlock("onyx_ore");
    public static final Block PYRITE_ORE = createOreBlock("pyrite_ore");
    public static final Block THALLIUM_ORE = createOreBlock("thallium_ore");
    public static final Block BAUXITE_ORE = createOreBlock("bauxite_ore");
    public static final Block TIN_ORE = createOreBlock("tin_ore");


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
