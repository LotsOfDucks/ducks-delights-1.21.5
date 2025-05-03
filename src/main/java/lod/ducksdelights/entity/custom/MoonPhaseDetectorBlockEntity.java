package lod.ducksdelights.entity.custom;

import lod.ducksdelights.entity.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class MoonPhaseDetectorBlockEntity extends BlockEntity {
    public MoonPhaseDetectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.MOON_PHASE_DETECTOR, pos, state);
    }
}
