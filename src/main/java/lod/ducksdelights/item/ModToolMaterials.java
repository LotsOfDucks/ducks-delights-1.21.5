package lod.ducksdelights.item;

import com.google.common.collect.Maps;
import lod.ducksdelights.DucksDelights;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
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

public class ModToolMaterials {
    public static final RegistryKey<EquipmentAsset> HAUNTED_STEEL_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(DucksDelights.MOD_ID, "haunted_steel"));
    public static final TagKey<Item> HAUNTED_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(DucksDelights.MOD_ID, "haunted_tool_materials"));
    public static final TagKey<Block> INCORRECT_FOR_HAUNTED_TOOL = TagKey.of(RegistryKeys.BLOCK, Identifier.of(DucksDelights.MOD_ID, "incorrect_for_haunted_tool"));
    public static final ToolMaterial HAUNTED_STEEL_MATERIAL = new ToolMaterial(
            ModToolMaterials.INCORRECT_FOR_HAUNTED_TOOL, 500, 7.0F, 2.0F, 14, ModToolMaterials.HAUNTED_TOOL_MATERIALS
    );




    public static void initialize() {
    }
}
