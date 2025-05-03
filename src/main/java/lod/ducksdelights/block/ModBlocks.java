package lod.ducksdelights.block;

import lod.ducksdelights.DucksDelights;
import lod.ducksdelights.block.custom.*;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        if (shouldRegisterItem) {
           RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(DucksDelights.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(DucksDelights.MOD_ID, name));
    }

    public static final Block SCULK_SPEAKER = register(
            "sculk_speaker",
            SculkSpeakerBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.CYAN).strength(1.5F).sounds(BlockSoundGroup.SCULK_SENSOR).nonOpaque(),
            true
    );

    public static final Block DEMON_CORE = register(
            "demon_core",
            DemonCoreBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.BLACK).requiresTool().strength(50.0F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).luminance((state) -> 3).emissiveLighting((state, world, pos) -> state.get(DemonCoreBlock.POWERED)),
            true
    );

    public static final Block MOON_PHASE_DETECTOR = register(
            "moon_phase_detector",
            MoonPhaseDetectorBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS).strength(0.2F).sounds(BlockSoundGroup.WOOD).burnable(),
            true
    );

    public static final Block PADDY_FARMLAND = register(
            "paddy_farmland",
            PaddyFarmlandBlock::new,
            AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.GRAVEL).blockVision(Blocks::always).suffocates(Blocks::always),
            true
    );

    public static final Block HAUNTED_STEEL_BLOCK = register(
            "haunted_steel_block",
            Block::new,
            AbstractBlock.Settings.create().mapColor(MapColor.CYAN).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresTool().strength(5.0F, 6.0F).sounds(BlockSoundGroup.NETHERITE),
            true
    );

    public static final Block RICE_CROP = register(
            "rice_crop",
            RiceCropBlock::new,
            AbstractBlock.Settings.create().mapColor((state) -> state.get(CropBlock.AGE) >= 6 ? MapColor.YELLOW : MapColor.DARK_GREEN).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY),
            true
    );

    public static final Block GOLDEN_RICE_CROP = register(
            "golden_rice_crop",
            GoldenRiceCropBlock::new,
            AbstractBlock.Settings.create().mapColor((state) -> state.get(CropBlock.AGE) >= 6 ? MapColor.YELLOW : MapColor.DARK_GREEN).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY),
            true
    );

    public static void initialize() {}
}
