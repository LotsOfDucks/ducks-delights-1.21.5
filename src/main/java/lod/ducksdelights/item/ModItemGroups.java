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
                    }).build());

    public static final ItemGroup DUCKSDELIGHTS_MATERIALS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DucksDelights.MOD_ID, "ducksdelights_materials"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.HAUNTED_STEEL_INGOT))
                    .displayName(Text.translatable("itemgroup.ducksdelights.materials"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.MOTE_OF_CREATION);
                        //entries.add(ModItems.MOTE_OF_DESTRUCTION);
                        //entries.add(ModItems.MOTE_UPGRADE_TEMPLATE);
                        entries.add(ModItems.HAUNTED_METAL_SCRAP);
                        entries.add(ModItems.HAUNTED_METAL_SHEETS);
                        entries.add(ModItems.HAUNTED_STEEL_INGOT);
                        entries.add(ModBlocks.HAUNTED_STEEL_BLOCK);
                    }).build());

    public static final ItemGroup DUCKSDELIGHTS_TOOLSANDEQUIP_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DucksDelights.MOD_ID, "ducksdelights_toolsandequip"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.HAUNTED_STEEL_PICKAXE))
                    .displayName(Text.translatable("itemgroup.ducksdelights.toolsandequip"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.HAUNTED_STEEL_SHOVEL);
                        entries.add(ModItems.HAUNTED_STEEL_PICKAXE);
                        entries.add(ModItems.HAUNTED_STEEL_AXE);
                        entries.add(ModItems.HAUNTED_STEEL_HOE);
                        entries.add(ModItems.HAUNTED_STEEL_SWORD);
                        entries.add(ModItems.HAUNTED_STEEL_HELMET);
                        entries.add(ModItems.HAUNTED_STEEL_CHESTPLATE);
                        entries.add(ModItems.HAUNTED_STEEL_LEGGINGS);
                        entries.add(ModItems.HAUNTED_STEEL_BOOTS);
                        //entries.add(ModItems.CREATION_IMBUED_HELMET);
                        //entries.add(ModItems.CREATION_IMBUED_CHESTPLATE);
                        //entries.add(ModItems.CREATION_IMBUED_LEGGINGS);
                        //entries.add(ModItems.CREATION_IMBUED_BOOTS);
                        //entries.add(ModItems.DESTRUCTION_IMBUED_HELMET);
                        //entries.add(ModItems.DESTRUCTION_IMBUED_CHESTPLATE);
                        //entries.add(ModItems.DESTRUCTION_IMBUED_LEGGINGS);
                        //entries.add(ModItems.DESTRUCTION_IMBUED_BOOTS);
                        entries.add(ModItems.HAUNTED_STEEL_BUCKET);
                        entries.add(ModItems.HAUNTED_STEEL_WATER_BUCKET);
                        entries.add(ModItems.HAUNTED_STEEL_LAVA_BUCKET);
                        entries.add(ModItems.OVERFLOWING_WATER_BUCKET);
                        entries.add(ModItems.OVERFLOWING_LAVA_BUCKET);
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
                        entries.add(ModItems.ONIGIRI);
                        entries.add(ModItems.CHICKEN_ONIGIRI);
                        entries.add(ModItems.PORK_ONIGIRI);
                        entries.add(ModItems.BEEF_ONIGIRI);
                        entries.add(ModItems.SALMON_ONIGIRI);
                        entries.add(ModItems.GILDED_ONIGIRI);
                        entries.add(ModItems.KIBBLESTONE);
                        entries.add(ModItems.PLAIN_ROCK_CANDY);
                        entries.add(ModItems.WHITE_ROCK_CANDY);
                        entries.add(ModItems.LIGHT_GRAY_ROCK_CANDY);
                        entries.add(ModItems.GRAY_ROCK_CANDY);
                        entries.add(ModItems.BLACK_ROCK_CANDY);
                        entries.add(ModItems.BROWN_ROCK_CANDY);
                        entries.add(ModItems.RED_ROCK_CANDY);
                        entries.add(ModItems.ORANGE_ROCK_CANDY);
                        entries.add(ModItems.YELLOW_ROCK_CANDY);
                        entries.add(ModItems.LIME_ROCK_CANDY);
                        entries.add(ModItems.GREEN_ROCK_CANDY);
                        entries.add(ModItems.CYAN_ROCK_CANDY);
                        entries.add(ModItems.LIGHT_BLUE_ROCK_CANDY);
                        entries.add(ModItems.BLUE_ROCK_CANDY);
                        entries.add(ModItems.PURPLE_ROCK_CANDY);
                        entries.add(ModItems.MAGENTA_ROCK_CANDY);
                        entries.add(ModItems.PINK_ROCK_CANDY);
                    }).build());



    public static void initialize() {
        DucksDelights.LOGGER.info("Registering Item Groups for " + DucksDelights.MOD_ID);
    }
}
