package lod.ducksdelights;

import lod.ducksdelights.block.ModBlocks;
import lod.ducksdelights.entity.ModBlockEntityTypes;
import lod.ducksdelights.item.ModConsumableComponents;
import lod.ducksdelights.item.ModFoodComponents;
import lod.ducksdelights.item.ModItemGroups;
import lod.ducksdelights.item.ModItems;
import lod.ducksdelights.item.ModPotions;
import lod.ducksdelights.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DucksDelights implements ModInitializer {
	public static final String MOD_ID = "ducksdelights";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.initialize();
		ModItems.initialize();
		ModBlocks.initialize();
		ModBlockEntityTypes.initialize();
		ModSounds.initialize();
		ModFoodComponents.initialize();
		ModConsumableComponents.initialize();
		ModPotions.registerPotions();

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(Potions.AWKWARD, ModItems.MOTE_OF_CREATION, ModPotions.HEALTH_BOOST_POTION);
			builder.registerPotionRecipe(ModPotions.HEALTH_BOOST_POTION, Items.REDSTONE, ModPotions.LONG_HEALTH_BOOST_POTION);
			builder.registerPotionRecipe(ModPotions.HEALTH_BOOST_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_HEALTH_BOOST_POTION);
		});
	}
}