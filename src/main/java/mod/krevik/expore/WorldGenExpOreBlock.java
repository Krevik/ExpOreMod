package mod.krevik.expore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;
import java.util.function.Predicate;

public class WorldGenExpOreBlock implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
                         IChunkProvider chunkProvider) {
        switch(world.provider.getDimension()) {
//Nether
            case -1:
                break;
//Overworld
            case 0:
                if(ModConfig.should_Ore_Generate) {
                    runGenerator(world,random,chunkX,chunkZ);
                }
//End
            case 1:
                break;
//Everything else
            default:
                break;
        }
    }

    private void runGenerator(World world, Random rand, int chunk_X, int chunk_Z){
        if (ModConfig.maximum_Ore_Height>256)
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

        int size=MathHelper.clamp(rand.nextInt(ModConfig.maximum_Vein_Size+1),ModConfig.minimum_Vein_Size,ModConfig.maximum_Vein_Size);
        WorldGenMinable generator = new WorldGenMinable(Main.BLOCK_EXP_ORE.getDefaultState(), size, BlockMatcher.forBlock(Blocks.STONE));
        int heightdiff = ModConfig.maximum_Ore_Height - 5 +1;
        for (int i=0; i<ModConfig.veins_Per_Chunk; i++){
            int x = chunk_X * 16 +rand.nextInt(16);
            int y = 5 + rand.nextInt(heightdiff);
            int z = chunk_Z * 16 + rand.nextInt(16);

            generator.generate(world, rand, new BlockPos(x, y, z));
        }
    }
}