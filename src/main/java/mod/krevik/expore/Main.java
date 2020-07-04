package mod.krevik.expore;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {

    private static Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "exp_ore";
    public static final String NAME = "exp_ore";
    public static final String VERSION = "1.12.2-1.0";

    @Mod.Instance(MODID)
    public static Main instance;

    @GameRegistry.ObjectHolder(Main.MODID +":"+ "exp_ore_block")
    public static final BlockExpOre BLOCK_EXP_ORE = new BlockExpOre();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        GameRegistry.registerWorldGenerator(new WorldGenExpOreBlock(), 0);
        RecipeHandler.addRecipes();
    }

}
