package mod.krevik.expore;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {
    @SubscribeEvent(priority=EventPriority.HIGH)
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        if(ConfigHandler.should_generate_ore.get()){
            event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> OreGeneration.EXPORE_SMALL_PLACED);
            event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES).add(() -> OreGeneration.EXPORE_SMALL_PLACED_FRACTION);
        }
    }
}