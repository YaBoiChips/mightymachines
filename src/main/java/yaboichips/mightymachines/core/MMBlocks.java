package yaboichips.mightymachines.core;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.blocks.CutterBlock;
import yaboichips.mightymachines.common.blocks.GeneratorBlock;
import yaboichips.mightymachines.common.blocks.SmasherBlock;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MMBlocks {
    public static List<Block> blocks = new ArrayList<>();

    //ores
    public static final Block RUBY_ORE = createOreBlock("ruby_ore"); //auto smelt
    public static final Block SAPPHIRE_ORE = createOreBlock("sapphire_ore"); //looting
    public static final Block TOPAZ_ORE = createOreBlock("topaz_ore"); //
    public static final Block OPAL_ORE = createOreBlock("opal_ore"); //
    public static final Block AMBER_ORE = createOreBlock("amber_ore"); //silk touch
    public static final Block ONYX_ORE = createOreBlock("onyx_ore"); //
    public static final Block PYRITE_ORE = createOreBlock("pyrite_ore"); // 3x3
    public static final Block THALLIUM_ORE = createOreBlock("thallium_ore");
    public static final Block BAUXITE_ORE = createOreBlock("bauxite_ore");
    public static final Block TIN_ORE = createOreBlock("tin_ore");

    public static final Block GENERATOR = registerBlock("generator", new GeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final Block MANUAL_SMASHER = registerBlock("manual_smasher", new SmasherBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final Block CUTTER = registerBlock("cutter", new CutterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));


    static @Nonnull
    Block createOreBlock(String id) {
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
