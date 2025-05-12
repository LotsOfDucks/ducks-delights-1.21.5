package lod.ducksdelights.item;

import lod.ducksdelights.DucksDelights;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;

public class ModSmithingTemplateItems extends SmithingTemplateItem {
    private static final Formatting DESCRIPTION_FORMATTING;
    private static final Text MOTE_UPGRADE_APPLIES_TO_TEXT;
    private static final Text MOTE_UPGRADE_INGREDIENTS_TEXT;
    private static final Text MOTE_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT;
    private static final Text MOTE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT;
    private static final Identifier EMPTY_ARMOR_SLOT_HELMET_TEXTURE;
    private static final Identifier EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE;
    private static final Identifier EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE;
    private static final Identifier EMPTY_ARMOR_SLOT_BOOTS_TEXTURE;
    private static final Identifier EMPTY_SLOT_HOE_TEXTURE;
    private static final Identifier EMPTY_SLOT_AXE_TEXTURE;
    private static final Identifier EMPTY_SLOT_SWORD_TEXTURE;
    private static final Identifier EMPTY_SLOT_SHOVEL_TEXTURE;
    private static final Identifier EMPTY_SLOT_PICKAXE_TEXTURE;
    private static final Identifier EMPTY_SLOT_MOTE_CREATION_TEXTURE;
    private static final Identifier EMPTY_SLOT_MOTE_DESTRUCTION_TEXTURE;

    public ModSmithingTemplateItems(Text appliesToText, Text ingredientsText, Text baseSlotDescriptionText, Text additionsSlotDescriptionText, List<Identifier> emptyBaseSlotTextures, List<Identifier> emptyAdditionsSlotTextures, Settings settings) {
        super(appliesToText, ingredientsText, baseSlotDescriptionText, additionsSlotDescriptionText, emptyBaseSlotTextures, emptyAdditionsSlotTextures, settings);
    }

    public static SmithingTemplateItem createMoteUpgrade(Item.Settings settings) {
        return new SmithingTemplateItem(MOTE_UPGRADE_APPLIES_TO_TEXT, MOTE_UPGRADE_INGREDIENTS_TEXT, MOTE_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT, MOTE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT, getMoteUpgradeEmptyBaseSlotTextures(), getMoteUpgradeEmptyAdditionsSlotTextures(), settings);
    }

    private static List<Identifier> getMoteUpgradeEmptyBaseSlotTextures() {
        return List.of(EMPTY_ARMOR_SLOT_HELMET_TEXTURE, EMPTY_SLOT_SWORD_TEXTURE, EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE, EMPTY_SLOT_PICKAXE_TEXTURE, EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE, EMPTY_SLOT_AXE_TEXTURE, EMPTY_ARMOR_SLOT_BOOTS_TEXTURE, EMPTY_SLOT_HOE_TEXTURE, EMPTY_SLOT_SHOVEL_TEXTURE);
    }

    private static List<Identifier> getMoteUpgradeEmptyAdditionsSlotTextures() {
        return List.of(EMPTY_SLOT_MOTE_CREATION_TEXTURE, EMPTY_SLOT_MOTE_DESTRUCTION_TEXTURE);
    }

    static {
        DESCRIPTION_FORMATTING = Formatting.BLUE;
        MOTE_UPGRADE_APPLIES_TO_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(DucksDelights.MOD_ID,"smithing_template.mote_upgrade.applies_to"))).formatted(DESCRIPTION_FORMATTING);
        MOTE_UPGRADE_INGREDIENTS_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(DucksDelights.MOD_ID,"smithing_template.mote_upgrade.ingredients"))).formatted(DESCRIPTION_FORMATTING);
        MOTE_UPGRADE_BASE_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(DucksDelights.MOD_ID,"smithing_template.mote_upgrade.base_slot_description")));
        MOTE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION_TEXT = Text.translatable(Util.createTranslationKey("item", Identifier.of(DucksDelights.MOD_ID,"smithing_template.mote_upgrade.additions_slot_description")));
        EMPTY_ARMOR_SLOT_HELMET_TEXTURE = Identifier.ofVanilla("container/slot/helmet");
        EMPTY_ARMOR_SLOT_CHESTPLATE_TEXTURE = Identifier.ofVanilla("container/slot/chestplate");
        EMPTY_ARMOR_SLOT_LEGGINGS_TEXTURE = Identifier.ofVanilla("container/slot/leggings");
        EMPTY_ARMOR_SLOT_BOOTS_TEXTURE = Identifier.ofVanilla("container/slot/boots");
        EMPTY_SLOT_HOE_TEXTURE = Identifier.ofVanilla("container/slot/hoe");
        EMPTY_SLOT_AXE_TEXTURE = Identifier.ofVanilla("container/slot/axe");
        EMPTY_SLOT_SWORD_TEXTURE = Identifier.ofVanilla("container/slot/sword");
        EMPTY_SLOT_SHOVEL_TEXTURE = Identifier.ofVanilla("container/slot/shovel");
        EMPTY_SLOT_PICKAXE_TEXTURE = Identifier.ofVanilla("container/slot/pickaxe");
        EMPTY_SLOT_MOTE_CREATION_TEXTURE = Identifier.of(DucksDelights.MOD_ID,"container/slot/mote_creation");
        EMPTY_SLOT_MOTE_DESTRUCTION_TEXTURE = Identifier.of(DucksDelights.MOD_ID,"container/slot/mote_destruction");
    }
}
