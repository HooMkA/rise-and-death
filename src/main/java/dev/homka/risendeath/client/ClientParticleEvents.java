package dev.homka.risendeath.client;

import dev.homka.risendeath.AllParticleTypes;
import dev.homka.risendeath.client.animations.HeartRingParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = "risendeath", value = Dist.CLIENT)
public class ClientParticleEvents {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AllParticleTypes.HEART_CONTAINER_BURST.get(), HeartRingParticle.Provider::new);
        event.registerSpriteSet(AllParticleTypes.CURSED_HEART_BURST.get(), HeartRingParticle.Provider::new);
    }



}
