package lod.ducksdelights.block.custom;

import lod.ducksdelights.block.ModBlocks;
import lod.ducksdelights.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GoldenRiceCropBlock extends RiceCropBlock{
    public GoldenRiceCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0));
    }

    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 0, 1);
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(i + 1), 2);
                }
            }
        }
    }

    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockPos = pos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float g = 0.0F;
                BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
                if (blockState.contains(Properties.WATERLOGGED)) {
                    if (blockState.isOf(ModBlocks.PADDY_FARMLAND)) {
                        g = 1.0F;
                        if (blockState.get(PaddyFarmlandBlock.MOISTURE) > 0) {
                            g = 3.0F;
                        }
                    } else {
                        if (blockState.getFluidState().isOf(Fluids.WATER)) {
                            g = 3.0F;
                        } else {
                            g = 1.0F;
                        }
                    }
                }

                if (i != 0 || j != 0) {
                    g /= 4.0F;
                }

                f += g;
            }
        }
        if (world.getBlockState(blockPos).isOf(ModBlocks.PADDY_FARMLAND) && !world.getBlockState(blockPos).get(Properties.WATERLOGGED)) {
            f /= 2.0F;
        }
        return f / 2.0F;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.RAW_GOLDEN_RICE;
    }
}
