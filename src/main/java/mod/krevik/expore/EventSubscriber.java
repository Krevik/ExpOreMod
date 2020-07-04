package mod.krevik.expore;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class EventSubscriber {

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = event.getRegistry();

        final Block[] blocks = {
                Main.BLOCK_EXP_ORE

        };

        registry.registerAll(blocks);
    }

    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
        final ItemBlock[] items = {
                new ItemBlock(Main.BLOCK_EXP_ORE)
        };

        final IForgeRegistry<Item> registry = event.getRegistry();

        for (final ItemBlock item : items) {
            final Block block = item.getBlock();
            final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
            registry.register(item.setRegistryName(registryName));
        }
    }

    @SubscribeEvent
    public static void harvestDrops(final BlockEvent.BreakEvent event){

    }

}