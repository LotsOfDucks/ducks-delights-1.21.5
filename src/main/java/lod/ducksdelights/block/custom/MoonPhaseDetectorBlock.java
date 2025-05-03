package lod.ducksdelights.block.custom;

import com.mojang.serialization.MapCodec;
import lod.ducksdelights.entity.ModBlockEntityTypes;
import lod.ducksdelights.entity.custom.MoonPhaseDetectorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MoonPhaseDetectorBlock extends BlockWithEntity {
    public static final MapCodec<MoonPhaseDetectorBlock> CODEC = createCodec(MoonPhaseDetectorBlock::new);
    public static final IntProperty POWER;
    public static final IntProperty PHASE;
    protected static final VoxelShape SHAPE;

    public MapCodec<MoonPhaseDetectorBlock> getCodec() {
        return CODEC;
    }

    public MoonPhaseDetectorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWER, 0).with(PHASE, 0));
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWER);
    }

    private static void updateState(BlockState state, World world, BlockPos pos) {
        int i = (int) Math.floor(world.getMoonSize() * 15) + 1;
        int h = world.getMoonPhase();
        float f = world.getSkyAngleRadians(1.0F);
        if (i > 0) {
            if (f < 1.5707963F || f > 4.7123890F) {
                i = 0;
            }
        }
        i = MathHelper.clamp(i, 0, 15);
        if (state.get(POWER) != i) {
            world.setBlockState(pos, state.with(POWER, i));
        }
        if (state.get(PHASE) != h) {
            world.setBlockState(pos, state.with(PHASE, h));
        }
    }

    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MoonPhaseDetectorBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return !world.isClient && world.getDimension().hasSkyLight() ? validateTicker(type, ModBlockEntityTypes.MOON_PHASE_DETECTOR, MoonPhaseDetectorBlock::tick) : null;
    }

    private static void tick(World world, BlockPos pos, BlockState state, MoonPhaseDetectorBlockEntity blockEntity) {
        if (world.getTime() % 20L == 0L) {
            updateState(state, world, pos);
        }

    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER, PHASE);
    }

    static {
        POWER = Properties.POWER;
        PHASE = IntProperty.of("phase", 0, 7);
        SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);
    }
}
