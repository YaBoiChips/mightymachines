package yaboichips.mightymachines.common.world;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.core.MMFeatures;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MightyMachines.MOD_ID)
public class OreGenerator {

    private static final ArrayList<PlacedFeature> OVERWORLD_ORES = new ArrayList<>();

    public static final PlacedFeature BAUXITE_ORE = PlacementUtils.register("bauxite_ore", MMFeatures.BAUXITE_ORE.placed(commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)))));
    public static final PlacedFeature TIN_ORE = PlacementUtils.register("tin_ore", MMFeatures.TIN_ORE.placed(commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)))));
    public static final PlacedFeature THALLIUM_ORE = PlacementUtils.register("thallium_ore", MMFeatures.THALLIUM_ORE.placed(commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56)))));
    public static final PlacedFeature RUBY_ORE = PlacementUtils.register("ruby_ore", MMFeatures.RUBY_ORE.placed(commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
    public static final PlacedFeature ONYX_ORE = PlacementUtils.register("onyx_ore", MMFeatures.ONYX_ORE.placed(commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
    public static final PlacedFeature OPAL_ORE = PlacementUtils.register("opal_ore", MMFeatures.OPAL_ORE.placed(commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
    public static final PlacedFeature TOPAZ_ORE = PlacementUtils.register("topaz_ore", MMFeatures.TOPAZ_ORE.placed(commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
    public static final PlacedFeature SAPPHIRE_ORE = PlacementUtils.register("sapphire_ore", MMFeatures.SAPPHIRE_ORE.placed(commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));
    public static final PlacedFeature PYRITE_ORE = PlacementUtils.register("pyrite_ore", MMFeatures.PYRITE_ORE.placed(commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static void registerOre(){
        OVERWORLD_ORES.add(BAUXITE_ORE);
        OVERWORLD_ORES.add(TIN_ORE);
        OVERWORLD_ORES.add(THALLIUM_ORE);
        OVERWORLD_ORES.add(RUBY_ORE);
        OVERWORLD_ORES.add(ONYX_ORE);
        OVERWORLD_ORES.add(OPAL_ORE);
        OVERWORLD_ORES.add(TOPAZ_ORE);
        OVERWORLD_ORES.add(SAPPHIRE_ORE);
        OVERWORLD_ORES.add(PYRITE_ORE);
    }


    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gen(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();
        for (PlacedFeature ore : OVERWORLD_ORES) {
            if (ore != null) generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ore);
        }
    }

}
