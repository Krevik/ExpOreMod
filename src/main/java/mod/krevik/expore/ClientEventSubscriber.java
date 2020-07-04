package mod.krevik.expore;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@Mod.EventBusSubscriber(modid = Main.MODID, value = CLIENT)
public final class ClientEventSubscriber {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Main.BLOCK_EXP_ORE.initModel();
    }
}