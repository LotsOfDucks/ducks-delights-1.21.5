package lod.ducksdelights.item;

import lod.ducksdelights.DucksDelights;
import lod.ducksdelights.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup DUCKSDELIGHTS_MISC_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DucksDelights.MOD_ID, "ducksdelights_misc"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModBlocks.SCULK_SPEAKER))
                    .displayName(Text.translatable("itemgroup.ducksdelights.misc"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.SCULK_SPEAKER);
                        entries.add(ModBlocks.MOON_PHASE_DETECTOR);
                        entries.add(ModBlocks.DEMON_CORE);
                        entries.add(ModItems.HAUNTED_STEEL_BUCKET);
                        entries.add(ModItems.HAUNTED_STEEL_WATER_BUCKET);
                        entries.add(ModItems.HAUNTED_STEEL_LAVA_BUCKET);
                        entries.add(ModItems.OVERFLOWING_LAVA_BUCKET);
                        entries.add(ModItems.OVERFLOWING_WATER_BUCKET);
                    }).build());

    public static final ItemGroup DUCKSDELIGHTS_MATERIALS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DucksDelights.MOD_ID, "ducksdelights_materials"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.HAUNTED_STEEL_INGOT))
                    .displayName(Text.translatable("itemgroup.ducksdelights.materials"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.MOTE_OF_CREATION);
                        entries.add(ModItems.HAUNTED_METAL_SCRAP);
                        entries.add(ModItems.HAUNTED_METAL_SHEETS);
                        entries.add(ModItems.HAUNTED_STEEL_INGOT);
                        entries.add(ModBlocks.HAUNTED_STEEL_BLOCK);
                    }).build());

    public static final ItemGroup DUCKSDELIGHTS_FOOD_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DucksDelights.MOD_ID, "ducksdelights_food"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.RAW_RICE))
                    .displayName(Text.translatable("itemgroup.ducksdelights.food"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.PADDY_FARMLAND);
                        entries.add(ModItems.RAW_RICE);
                        entries.add(ModItems.RAW_GOLDEN_RICE);
                        entries.add(ModItems.WHITE_RICE);
                        entries.add(ModItems.GOLDEN_RICE);
                        entries.add(ModItems.CHICKEN_ONIGIRI);
                        entries.add(ModItems.PORK_ONIGIRI);
                        entries.add(ModItems.BEEF_ONIGIRI);
                        entries.add(ModItems.SALMON_ONIGIRI);
                        entries.add(ModItems.GILDED_ONIGIRI);
                        entries.add(ModItems.KIBBLESTONE);
                    }).build());



    public static void initialize() {
        DucksDelights.LOGGER.info("Registering Item Groups for " + DucksDelights.MOD_ID);
    }
}
