package yaboichips.mightymachines.core;

import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import static net.minecraft.data.worldgen.features.FeatureUtils.register;

public class MMFeatures {

    public static ConfiguredFeature<?, ?> BAUXITE_ORE = register("bauxite_ore", Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, MMBlocks.BAUXITE_ORE.defaultBlockState(), 4)));
    public static ConfiguredFeature<?, ?> THALLIUM_ORE = register("thallium_ore", Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, MMBlocks.THALLIUM_ORE.defaultBlockState(), 4)));
    public static ConfiguredFeature<?, ?> TIN_ORE = register("tin_ore", Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, MMBlocks.TIN_ORE.defaultBlockState(), 4)));
    public static ConfiguredFeature<?, ?> ONYX_ORE = register("onyx_ore", Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, MMBlocks.ONYX_ORE.defaultBlockState(), 1)));
    public static ConfiguredFeature<?, ?> OPAL_ORE = register("opal_ore", Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, MMBlocks.OPAL_ORE.defaultBlockState(), 1)));
    public static ConfiguredFeature<?, ?> RUBY_ORE = register("ruby_ore", Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, MMBlocks.RUBY_ORE.defaultBlockState(), 1)));
    public static ConfiguredFeature<?, ?> SAPPHIRE_ORE = register("sapphire_ore", Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, MMBlocks.SAPPHIRE_ORE.defaultBlockState(), 1)));
    public static ConfiguredFeature<?, ?> TOPAZ_ORE = register("topaz_ore", Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, MMBlocks.TOPAZ_ORE.defaultBlockState(), 1)));
    public static ConfiguredFeature<?, ?> PYRITE_ORE = register("pyrite_ore", Feature.ORE.configured(new OreConfiguration(OreFeatures.NATURAL_STONE, MMBlocks.PYRITE_ORE.defaultBlockState(), 1)));
}
