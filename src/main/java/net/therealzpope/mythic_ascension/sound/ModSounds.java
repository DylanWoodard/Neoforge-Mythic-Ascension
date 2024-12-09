package net.therealzpope.mythic_ascension.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.therealzpope.mythic_ascension.MythicAscension;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MythicAscension.MOD_ID);

    public static final Supplier<SoundEvent> SILVER_BELL_STANDARD_RING = registerSoundEvent("silver_bell_standard_ring");
    public static final Supplier<SoundEvent> SILVER_BELL_FINALITY_RING = registerSoundEvent("silver_bell_finality_ring");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
