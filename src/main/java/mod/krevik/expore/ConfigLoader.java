package mod.krevik.expore;

import net.minecraftforge.common.ForgeConfigSpec;
import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
public class ConfigLoader {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SERVER_CONFIG;

    static
    {
        ConfigHandler.init(BUILDER);

        SERVER_CONFIG = BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .preserveInsertionOrder()
                .build();

        configData.load();
        spec.setConfig(configData);
    }
}