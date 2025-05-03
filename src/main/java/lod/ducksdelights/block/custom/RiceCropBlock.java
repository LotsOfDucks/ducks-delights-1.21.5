package lod.ducksdelights.block.custom;

import com.mojang.serialization.MapCodec;
import lod.ducksdelights.block.ModBlocks;
import lod.ducksdelights.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class RiceCropBlock extends CropBlock  {
    public static final MapCodec<RiceCropBlock> CODEC = createCodec(RiceCropBlock::new);
    public static final int MAX_AGE = 7;
    public static IntProperty AGE;
    private static final VoxelShape[] AGE_TO_SHAPE;
    private static final BooleanProperty GOLDEN;

    public MapCodec<RiceCropBlock> getCodec() {
        return CODEC;
    }

    public RiceCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0).with(GOLDEN, false));
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[this.getAge(state)];
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.PADDY_FARMLAND);
    }

    protected IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 7;
    }

     protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    if (i == 5 && random.nextInt(4999) == 0) {
                        world.setBlockState(pos, this.withAge(i + 1).with(GOLDEN,true), 2);
                    } else if (state.get(GOLDEN, true)) {
                        world.setBlockState(pos, this.withAge(i + 1).with(GOLDEN,true), 2);
                    } else {
                        world.setBlockState(pos, this.withAge(i + 1), 2);
                    }
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
        return f;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.RAW_RICE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, GOLDEN);
    }

    static {
        GOLDEN = BooleanProperty.of("golden");
        AGE = Properties.AGE_7;
        AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(7.0, -6.0, 7.0, 9.0, 1.0, 9.0),
                Block.createCuboidShape(6.0, -6.0, 6.0, 10.0, 3.0, 10.0),
                Block.createCuboidShape(5.0, -6.0, 5.0, 11.0, 6.0, 11.0),
                Block.createCuboidShape(5.0, -6.0, 5.0, 11.0, 9.0, 11.0),
                Block.createCuboidShape(4.0, -6.0, 4.0, 12.0, 11.0, 12.0),
                Block.createCuboidShape(4.0, -6.0, 4.0, 12.0, 13.0, 12.0),
                Block.createCuboidShape(3.0, -6.0, 3.0, 13.0, 15.0, 13.0),
                Block.createCuboidShape(3.0, -6.0, 3.0, 13.0, 14.0, 13.0)
        };
    }
}
