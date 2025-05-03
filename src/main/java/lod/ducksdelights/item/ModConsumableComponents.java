package lod.ducksdelights.item;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundEvents;

import java.util.List;

public class ModConsumableComponents {
    public static final ConsumableComponent WHITE_RICE;
    public static final ConsumableComponent GOLDEN_RICE;
    public static final ConsumableComponent GILDED_ONIGIRI;
    public static final ConsumableComponent KIBBLESTONE;






    public static ConsumableComponent.Builder food() {
        return ConsumableComponent.builder().consumeSeconds(1.6F).useAction(UseAction.EAT).sound(SoundEvents.ENTITY_GENERIC_EAT).consumeParticles(true);
    }

    public static ConsumableComponent.Builder drink() {
        return ConsumableComponent.builder().consumeSeconds(1.6F).useAction(UseAction.DRINK).sound(SoundEvents.ENTITY_GENERIC_DRINK).consumeParticles(false);
    }

    static {
        KIBBLESTONE = food().consumeSeconds(1.2F).build();
        WHITE_RICE = food().consumeSeconds(1.2F).build();
        GOLDEN_RICE = food().consumeSeconds(1.2F).build();
        GILDED_ONIGIRI = food().consumeEffect(new ApplyEffectsConsumeEffect(List.of(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 1), new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 1), new StatusEffectInstance(StatusEffects.NIGHT_VISION, 4800, 0)))).build();
    }

    public static void initialize() {
    }
}
