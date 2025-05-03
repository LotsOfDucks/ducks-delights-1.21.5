package lod.ducksdelights.item;

import lod.ducksdelights.DucksDelights;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import javax.naming.Name;

public class ModPotions {
    public static final RegistryEntry<Potion> HEALTH_BOOST_POTION = registerPotion("health_boost_potion",
            new Potion("health_boost_potion", new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 6000, 1)));

    public static final RegistryEntry<Potion> LONG_HEALTH_BOOST_POTION = registerPotion("long_health_boost_potion",
            new Potion("long_health_boost_potion", new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 9000, 1)));

    public static final RegistryEntry<Potion> STRONG_HEALTH_BOOST_POTION = registerPotion("strong_health_boost_potion",
            new Potion("strong_health_boost_potion", new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 6000, 4)));

    private static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(DucksDelights.MOD_ID, name), potion);
    }

    public static void registerPotions(){
    }
}
