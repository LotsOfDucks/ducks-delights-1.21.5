package lod.ducksdelights.item;

import lod.ducksdelights.block.ModBlocks;
import lod.ducksdelights.item.custom.HauntedBucketItem;
import lod.ducksdelights.item.custom.InfiniteBucketItem;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DamageResistantComponent;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public final class ModItems {
    private ModItems() {
    }

    private static Function<Item.Settings, Item> createBlockItemWithUniqueName(Block block) {
        return (settings) -> new BlockItem(block, settings.useItemPrefixedTranslationKey());
    }

    private static Function<Item.Settings, Item> createHauntedBucketItem(Fluid fluid) {
        return (settings) -> new HauntedBucketItem(fluid, settings.useItemPrefixedTranslationKey());
    }

    private static Function<Item.Settings, Item> createInfiniteBucketItem(Fluid fluid) {
        return (settings) -> new InfiniteBucketItem(fluid, settings.useItemPrefixedTranslationKey());
    }

    public static final Item MOTE_OF_CREATION = register("mote_of_creation", Item::new, new Item.Settings().rarity(Rarity.UNCOMMON).component(DataComponentTypes.DAMAGE_RESISTANT, new DamageResistantComponent(DamageTypeTags.IS_EXPLOSION)));
    public static final Item HAUNTED_METAL_SCRAP = register("haunted_metal_scrap", Item::new, new Item.Settings());
    public static final Item HAUNTED_METAL_SHEETS = register("haunted_metal_sheets", Item::new, new Item.Settings());
    public static final Item HAUNTED_STEEL_INGOT = register("haunted_steel_ingot", Item::new, new Item.Settings());
    public static final Item HAUNTED_STEEL_BUCKET = register("haunted_steel_bucket", createHauntedBucketItem(Fluids.EMPTY), new Item.Settings().maxCount(16));
    public static final Item HAUNTED_STEEL_WATER_BUCKET = register("haunted_steel_water_bucket", createHauntedBucketItem(Fluids.WATER), new Item.Settings().maxCount(16));
    public static final Item HAUNTED_STEEL_LAVA_BUCKET = register("haunted_steel_lava_bucket", createHauntedBucketItem(Fluids.LAVA), new Item.Settings().maxCount(16));
    public static final Item OVERFLOWING_WATER_BUCKET = register("overflowing_water_bucket", createInfiniteBucketItem(Fluids.WATER), new Item.Settings().maxCount(1));
    public static final Item OVERFLOWING_LAVA_BUCKET = register("overflowing_lava_bucket", createInfiniteBucketItem(Fluids.LAVA), new Item.Settings().maxCount(1));

    public static final Item KIBBLESTONE = register("kibblestone", Item::new, new Item.Settings().food(ModFoodComponents.KIBBLESTONE, ModConsumableComponents.KIBBLESTONE));

    public static final Item RAW_RICE = register("raw_rice", createBlockItemWithUniqueName(ModBlocks.RICE_CROP), new Item.Settings());
    public static final Item RAW_GOLDEN_RICE = register("raw_golden_rice", createBlockItemWithUniqueName(ModBlocks.GOLDEN_RICE_CROP), new Item.Settings());
    public static final Item WHITE_RICE = register("white_rice", Item::new, new Item.Settings().recipeRemainder(Items.BOWL).food(ModFoodComponents.WHITE_RICE, ModConsumableComponents.WHITE_RICE).useRemainder(Items.BOWL));
    public static final Item GOLDEN_RICE = register("golden_rice", Item::new, new Item.Settings().recipeRemainder(Items.BOWL).food(ModFoodComponents.GOLDEN_RICE, ModConsumableComponents.GOLDEN_RICE).useRemainder(Items.BOWL));
    public static final Item ONIGIRI = register("onigiri", Item::new, new Item.Settings().food(ModFoodComponents.ONIGIRI));
    public static final Item CHICKEN_ONIGIRI = register("chicken_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.CHICKEN_ONIGIRI));
    public static final Item BEEF_ONIGIRI = register("beef_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.BEEF_ONIGIRI));
    public static final Item PORK_ONIGIRI = register("pork_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.PORK_ONIGIRI));
    public static final Item SALMON_ONIGIRI = register("salmon_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.SALMON_ONIGIRI));
    public static final Item GILDED_ONIGIRI = register("gilded_onigiri", Item::new, new Item.Settings().food(ModFoodComponents.GILDED_ONIGIRI, ModConsumableComponents.GILDED_ONIGIRI));

    //public static final Item CUSTOM_ITEM = register("custom_item", Item::new, new Item.Settings());

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("ducksdelights", path));
        return Items.register(registryKey, factory, settings);
    }

    public static void initialize() {
    }
}
