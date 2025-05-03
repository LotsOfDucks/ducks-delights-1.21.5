package lod.ducksdelights.entity;

import lod.ducksdelights.block.ModBlocks;
import lod.ducksdelights.entity.custom.DemonCoreBlockEntity;
import lod.ducksdelights.entity.custom.MoonPhaseDetectorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of("ducksdelights", path), blockEntityType);
    }

    public static final BlockEntityType<DemonCoreBlockEntity> DEMON_CORE = register(
            "demon_core",
            // For versions before 1.21.2, please use BlockEntityType.Builder.
            FabricBlockEntityTypeBuilder.create(DemonCoreBlockEntity::new, ModBlocks.DEMON_CORE).build()
    );

    public static final BlockEntityType<MoonPhaseDetectorBlockEntity> MOON_PHASE_DETECTOR = register(
            "moon_phase_detector",
            // For versions before 1.21.2, please use BlockEntityType.Builder.
            FabricBlockEntityTypeBuilder.create(MoonPhaseDetectorBlockEntity::new, ModBlocks.MOON_PHASE_DETECTOR).build()
    );

    public static void initialize() {
    }
}
