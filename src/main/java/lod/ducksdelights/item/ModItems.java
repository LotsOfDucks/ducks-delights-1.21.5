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
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.item.equipment.ArmorMaterial;
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

    private static Function<Item.Settings, Item> createHauntedArmor(ArmorMaterial material, RegistryEntry<EntityAttribute> attribute, String attributeName, Integer value, EntityAttributeModifier.Operation operation, AttributeModifierSlot slot ,EquipmentType type) {
        return (settings) -> new Item(settings.maxDamage(type.getMaxDamage(material.durability()))
                .attributeModifiers(material.createAttributeModifiers(type).with(attribute, new EntityAttributeModifier(Identifier.of(DucksDelights.MOD_ID, attributeName), value, operation), slot))
                .enchantable(material.enchantmentValue())
                .component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(type.getEquipmentSlot()).equipSound(material.equipSound()).model(material.assetId()).build())
                .repairable(material.repairIngredient()));
    }

    private static Function<Item.Settings, Item> createCreationArmor(ArmorMaterial material, RegistryEntry<EntityAttribute> attribute, String attributeName, Integer value, EntityAttributeModifier.Operation operation, AttributeModifierSlot slot ,EquipmentType type) {
        return (settings) -> new Item(settings.maxDamage(type.getMaxDamage(material.durability()))
                .attributeModifiers(material.createAttributeModifiers(type).with(attribute, new EntityAttributeModifier(Identifier.of(DucksDelights.MOD_ID, attributeName), value, operation), slot))
                .enchantable(material.enchantmentValue())
                .component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(type.getEquipmentSlot()).equipSound(material.equipSound()).model(material.assetId()).build())
                .repairable(material.repairIngredient()));
    }

    private static Function<Item.Settings, Item> createDestructionArmor(ArmorMaterial material, RegistryEntry<EntityAttribute> attribute, String attributeName, Integer value, EntityAttributeModifier.Operation operation, AttributeModifierSlot slot ,EquipmentType type) {
        return (settings) -> new Item(settings.maxDamage(type.getMaxDamage(material.durability()))
                .attributeModifiers(material.createAttributeModifiers(type).with(attribute, new EntityAttributeModifier(Identifier.of(DucksDelights.MOD_ID, attributeName), value, operation), slot))
                .enchantable(material.enchantmentValue())
                .component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(type.getEquipmentSlot()).equipSound(material.equipSound()).model(material.assetId()).build())
                .repairable(material.repairIngredient()));
    }

    public static final Item MOTE_OF_CREATION = register("mote_of_creation", Item::new, new Item.Settings().rarity(Rarity.UNCOMMON).component(DataComponentTypes.DAMAGE_RESISTANT, new DamageResistantComponent(DamageTypeTags.IS_EXPLOSION)));
    public static final Item MOTE_OF_DESTRUCTION = register("mote_of_destruction", Item::new, new Item.Settings().rarity(Rarity.RARE).component(DataComponentTypes.DAMAGE_RESISTANT, new DamageResistantComponent(DamageTypeTags.IS_EXPLOSION)));
    public static final Item HAUNTED_METAL_SCRAP = register("haunted_metal_scrap", Item::new, new Item.Settings());
    public static final Item HAUNTED_METAL_SHEETS = register("haunted_metal_sheets", Item::new, new Item.Settings());
    public static final Item HAUNTED_STEEL_INGOT = register("haunted_steel_ingot", Item::new, new Item.Settings());
    public static final Item MOTE_UPGRADE_TEMPLATE = register("mote_upgrade_template", ModSmithingTemplateItems::createMoteUpgrade, (new Item.Settings()).rarity(Rarity.RARE));

    public static final Item HAUNTED_STEEL_HELMET = register("haunted_steel_helmet", createHauntedArmor(ModArmorMaterials.HAUNTED_STEEL_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_head", 2, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.HEAD), EquipmentType.HELMET), new Item.Settings());
    public static final Item HAUNTED_STEEL_CHESTPLATE = register("haunted_steel_chestplate", createHauntedArmor(ModArmorMaterials.HAUNTED_STEEL_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_chest", 3, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.CHEST), EquipmentType.CHESTPLATE), new Item.Settings());
    public static final Item HAUNTED_STEEL_LEGGINGS = register("haunted_steel_leggings", createHauntedArmor(ModArmorMaterials.HAUNTED_STEEL_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_legs", 2, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.LEGS), EquipmentType.LEGGINGS), new Item.Settings());
    public static final Item HAUNTED_STEEL_BOOTS = register("haunted_steel_boots", createHauntedArmor(ModArmorMaterials.HAUNTED_STEEL_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_feet", 1, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.FEET), EquipmentType.BOOTS), new Item.Settings());

    public static final Item CREATION_IMBUED_HELMET = register("creation_imbued_helmet", createHauntedArmor(ModArmorMaterials.CREATION_IMBUED_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_head", 4, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.HEAD), EquipmentType.HELMET), new Item.Settings());
    public static final Item CREATION_IMBUED_CHESTPLATE = register("creation_imbued_chestplate", createHauntedArmor(ModArmorMaterials.CREATION_IMBUED_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_chest", 7, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.CHEST), EquipmentType.CHESTPLATE), new Item.Settings());
    public static final Item CREATION_IMBUED_LEGGINGS = register("creation_imbued_leggings", createHauntedArmor(ModArmorMaterials.CREATION_IMBUED_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_legs", 6, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.LEGS), EquipmentType.LEGGINGS), new Item.Settings());
    public static final Item CREATION_IMBUED_BOOTS = register("creation_imbued_boots", createHauntedArmor(ModArmorMaterials.CREATION_IMBUED_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_feet", 3, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.FEET), EquipmentType.BOOTS), new Item.Settings());

    public static final Item DESTRUCTION_IMBUED_HELMET = register("destruction_imbued_helmet", createDestructionArmor(ModArmorMaterials.DESTRUCTION_IMBUED_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_head", -2, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.HEAD), EquipmentType.HELMET), new Item.Settings());
    public static final Item DESTRUCTION_IMBUED_CHESTPLATE = register("destruction_imbued_chestplate", createDestructionArmor(ModArmorMaterials.DESTRUCTION_IMBUED_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_chest", -3, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.CHEST), EquipmentType.CHESTPLATE), new Item.Settings());
    public static final Item DESTRUCTION_IMBUED_LEGGINGS = register("destruction_imbued_leggings", createDestructionArmor(ModArmorMaterials.DESTRUCTION_IMBUED_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_legs", -2, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.LEGS), EquipmentType.LEGGINGS), new Item.Settings());
    public static final Item DESTRUCTION_IMBUED_BOOTS = register("destruction_imbued_boots", createDestructionArmor(ModArmorMaterials.DESTRUCTION_IMBUED_MATERIAL, EntityAttributes.MAX_HEALTH, "haunted_health_boost_feet", -1, EntityAttributeModifier.Operation.ADD_VALUE, AttributeModifierSlot.forEquipmentSlot(EquipmentSlot.FEET), EquipmentType.BOOTS), new Item.Settings());

    public static final Item HAUNTED_STEEL_SWORD = register("haunted_steel_sword", Item::new, new Item.Settings().sword(ModToolMaterials.HAUNTED_STEEL_MATERIAL, 3.5F, -2.4F));
    public static final Item HAUNTED_STEEL_SHOVEL = registerTool("haunted_steel_shovel", (settings) -> new ShovelItem(ModToolMaterials.HAUNTED_STEEL_MATERIAL, 2.0F, -3.0F, settings));
    public static final Item HAUNTED_STEEL_PICKAXE = register("haunted_steel_pickaxe", Item::new, new Item.Settings().pickaxe(ModToolMaterials.HAUNTED_STEEL_MATERIAL, 1.5F, -2.8F));
    public static final Item HAUNTED_STEEL_AXE = registerTool("haunted_steel_axe", (settings) -> new AxeItem(ModToolMaterials.HAUNTED_STEEL_MATERIAL, 6.5F, -3.0F, settings));
    public static final Item HAUNTED_STEEL_HOE = registerTool("haunted_steel_hoe", (settings) -> new HoeItem(ModToolMaterials.HAUNTED_STEEL_MATERIAL, -2.0F, -0.5F, settings));

    public static final Item HAUNTED_STEEL_BUCKET = register("haunted_steel_bucket", createHauntedBucketItem(Fluids.EMPTY), new Item.Settings().maxCount(16));
    public static final Item HAUNTED_STEEL_WATER_BUCKET = register("haunted_steel_water_bucket", createHauntedBucketItem(Fluids.WATER), new Item.Settings().maxCount(16));
    public static final Item HAUNTED_STEEL_LAVA_BUCKET = register("haunted_steel_lava_bucket", createHauntedBucketItem(Fluids.LAVA), new Item.Settings().maxCount(16));
    public static final Item OVERFLOWING_WATER_BUCKET = register("overflowing_water_bucket", createInfiniteBucketItem(Fluids.WATER), new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON).component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true));
    public static final Item OVERFLOWING_LAVA_BUCKET = register("overflowing_lava_bucket", createInfiniteBucketItem(Fluids.LAVA), new Item.Settings().maxCount(1).rarity(Rarity.RARE).component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true));

    public static final Item KIBBLESTONE = register("kibblestone", Item::new, new Item.Settings().food(ModFoodComponents.KIBBLESTONE, ModConsumableComponents.KIBBLESTONE));
    public static final Item PLAIN_ROCK_CANDY = register("plain_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item WHITE_ROCK_CANDY = register("white_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item LIGHT_GRAY_ROCK_CANDY = register("light_gray_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item GRAY_ROCK_CANDY = register("gray_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item BLACK_ROCK_CANDY = register("black_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item BROWN_ROCK_CANDY = register("brown_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item RED_ROCK_CANDY = register("red_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item ORANGE_ROCK_CANDY = register("orange_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item YELLOW_ROCK_CANDY = register("yellow_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item LIME_ROCK_CANDY = register("lime_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item GREEN_ROCK_CANDY = register("green_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item CYAN_ROCK_CANDY = register("cyan_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item LIGHT_BLUE_ROCK_CANDY = register("light_blue_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item BLUE_ROCK_CANDY = register("blue_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item PURPLE_ROCK_CANDY = register("purple_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item MAGENTA_ROCK_CANDY = register("magenta_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));
    public static final Item PINK_ROCK_CANDY = register("pink_rock_candy", Item::new, new Item.Settings().food(ModFoodComponents.ROCK_CANDY, ModConsumableComponents.ROCK_CANDY).useRemainder(Items.STICK));

    public static final Item RAW_RICE = register("raw_rice", createBlockItemWithUniqueName(ModBlocks.RICE_CROP), new Item.Settings());
    public static final Item RAW_GOLDEN_RICE = register("raw_golden_rice", Item::new ,new Item.Settings());
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
