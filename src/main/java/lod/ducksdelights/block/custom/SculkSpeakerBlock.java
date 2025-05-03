package lod.ducksdelights.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class SculkSpeakerBlock extends Block implements Waterloggable {
    public static final MapCodec<SculkSpeakerBlock> CODEC = createCodec(SculkSpeakerBlock::new);
    public static final BooleanProperty POWERED;
    public static final IntProperty TUNE;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape UP_SHAPE;
    protected static final VoxelShape LOW_SHAPE;
    protected static final VoxelShape SHAPE;
    public static final EnumProperty<Direction> FACING;

    public MapCodec<SculkSpeakerBlock> getCodec() {
        return CODEC;
    }

    public SculkSpeakerBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(TUNE, 1).with(POWERED, false).with(WATERLOGGED, false).with(FACING, Direction.NORTH));
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    protected VoxelShape getCullingShape(BlockState state) {
        return SHAPE;
    }

    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos);
        int a = world.getEmittedRedstonePower(pos.offset(state.get(FACING).getOpposite()), state.get(FACING).getOpposite());
        if (bl != state.get(POWERED)) {
            if (a > 0) {
                world.setBlockState(pos, state.with(POWERED, bl).with(TUNE, a));
            }else {
                world.setBlockState(pos, state.with(POWERED, bl));
            }
            if (bl) {
                this.playResonance(null, state, world, pos);
            }
        }
    }

    private void playResonance(@Nullable Entity entity, BlockState state, World world, BlockPos pos) {
        world.addSyncedBlockEvent(pos, this, 0, 0);
        int c = state.get(TUNE);
        switch(c){
            case 1:
                world.emitGameEvent(entity, GameEvent.RESONATE_1, pos);
                break;
            case 2:
                world.emitGameEvent(entity, GameEvent.RESONATE_2, pos);
                break;
            case 3:
                world.emitGameEvent(entity, GameEvent.RESONATE_3, pos);
                break;
            case 4:
                world.emitGameEvent(entity, GameEvent.RESONATE_4, pos);
                break;
            case 5:
                world.emitGameEvent(entity, GameEvent.RESONATE_5, pos);
                break;
            case 6:
                world.emitGameEvent(entity, GameEvent.RESONATE_6, pos);
                break;
            case 7:
                world.emitGameEvent(entity, GameEvent.RESONATE_7, pos);
                break;
            case 8:
                world.emitGameEvent(entity, GameEvent.RESONATE_8, pos);
                break;
            case 9:
                world.emitGameEvent(entity, GameEvent.RESONATE_9, pos);
                break;
            case 10:
                world.emitGameEvent(entity, GameEvent.RESONATE_10, pos);
                break;
            case 11:
                world.emitGameEvent(entity, GameEvent.RESONATE_11, pos);
                break;
            case 12:
                world.emitGameEvent(entity, GameEvent.RESONATE_12, pos);
                break;
            case 13:
                world.emitGameEvent(entity, GameEvent.RESONATE_13, pos);
                break;
            case 14:
                world.emitGameEvent(entity, GameEvent.RESONATE_14, pos);
                break;
            case 15:
                world.emitGameEvent(entity, GameEvent.RESONATE_15, pos);
        }
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            state = state.cycle(TUNE);
            world.setBlockState(pos, state);
            this.playResonance(player, state, world, pos);
            player.incrementStat(Stats.TUNE_NOTEBLOCK);
        }

        return ActionResult.SUCCESS;
    }

    protected void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isClient) {
            this.playResonance(player, state, world, pos);
            player.incrementStat(Stats.PLAY_NOTEBLOCK);
        }
    }

    public static float getNotePitch(int note) {
        return (float)Math.pow(2.0, (double)(note - 12) / 12.0);
    }

    protected boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        NoteBlockInstrument noteBlockInstrument = NoteBlockInstrument.BASEDRUM;
        float f;
        if (noteBlockInstrument.canBePitched()) {
            int i = state.get(TUNE);
            f = getNotePitch(i);
            world.addParticleClient(ParticleTypes.NOTE, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, (double)i / 24.0, 0.0, 0.0);
        } else {
            f = 1.0F;
        }
        RegistryEntry<SoundEvent> registryEntry = noteBlockInstrument.getSound();
        if (!(Boolean)state.get(WATERLOGGED)) {
            world.playSound(null, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, registryEntry, SoundCategory.RECORDS, 3.0F, f, world.random.nextLong());
        } else {
            double d = pos.getX();
            double e = pos.getY();
            double g = pos.getZ();
            world.addImportantParticleClient(ParticleTypes.BUBBLE, d + 0.5, e + 0.7, g + 0.5, 0.0, 0.04, 0.0);
            world.addImportantParticleClient(ParticleTypes.BUBBLE, d + Math.random(), e + 0.7, g + Math.random(), 0.0, 0.04, 0.0);
            world.addImportantParticleClient(ParticleTypes.BUBBLE_COLUMN_UP, d + Math.random(), e + 0.7, g + Math.random(), 0.0, 0.04, 0.0);
            world.addImportantParticleClient(ParticleTypes.BUBBLE_COLUMN_UP, d + Math.random(), e + 0.7, g + Math.random(), 0.0, 0.04, 0.0);
        }
        return true;
    }

    protected void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (dropExperience) {
            this.dropExperienceWhenMined(world, pos, tool, ConstantIntProvider.create(5));
        }

    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED, WATERLOGGED, TUNE, FACING);
    }

    static {
        POWERED = Properties.POWERED;
        WATERLOGGED = Properties.WATERLOGGED;
        TUNE = IntProperty.of("tune", 1, 15);
        UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
        LOW_SHAPE = Block.createCuboidShape(2.0, 8.0, 2.0, 14.0, 12.0, 14.0);
        SHAPE = VoxelShapes.union(UP_SHAPE, LOW_SHAPE);
        FACING = Properties.HORIZONTAL_FACING;
    }
}
