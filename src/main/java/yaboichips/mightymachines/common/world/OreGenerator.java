package yaboichips.mightymachines.common.world;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.core.MMBlocks;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MightyMachines.MOD_ID)
public class OreGenerator {

    private static final ArrayList<ConfiguredFeature<?, ?>> OVERWORLD_ORES = new ArrayList<ConfiguredFeature<?, ?>>();

    public static void registerOre() {
        OVERWORLD_ORES.add(register("bauxite_ore", Feature.ORE.configured(new OreConfiguration(
                        OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.BAUXITE_ORE.defaultBlockState(), 4))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20)));
        OVERWORLD_ORES.add(register("thallium_ore", Feature.ORE.configured(new OreConfiguration
                        (OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.THALLIUM_ORE.defaultBlockState(), 4))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20)));
        OVERWORLD_ORES.add(register("tin_ore", Feature.ORE.configured(new OreConfiguration
                        (OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.TIN_ORE.defaultBlockState(), 4))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(20)));
        OVERWORLD_ORES.add(register("onyx_ore", Feature.ORE.configured(new OreConfiguration
                        (OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.ONYX_ORE.defaultBlockState(), 1))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(10)));
        OVERWORLD_ORES.add(register("opal_ore", Feature.ORE.configured(new OreConfiguration
                        (OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.OPAL_ORE.defaultBlockState(), 1))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(10)));
        OVERWORLD_ORES.add(register("ruby_ore", Feature.ORE.configured(new OreConfiguration
                        (OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.RUBY_ORE.defaultBlockState(), 1))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(10)));
        OVERWORLD_ORES.add(register("sapphire_ore", Feature.ORE.configured(new OreConfiguration
                        (OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.SAPPHIRE_ORE.defaultBlockState(), 1))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(10)));
        OVERWORLD_ORES.add(register("topaz_ore", Feature.ORE.configured(new OreConfiguration
                        (OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.TOPAZ_ORE.defaultBlockState(), 1))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(10)));
        OVERWORLD_ORES.add(register("pyrite_ore", Feature.ORE.configured(new OreConfiguration
                        (OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.PYRITE_ORE.defaultBlockState(), 1))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(10)));
        OVERWORLD_ORES.add(register("onyx_ore", Feature.ORE.configured(new OreConfiguration
                        (OreConfiguration.Predicates.NATURAL_STONE, MMBlocks.ONYX_ORE.defaultBlockState(), 1))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(63)).squared().count(10)));
    }

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, MightyMachines.MOD_ID + ":" + key, configuredFeature);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gen(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        for (ConfiguredFeature<?, ?> ore : OVERWORLD_ORES) {
            if (ore != null) generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ore);
        }
    }
}
