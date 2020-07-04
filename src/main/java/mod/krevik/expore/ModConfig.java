package mod.krevik.expore;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Main.MODID)
@Config.LangKey("exp_ore_block.config.title")
public class ModConfig {
    @Config.Comment("Veins per chunk")
    public static int veins_Per_Chunk=6;
    @Config.Comment("Minimum vein size")
    public static int minimum_Vein_Size=2;
    @Config.Comment("Maximum vein size")
    public static int maximum_Vein_Size=8;
    @Config.Comment("Minimum exp that can drop from the ore")
    public static int minimum_Exp_From_Ore=500;
    @Config.Comment("Maximum exp that can drop from the ore")
    public static int maximum_Exp_From_Ore=1500;
    @Config.Comment("Maximum Height of ore generation")
    public static int maximum_Ore_Height=80;
    @Config.Comment("Should ore event generate")
    public static boolean should_Ore_Generate = true;
    @Config.Comment("Should add recipe for the exp ore to the game")
    public static boolean should_add_recipe = true;
    @Mod.EventBusSubscriber(modid = Main.MODID)
    private static class EventHandler {

        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Main.MODID)) {
                ConfigManager.sync(Main.MODID, Config.Type.INSTANCE);
            }
        }
    }
}