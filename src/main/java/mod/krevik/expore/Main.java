package mod.krevik.expore;


import com.google.common.collect.ImmutableList;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
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
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.core.Registry;

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
    public static int veins_Per_Chunk = ConfigHandler.VEINS_PER_CHUNK.get();
    public static int Vein_Size = ConfigHandler.ORE_SIZE.get();
    public static int minimum_Exp_From_Ore = ConfigHandler.MIN_ORE_EXP.get();
    public static int maximum_Exp_From_Ore = ConfigHandler.MAX_ORE_EXP.get();
    public static int maximum_Ore_Height = ConfigHandler.MAX_ORE_HEIGHT.get();
    public static boolean should_Ore_Generate = ConfigHandler.SHOULD_GENERATE_ORE.get();
    public static final ImmutableList<OreConfiguration.TargetBlockState> EXP_ORE_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, BLOCK_EXP_ORE.defaultBlockState()), OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES, BLOCK_EXP_ORE.defaultBlockState()));
    public static final ConfiguredFeature<?, ?> EXP_ORE_FEATURE = Feature.ORE.configured(new OreConfiguration(EXP_ORE_TARGET_LIST, Vein_Size)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.belowTop(maximum_Ore_Height)).squared().count(veins_Per_Chunk);

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new ResourceLocation(Main.MODID, "exp_ore"), EXP_ORE_FEATURE);
    }

    public Main() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.SPEC);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        forgeBus.addListener(EventPriority.HIGH, this::bindFeatureToBiomes);
    }

    public void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            Main.registerConfiguredFeatures();
        });
    }

    @SubscribeEvent
    public void bindFeatureToBiomes(final BiomeLoadingEvent event){
        if(Main.should_Ore_Generate) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, EXP_ORE_FEATURE);
        }
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
            registry.register(new BlockItem(BLOCK_EXP_ORE,new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)).setRegistryName(Main.MODID,"block_exp_ore"));
        }

    }

}
