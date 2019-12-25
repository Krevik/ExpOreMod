package mod.krevik.expore;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.PlainsBiome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
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

import java.util.Random;


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

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
    }

    private void processIMC(final InterModProcessEvent event)
    {
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        if(should_Ore_Generate) {
            for (BiomeManager.BiomeType biomeType : BiomeManager.BiomeType.values()) {
                for (BiomeManager.BiomeEntry biomeEntry : BiomeManager.getBiomes(biomeType)) {
                    biomeEntry.biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Main.BLOCK_EXP_ORE.getDefaultState(), 17)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(veins_Per_Chunk, 0, 0, maximum_Ore_Height))));
                }
            }
        }
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
