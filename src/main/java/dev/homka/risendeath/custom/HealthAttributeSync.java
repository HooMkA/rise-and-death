package dev.homka.risendeath.custom;

import dev.homka.risendeath.AllAttachments;
import dev.homka.risendeath.RiseAndDeath;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HealthAttributeSync {

    private static final ResourceLocation CUSTOM_HEALTH_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(RiseAndDeath.MODID, "custom_health_modifier");

    public static void syncMaxHealth(ServerPlayer player) {
        double customHealth = player.getData(AllAttachments.CUSTOM_HEALTH.get());
        AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);
        if (instance == null) return;

        double vanillaBase = instance.getBaseValue(); // обычно 20.0
        double diff = customHealth - vanillaBase;

        instance.addOrReplacePermanentModifier(new AttributeModifier(
                CUSTOM_HEALTH_MODIFIER_ID,
                diff,
                AttributeModifier.Operation.ADD_VALUE
        ));
    }

    // Хилим игрока до его реального значения максимального хп
    // Вызываем после сихронизации, чтобы не было ситуации, что добавили хп, а оно не выхилено
    public static void healToFull(ServerPlayer player) {
        player.setHealth((float) player.getMaxHealth());
    }
}
