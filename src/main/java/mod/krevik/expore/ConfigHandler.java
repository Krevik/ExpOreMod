package mod.krevik.expore;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {
    public static ForgeConfigSpec.BooleanValue should_generate_ore;
    public static ForgeConfigSpec.IntValue ore_size;
    public static ForgeConfigSpec.IntValue veins_per_chunk;
    public static ForgeConfigSpec.IntValue min_ore_exp;
    public static ForgeConfigSpec.IntValue max_ore_exp;
    public static ForgeConfigSpec.IntValue max_ore_height;

    public static void init(ForgeConfigSpec.Builder builder) {
        builder.comment("Note that almost all config changes require a server restart and/or a full minecraft restart.");
        builder.push("exp_ore");

        should_generate_ore = builder
                .comment("Spawn exp ore in overworld? Default = true")
                .define("should_generate_ore", true);
        ore_size = builder
                .comment("Max vein size. Default = 5")
                .defineInRange("ore_size", 5, 1, 100);
        veins_per_chunk = builder
                .comment("How many vein clusters should be generated per chunk. Default = 10")
                .defineInRange("veins_per_chunk", 10, 1, 100);
        min_ore_exp = builder
                .comment("How much exp should give 1 ore block (minimum). Default = 40")
                .defineInRange("min_ore_exp", 40, 0, Integer.MAX_VALUE);
        max_ore_exp = builder
                .comment("How much exp should give 1 ore block (maximum). Default = 245")
                .defineInRange("max_ore_exp", 245, 1, Integer.MAX_VALUE);
        max_ore_height = builder
                .comment("Maximum height at which ore can be found. Default = 80")
                .defineInRange("max_ore_height", 80, -50, 255);
        builder.pop();
    }
}
