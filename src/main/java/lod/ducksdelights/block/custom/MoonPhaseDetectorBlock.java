package lod.ducksdelights.block.custom;

import com.mojang.serialization.MapCodec;
import lod.ducksdelights.entity.ModBlockEntityTypes;
import lod.ducksdelights.entity.custom.MoonPhaseDetectorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class MoonPhaseDetectorBlock extends BlockWithEntity {
    public static final MapCodec<MoonPhaseDetectorBlock> CODEC = createCodec(MoonPhaseDetectorBlock::new);
    public static final IntProperty POWER;
    public static final IntProperty PHASE;
    protected static final VoxelShape SHAPE;
    public static final BooleanProperty SPECIFIC;

    public MapCodec<MoonPhaseDetectorBlock> getCodec() {
        return CODEC;
    }

    public MoonPhaseDetectorBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWER, 0).with(PHASE, 0).with(SPECIFIC, true));
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
        int moonSize = (int) Math.floor(world.getMoonSize() * 15) + 1;
        int moonPhase = world.getMoonPhase();
        boolean emitPower = true;
        float skyAngle = world.getSkyAngleRadians(1.0F);
        if (moonSize > 0) {
            if (skyAngle < 1.5707963F || skyAngle > 4.7123890F) {
                emitPower = false;
            }
        }
        moonSize = MathHelper.clamp(moonSize, 0, 15);
        if (state.get(PHASE) != moonPhase) {
            world.setBlockState(pos, state.with(PHASE, moonPhase));
        }
        int moonPhasePower = switch (moonPhase) {
            case 0 -> 6;
            case 1 -> 7;
            case 2 -> 8;
            case 3 -> 9;
            case 4 -> 1;
            case 5 -> 2;
            case 6 -> 3;
            case 7 -> 4;
            case 8 -> 5;
            default -> 0;
        };
        if (emitPower) {
            if (!state.get(SPECIFIC) && state.get(POWER) != moonSize) {
                world.setBlockState(pos, state.with(POWER, moonSize));
            } else if (state.get(SPECIFIC) && state.get(POWER) != moonPhasePower) {
                world.setBlockState(pos, state.with(POWER, moonPhasePower));
            }
        } else {
            world.setBlockState(pos, state.with(POWER, 0));
        }
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.canModifyBlocks()) {
            return super.onUse(state, world, pos, player, hit);
        } else {
            if (!world.isClient) {
                BlockState blockState = state.cycle(SPECIFIC);
                world.setBlockState(pos, blockState);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
                updateState(blockState, world, pos);
            }

            return ActionResult.SUCCESS;
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
        builder.add(POWER, PHASE, SPECIFIC);
    }

    static {
        POWER = Properties.POWER;
        PHASE = IntProperty.of("phase", 0, 7);
        SPECIFIC = BooleanProperty.of("specific");
        SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);
    }
}
