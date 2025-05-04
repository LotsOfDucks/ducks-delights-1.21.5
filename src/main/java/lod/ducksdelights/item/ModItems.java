package lod.ducksdelights.item;

import lod.ducksdelights.DucksDelights;
import lod.ducksdelights.block.ModBlocks;
import lod.ducksdelights.item.custom.HauntedBucketItem;
import lod.ducksdelights.item.custom.InfiniteBucketItem;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.DamageResistantComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
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
    public static final Item HAUNTED_STEEL_HELMET = register("haunted_steel_helmet", Item::new, new Item.Settings().armor(ModArmorMaterials.HAUNTED_STEEL_MATERIAL, EquipmentType.HELMET).attributeModifiers(AttributeModifiersComponent.builder().add(EntityAttributes.MAX_HEALTH, new EntityAttributeModifier(Identifier.of(DucksDelights.MOD_ID, "haunted_health_boost_head"), 2, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.HEAD)).build()));
    public static final Item HAUNTED_STEEL_CHESTPLATE = register("haunted_steel_chestplate", Item::new, new Item.Settings().armor(ModArmorMaterials.HAUNTED_STEEL_MATERIAL, EquipmentType.CHESTPLATE).attributeModifiers(AttributeModifiersComponent.builder().add(EntityAttributes.MAX_HEALTH, new EntityAttributeModifier(Identifier.of(DucksDelights.MOD_ID, "haunted_health_boost_chest"), 3, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.CHEST)).build()));
    public static final Item HAUNTED_STEEL_LEGGINGS = register("haunted_steel_leggings", Item::new, new Item.Settings().armor(ModArmorMaterials.HAUNTED_STEEL_MATERIAL, EquipmentType.LEGGINGS).attributeModifiers(AttributeModifiersComponent.builder().add(EntityAttributes.MAX_HEALTH, new EntityAttributeModifier(Identifier.of(DucksDelights.MOD_ID, "haunted_health_boost_legs"), 2, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.LEGS)).build()));
    public static final Item HAUNTED_STEEL_BOOTS = register("haunted_steel_boots", Item::new, new Item.Settings().armor(ModArmorMaterials.HAUNTED_STEEL_MATERIAL, EquipmentType.BOOTS).attributeModifiers(AttributeModifiersComponent.builder().add(EntityAttributes.MAX_HEALTH, new EntityAttributeModifier(Identifier.of(DucksDelights.MOD_ID, "haunted_health_boost_feet"), 1, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.FEET)).build()));

    public static final Item HAUNTED_STEEL_SWORD = register("haunted_steel_sword", Item::new, new Item.Settings().sword(ModToolMaterials.HAUNTED_STEEL_MATERIAL, 3.5F, -2.4F));
    public static final Item HAUNTED_STEEL_SHOVEL = registerTool("haunted_steel_shovel", (settings) -> new ShovelItem(ModToolMaterials.HAUNTED_STEEL_MATERIAL, 2.0F, -3.0F, settings));
    public static final Item HAUNTED_STEEL_PICKAXE = register("haunted_steel_pickaxe", Item::new, new Item.Settings().pickaxe(ModToolMaterials.HAUNTED_STEEL_MATERIAL, 1.5F, -2.8F));
    public static final Item HAUNTED_STEEL_AXE = registerTool("haunted_steel_axe", (settings) -> new AxeItem(ModToolMaterials.HAUNTED_STEEL_MATERIAL, 6.5F, -3.0F, settings));
    public static final Item HAUNTED_STEEL_HOE = registerTool("haunted_steel_hoe", (settings) -> new HoeItem(ModToolMaterials.HAUNTED_STEEL_MATERIAL, -2.0F, -0.5F, settings));

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
    public static Item registerTool(String path, Function<Item.Settings, Item> factory) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("ducksdelights", path));
        return Items.register(registryKey, factory);
    }

    public static void initialize() {
    }
}
