package lod.ducksdelights.sound;

import lod.ducksdelights.DucksDelights;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent DEMON_CORE_AMBIENT = registerSoundEvents("demon_core_ambient");

    public static final SoundEvent DEMON_CORE_TINK = registerSoundEvents("demon_core_tink");

    private static SoundEvent registerSoundEvents(String name) {
        Identifier id = Identifier.of(DucksDelights.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void initialize() {}
}

