package yaboichips.mightymachines.core;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.blocks.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MMBlocks {
    public static List<Block> blocks = new ArrayList<>();

    //ores
    public static final Block RUBY_ORE = createOreBlock("ruby_ore"); //auto smelt FIXME
    public static final Block SAPPHIRE_ORE = createOreBlock("sapphire_ore"); //looting
    public static final Block TOPAZ_ORE = createOreBlock("topaz_ore");
    public static final Block OPAL_ORE = createOreBlock("opal_ore");
    public static final Block AMBER_ORE = createOreBlock("amber_ore");
    public static final Block ONYX_ORE = createOreBlock("onyx_ore"); //feeds you
    public static final Block PYRITE_ORE = createOreBlock("pyrite_ore");
    public static final Block THALLIUM_ORE = createOreBlock("thallium_ore");
    public static final Block BAUXITE_ORE = createOreBlock("bauxite_ore");
    public static final Block TIN_ORE = createOreBlock("tin_ore");

    public static final Block THALLIUM_BLOCK = createMetalBlock("thallium_block");
    public static final Block ALUMINIUM_BLOCK = createMetalBlock("aluminium_block");
    public static final Block TIN_BLOCK = createMetalBlock("tin_block");


    public static final Block DEEPSLATE_RUBY_ORE = createDeepslateOreBlock("deepslate_ruby_ore");
    public static final Block DEEPSLATE_SAPPHIRE_ORE = createDeepslateOreBlock("deepslate_sapphire_ore");
    public static final Block DEEPSLATE_TOPAZ_ORE = createDeepslateOreBlock("deepslate_topaz_ore");
    public static final Block DEEPSLATE_OPAL_ORE = createDeepslateOreBlock("deepslate_opal_ore");
    public static final Block DEEPSLATE_AMBER_ORE = createDeepslateOreBlock("deepslate_amber_ore");
    public static final Block DEEPSLATE_ONYX_ORE = createDeepslateOreBlock("deepslate_onyx_ore");
    public static final Block DEEPSLATE_PYRITE_ORE = createDeepslateOreBlock("deepslate_pyrite_ore");
    public static final Block DEEPSLATE_THALLIUM_ORE = createDeepslateOreBlock("deepslate_thallium_ore");
    public static final Block DEEPSLATE_BAUXITE_ORE = createDeepslateOreBlock("deepslate_bauxite_ore");
    public static final Block DEEPSLATE_TIN_ORE = createDeepslateOreBlock("deepslate_tin_ore");

    public static final Block GENERATOR = registerBlock("generator", new GeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final Block FARMER = registerBlock("farmer", new FarmerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final Block MACHINE_BUILDER = registerBlock("machine_builder", new MachineBuilderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));



    static @Nonnull
    Block createOreBlock(String id) {
        Block stone = new OreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE));
        return registerBlock(id, stone);
    }

    static @Nonnull
    Block createDeepslateOreBlock(String id) {
        Block stone = new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE));
        return registerBlock(id, stone);
    }
    static @Nonnull
    Block createMetalBlock(String id) {
        Block stone = new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
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