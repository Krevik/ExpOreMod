package mod.krevik.expore;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.screen.BiomeGeneratorTypeScreens;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.data.BiomeProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.Dimension;
import net.minecraft.world.biome.*;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;


@Mod("exp_ore")
public class Main {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "exp_ore";
    public static final String NAME = "exp_ore";
    public static final String VERSION = "@VERSION@";

    public static final Block BLOCK_EXP_ORE = new BlockExpOre();
    public static int veins_Per_Chunk;
    public static int minimum_Vein_Size;
    public static int maximum_Vein_Size;
    public static int minimum_Exp_From_Ore;
    public static int maximum_Exp_From_Ore;
    public static int maximum_Ore_Height;
    public static boolean should_Ore_Generate;
    public static boolean should_Add_Recipe;

    public Main() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.CONFIG_SPEC);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        forgeBus.addListener(EventPriority.HIGH, this::bindFeatureToBiomes);

    }

    @SubscribeEvent
    public void bindFeatureToBiomes(final BiomeLoadingEvent event){
        ConfiguredFeature<?, ?> ORE_EXP =  Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Main.BLOCK_EXP_ORE.getDefaultState(), MathHelper.nextInt(new Random(),minimum_Vein_Size,maximum_Vein_Size)))
                .withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(maximum_Ore_Height/2, maximum_Ore_Height/2))
                        .func_242730_a(FeatureSpread.func_242252_a(1))
                        .square());
        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_EXP);
    }


    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }


    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event){
            final IForgeRegistry<Block> registry = event.getRegistry();
            registry.register(BLOCK_EXP_ORE);
        }

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event){
            final IForgeRegistry<Item> registry = event.getRegistry();
            registry.register(new BlockItem(BLOCK_EXP_ORE,new Item.Properties().group(ItemGroup.COMBAT)).setRegistryName(Main.MODID,"block_exp_ore"));
        }

    }

}
