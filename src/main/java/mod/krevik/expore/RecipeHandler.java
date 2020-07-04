package mod.krevik.expore;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeHandler {

    public static void addRecipes() {
        if(ModConfig.should_add_recipe) {
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "Exp_Ore"), new ResourceLocation("Blocks"), new ItemStack(Main.BLOCK_EXP_ORE, 1), "XXX", "YYY", "AZA", Character.valueOf('X'), Items.COAL, Character.valueOf('Y'), Items.IRON_INGOT, Character.valueOf('A'), Items.GOLD_INGOT, Character.valueOf('Z'), Items.DIAMOND);
        }
    }
}