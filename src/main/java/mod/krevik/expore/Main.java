package mod.krevik.expore;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
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
import net.minecraftforge.fml.loading.FMLPaths;
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


    public Main() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigLoader.SERVER_CONFIG);
        ConfigLoader.loadConfig(ConfigLoader.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("exp_ore-common.toml"));

    }

    public void setup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            OreGeneration.registerFeatures();
        });
    }


    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }


    private void doClientStuff(final FMLClientSetupEvent event) {
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
