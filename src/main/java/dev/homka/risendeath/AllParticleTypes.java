package dev.homka.risendeath;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, RiseAndDeath.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CURSED_HEART_BURST =
        PARTICLE_TYPES.register("cursed_heart_burst", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> HEART_CRYSTAL_BURST =
            PARTICLE_TYPES.register("heart_crystal_burst", () -> new SimpleParticleType(false));

}
