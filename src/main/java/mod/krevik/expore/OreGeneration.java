package mod.krevik.expore;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class OreGeneration {

    public static ConfiguredFeature<?, ?> EXPORE_SMALL;

    public static PlacedFeature EXPORE_SMALL_PLACED;

    public static PlacedFeature EXPORE_SMALL_PLACED_FRACTION;

    public static ImmutableList<OreConfiguration.TargetBlockState> EXPORE_TARGET_BLOCKS;

    public static ConfiguredFeature<?, ?> buildConfigured(ImmutableList<OreConfiguration.TargetBlockState> targets, double veinSize, float discardChance) {
        return Feature.ORE.configured(new OreConfiguration(targets, (int) Math.round(veinSize), discardChance));
    }

    // Only used for non-default config options
    public static PlacedFeature buildFeature(ConfiguredFeature<?, ?> configured, int veinCount, double timesRarer, HeightRangePlacement placement) {
        if (veinCount < 1 || (int) Math.round(veinCount / timesRarer) <= 0) {
            int chunksPerOre = veinCount < 1 ? (int) timesRarer : (int) Math.max(1, Math.round(timesRarer / veinCount));
            return configured.placed(rareOrePlacement(chunksPerOre, placement));
        } else {
            return configured.placed(commonOrePlacement((int) Math.round(veinCount / timesRarer), placement));
        }
    }

    public static HeightRangePlacement buildPlacement(int maxHeight) {
        return HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64 - maxHeight), VerticalAnchor.aboveBottom(64 + maxHeight));
    }
    public static HeightRangePlacement buildPlacement(int minHeight, int maxHeight) {
        return HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight));
    }

    public static void registerFeatures() {

        EXPORE_TARGET_BLOCKS = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, Main.BLOCK_EXP_ORE.defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Main.BLOCK_EXP_ORE.defaultBlockState()));

        EXPORE_SMALL = buildConfigured(EXPORE_TARGET_BLOCKS, Main.Vein_Size,  0.5F);

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Main.MODID, "exp_ore"), EXPORE_SMALL);

        // Uses fractions to try and better imitate vanilla's ore spawning
        HeightRangePlacement aPlacement = buildPlacement(Main.maximum_Ore_Height);
        EXPORE_SMALL_PLACED = buildFeature(EXPORE_SMALL, Main.veins_Per_Chunk, 1, aPlacement);
        EXPORE_SMALL_PLACED_FRACTION = EXPORE_SMALL.placed(commonOrePlacement(0, aPlacement));

        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Main.MODID, "exp_ore"), EXPORE_SMALL_PLACED);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Main.MODID, "exp_ore_fraction"), EXPORE_SMALL_PLACED_FRACTION);
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
}