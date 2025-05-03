package lod.ducksdelights.item;

import com.google.common.collect.Maps;
import lod.ducksdelights.DucksDelights;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ModArmorMaterials {
    public static final RegistryKey<EquipmentAsset> HAUNTED_STEEL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(DucksDelights.MOD_ID, "haunted_steel"));
    public static final TagKey<Item> REPAIRS_HAUNTED_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(DucksDelights.MOD_ID, "repairs_haunted_armor"));
    public static final ArmorMaterial HAUNTED_STEEL_MATERIAL = new ArmorMaterial(
            20, createDefenseMap(2, 5, 7, 2, 6), 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 1.0F, 0.0F, REPAIRS_HAUNTED_ARMOR, HAUNTED_STEEL_MATERIAL_KEY
    );


    private static Map<EquipmentType, Integer> createDefenseMap(int bootsDefense, int leggingsDefense, int chestplateDefense, int helmetDefense, int bodyDefense) {
        return Maps.newEnumMap(
                Map.of(
                        EquipmentType.BOOTS,
                        bootsDefense,
                        EquipmentType.LEGGINGS,
                        leggingsDefense,
                        EquipmentType.CHESTPLATE,
                        chestplateDefense,
                        EquipmentType.HELMET,
                        helmetDefense,
                        EquipmentType.BODY,
                        bodyDefense
                )
        );
    }

    public static void initialize() {
    }
}
