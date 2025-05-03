package lod.ducksdelights.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.GlowLichenBlock;
import net.minecraft.block.MultifaceGrower;
import net.minecraft.block.MultifaceGrowthBlock;

public class ThornyVineBlock extends MultifaceGrowthBlock {
    public static final MapCodec<ThornyVineBlock> CODEC = createCodec(ThornyVineBlock::new);
    private final MultifaceGrower grower = new MultifaceGrower(this);

    public ThornyVineBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends MultifaceGrowthBlock> getCodec() {
        return null;
    }

    @Override
    public MultifaceGrower getGrower() {
        return null;
    }
}
