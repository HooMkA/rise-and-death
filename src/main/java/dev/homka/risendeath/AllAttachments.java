package dev.homka.risendeath;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AllAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, RiseAndDeath.MODID);

    public static final Supplier<AttachmentType<Double>> CUSTOM_HEALTH =
            ATTACHMENT_TYPES.register("custom_health",
                    () -> AttachmentType.builder(() -> 20.0)
                        .serialize(Codec.DOUBLE)
                            .build()
            );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }

}
