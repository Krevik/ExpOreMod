package mod.krevik.expore;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SPEC = null;
    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOULD_GENERATE_ORE;
    public static final ForgeConfigSpec.ConfigValue<Integer> ORE_SIZE;
    public static final ForgeConfigSpec.ConfigValue<Integer> VEINS_PER_CHUNK;
    public static final ForgeConfigSpec.ConfigValue<Integer> MIN_ORE_EXP;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ORE_EXP;
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ORE_HEIGHT;

    public ConfigHandler() {
    }

    static {
        BUILDER.push("Exp Ore Block Generation Configs");
        SHOULD_GENERATE_ORE = BUILDER.define("Should generate ore at all", true);
        ORE_SIZE = BUILDER.define("Vein size", 8);
        MAX_ORE_HEIGHT = BUILDER.define("Maximum height at which the ore can generate", 128);
        VEINS_PER_CHUNK = BUILDER.define("Maximal amount of veins per chunk", 32);
        MIN_ORE_EXP = BUILDER.define("Minimum exp from ore", 40);
        MAX_ORE_EXP = BUILDER.define("Maximum exp from ore", 225);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
