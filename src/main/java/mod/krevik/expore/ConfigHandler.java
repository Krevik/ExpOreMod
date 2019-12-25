package mod.krevik.expore;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
public class ConfigHandler {

    public static final Config CONFIG;
    public static final ForgeConfigSpec CONFIG_SPEC;

    static
    {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }
    public static class Config
    {
        public ForgeConfigSpec.IntValue veins_Per_Chunk;
        public ForgeConfigSpec.IntValue minimum_Vein_Size;
        public ForgeConfigSpec.IntValue maximum_Vein_Size;
        public ForgeConfigSpec.IntValue minimum_Exp_From_Ore;
        public ForgeConfigSpec.IntValue maximum_Exp_From_Ore;
        public ForgeConfigSpec.IntValue maximum_Ore_Height;

        public ForgeConfigSpec.BooleanValue should_Ore_Generate;
        public ForgeConfigSpec.BooleanValue should_Add_Recipe;

        Config(ForgeConfigSpec.Builder builder)
        {

            builder.push("general");
            should_Ore_Generate = builder.comment("Defines if the ore should even generate (cause it is craftable)")
                    .translation("config.expore.should_ore_generate")
                    .define("should_ore_generate", true);
            builder.pop();

            builder.push("general");
            veins_Per_Chunk = builder.comment("Defines how much veins of the exp ore should be generated per one chunk")
                    .translation("config.expore.veins_per_chunk")
                    .defineInRange("veins_per_chunk", 3, 0, 100);
            builder.pop();

            builder.push("general");
            minimum_Vein_Size = builder.comment("Defines how small (minimally) can be one vein")
                    .translation("config.expore.minimum_vein_size")
                    .defineInRange("minimum_vein_size", 8, 1, 10);
            builder.pop();

            builder.push("general");
            maximum_Vein_Size = builder.comment("Defines how big (maximally) can be one vein")
                    .translation("config.expore.maximum_vein_size")
                    .defineInRange("maximum_vein_size", 8, 1, 64);
            builder.pop();

            builder.push("general");
            minimum_Exp_From_Ore = builder.comment("Defines minimum exp that the ore can drop")
                    .translation("config.expore.minimum_exp_from_ore")
                    .defineInRange("minimum_exp_from_ore", 500, 1, 100000);
            builder.pop();

            builder.push("general");
            maximum_Exp_From_Ore = builder.comment("Defines maximum exp that the ore can drop")
                    .translation("config.expore.maximum_exp_from_ore")
                    .defineInRange("maximum_exp_from_ore", 1500, 1, 300000);
            builder.pop();

            builder.push("general");
            maximum_Ore_Height = builder.comment("Defines max height that the ore can generate at")
                    .translation("config.expore.maximum_ore_height")
                    .defineInRange("maximum_ore_height", 80, 8, 255);
            builder.pop();

        }
    }

    @SubscribeEvent
    public static void onConfigEvent(ModConfig.ModConfigEvent ev)
    {
        if(ev.getConfig().getSpec() == CONFIG_SPEC)
        {
            Main.maximum_Exp_From_Ore = ev.getConfig().getConfigData().get("general.maximum_exp_from_ore");
            Main.minimum_Exp_From_Ore = ev.getConfig().getConfigData().get("general.minimum_exp_from_ore");
            Main.should_Ore_Generate = ev.getConfig().getConfigData().get("general.should_ore_generate");
            Main.veins_Per_Chunk = ev.getConfig().getConfigData().get("general.veins_per_chunk");
            Main.minimum_Vein_Size = ev.getConfig().getConfigData().get("general.minimum_vein_size");
            Main.maximum_Vein_Size = ev.getConfig().getConfigData().get("general.maximum_vein_size");
            Main.maximum_Ore_Height = ev.getConfig().getConfigData().get("general.maximum_ore_height");
        }
    }
}
