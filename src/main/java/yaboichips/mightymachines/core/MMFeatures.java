package yaboichips.mightymachines.core;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

import static net.minecraft.data.worldgen.features.FeatureUtils.register;
import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;

public class MMFeatures {

    public static final List<OreConfiguration.TargetBlockState> ORE_RUBY_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MMBlocks.RUBY_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DEEPSLATE_RUBY_ORE.defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> ORE_BAUXITE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MMBlocks.BAUXITE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DEEPSLATE_BAUXITE_ORE.defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> ORE_THALLIUM_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MMBlocks.THALLIUM_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DEEPSLATE_THALLIUM_ORE.defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> ORE_TIN_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MMBlocks.TIN_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DEEPSLATE_TIN_ORE.defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> ORE_ONYX_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MMBlocks.ONYX_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DEEPSLATE_ONYX_ORE.defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> ORE_OPAL_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MMBlocks.OPAL_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DEEPSLATE_OPAL_ORE.defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> ORE_SAPPHIRE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MMBlocks.SAPPHIRE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DEEPSLATE_SAPPHIRE_ORE.defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> ORE_TOPAZ_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MMBlocks.TOPAZ_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DEEPSLATE_TOPAZ_ORE.defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> ORE_PYRITE_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MMBlocks.PYRITE_ORE.defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MMBlocks.DEEPSLATE_PYRITE_ORE.defaultBlockState()));

    public static ConfiguredFeature<?, ?> BAUXITE_ORE = register("bauxite_ore", Feature.ORE.configured(new OreConfiguration(ORE_BAUXITE_TARGET_LIST, 4)));
    public static ConfiguredFeature<?, ?> THALLIUM_ORE = register("thallium_ore", Feature.ORE.configured(new OreConfiguration(ORE_THALLIUM_TARGET_LIST, 4)));
    public static ConfiguredFeature<?, ?> TIN_ORE = register("tin_ore", Feature.ORE.configured(new OreConfiguration(ORE_TIN_TARGET_LIST, 4)));
    public static ConfiguredFeature<?, ?> ONYX_ORE = register("onyx_ore", Feature.ORE.configured(new OreConfiguration(ORE_ONYX_TARGET_LIST, 1)));
    public static ConfiguredFeature<?, ?> OPAL_ORE = register("opal_ore", Feature.ORE.configured(new OreConfiguration(ORE_OPAL_TARGET_LIST, 1)));
    public static ConfiguredFeature<?, ?> RUBY_ORE = register("ruby_ore", Feature.ORE.configured(new OreConfiguration(ORE_RUBY_TARGET_LIST, 1)));
    public static ConfiguredFeature<?, ?> SAPPHIRE_ORE = register("sapphire_ore", Feature.ORE.configured(new OreConfiguration(ORE_SAPPHIRE_TARGET_LIST, 1)));
    public static ConfiguredFeature<?, ?> TOPAZ_ORE = register("topaz_ore", Feature.ORE.configured(new OreConfiguration(ORE_TOPAZ_TARGET_LIST, 1)));
    public static ConfiguredFeature<?, ?> PYRITE_ORE = register("pyrite_ore", Feature.ORE.configured(new OreConfiguration(ORE_PYRITE_TARGET_LIST, 1)));
}