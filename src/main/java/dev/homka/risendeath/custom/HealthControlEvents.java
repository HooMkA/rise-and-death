package dev.homka.risendeath.custom;

import dev.homka.risendeath.AllAttachments;
import dev.homka.risendeath.RiseAndDeath;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = RiseAndDeath.MODID)
public class HealthControlEvents {

    private static final double MIN_HEALTH = 6.0;
    private static final double MAX_HEALTH = 40.0;
    private static final double DEATH_PENALITY = 2.0;
    private static final double BONUS_HEALTH = 2.0;
    /*@SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.level().isClientSide() && entity instanceof Player player) {
            player.heal(1);
        }
    }*/

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event){
        if (!event.isWasDeath()) return;

        //Calculating new player hp
        double oldPlayerHealth = event.getOriginal().getData(AllAttachments.CUSTOM_HEALTH.get());
        double newPlayerHealth = Math.max(MIN_HEALTH, oldPlayerHealth - DEATH_PENALITY);

        //setting up new hp
        event.getEntity().setData(AllAttachments.CUSTOM_HEALTH.get(), newPlayerHealth);


        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            // Синхронизируем Реальное хп, с нашим кастомным
            HealthAttributeSync.syncMaxHealth(serverPlayer);
            HealthAttributeSync.healToFull(serverPlayer);

            //DEBUG
            serverPlayer.sendSystemMessage(
                    Component.literal("custom_health: " + oldPlayerHealth + " -> " + newPlayerHealth)
            );
        }
    }

    /*
    @SubscribeEvent
    public static void playerUsesCursedHearth(PlayerInteractEvent.RightClickItem event) {
        if (!event.getItemStack().is(AllModItems.CURSED_HEART.get())) return;

        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            double currentPlayerHealth = serverPlayer.getData(AllAttachments.CUSTOM_HEALTH.get());
            double newPlayerHealth = Math.min(MAX_HEALTH, currentPlayerHealth + BONUS_HEALTH);

            serverPlayer.setData(AllAttachments.CUSTOM_HEALTH.get(), newPlayerHealth);

            // Синхронизируем Реальное хп, с нашим кастомным
            HealthAttributeSync.syncMaxHealth(serverPlayer);
            HealthAttributeSync.healToFull(serverPlayer);

            //DEBUG
            serverPlayer.sendSystemMessage(
                    Component.literal("custom_health: " + currentPlayerHealth + " -> " + newPlayerHealth), true
            );
        }
    }*/

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            // Синхронизируем Реальное хп, с нашим кастомным
            HealthAttributeSync.syncMaxHealth(serverPlayer);
        }

    }
}
