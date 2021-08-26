package mod.krevik.expore;


import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class BlockExpOre extends OreBlock {

    public BlockExpOre() {
        super(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(5f, 2f).requiresCorrectToolForDrops());
        setRegistryName(Main.MODID,"block_exp_ore");
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? UniformInt.of(Main.minimum_Exp_From_Ore, Main.maximum_Exp_From_Ore).sample(RANDOM) : 0;
    }
}
