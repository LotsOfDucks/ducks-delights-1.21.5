package lod.ducksdelights.item;

import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent KIBBLESTONE = (new FoodComponent.Builder()).nutrition(1).saturationModifier(0.1F).build();
    public static final FoodComponent WHITE_RICE = (new FoodComponent.Builder()).nutrition(2).saturationModifier(0.4F).build();
    public static final FoodComponent GOLDEN_RICE = (new FoodComponent.Builder()).nutrition(4).saturationModifier(1.2F).build();
    public static final FoodComponent ONIGIRI = (new FoodComponent.Builder()).nutrition(5).saturationModifier(0.5F).build();
    public static final FoodComponent CHICKEN_ONIGIRI = (new FoodComponent.Builder()).nutrition(8).saturationModifier(0.6F).build();
    public static final FoodComponent BEEF_ONIGIRI = (new FoodComponent.Builder()).nutrition(10).saturationModifier(0.7F).build();
    public static final FoodComponent PORK_ONIGIRI = (new FoodComponent.Builder()).nutrition(10).saturationModifier(0.7F).build();
    public static final FoodComponent SALMON_ONIGIRI = (new FoodComponent.Builder()).nutrition(8).saturationModifier(0.6F).build();
    public static final FoodComponent GILDED_ONIGIRI = (new FoodComponent.Builder()).nutrition(14).saturationModifier(1.2F).alwaysEdible().build();

    private static FoodComponent.Builder createStew(int nutrition) {
        return (new FoodComponent.Builder()).nutrition(nutrition).saturationModifier(0.6F);
    }

    public static void initialize() {
    }
}
