package mod.krevik.expore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class BlockExpOre extends Block {

    public BlockExpOre() {
        super(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(5f));
        setRegistryName(Main.MODID,"block_exp_ore");
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? MathHelper.nextInt(new Random(), Main.minimum_Exp_From_Ore, Main.maximum_Exp_From_Ore) : 0;
    }
}
