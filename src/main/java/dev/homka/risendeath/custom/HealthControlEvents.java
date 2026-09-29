package dev.homka.risendeath.custom;

import dev.homka.risendeath.AllAttachments;
import dev.homka.risendeath.Config;
import dev.homka.risendeath.RiseAndDeath;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.*;

@EventBusSubscriber(modid = RiseAndDeath.MODID)
public class HealthControlEvents {



    //Пора уже писать конфиги))
    // Вроде написал ыыыыыыы

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
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        // проверяем какая это смерть
        // каждую N смерть у нас будут отбтрать хп...



        //Calculating new player hp
        double oldPlayerHealth = event.getOriginal().getData(AllAttachments.CUSTOM_HEALTH.get());

        // проверяем нужная ли смерть игрока
        // надо ли забирать хп
        int deathCount = serverPlayer.getStats().getValue(Stats.CUSTOM, Stats.DEATHS);
        if (deathCount % Config.DEATH_COUNT.get() != 0) {
            // если не нужно, то не забываем скопировать параметр и закончить нашу функцию
            event.getEntity().setData(AllAttachments.CUSTOM_HEALTH.get(), oldPlayerHealth);
            HealthAttributeSync.syncMaxHealth(serverPlayer);
            HealthAttributeSync.healToFull(serverPlayer);
            return;
        }

        double newPlayerHealth = Math.max(Config.MIN_HEALTH.get(), oldPlayerHealth - Config.DEATH_PENALITY.get());

        //setting up new hp
        event.getEntity().setData(AllAttachments.CUSTOM_HEALTH.get(), newPlayerHealth);

        // Синхронизируем Реальное хп, с нашим кастомным
        HealthAttributeSync.syncMaxHealth(serverPlayer);
        HealthAttributeSync.healToFull(serverPlayer);


        serverPlayer.sendSystemMessage(Component.literal("You lost your HP"), true);

        //DEBUG
        //serverPlayer.sendSystemMessage(Component.literal("DEATHS: " + deathCount));
        //
        //serverPlayer.sendSystemMessage(Component.literal("custom_health: " + oldPlayerHealth + " -> " + newPlayerHealth));
    }


    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            // Синхронизируем Реальное хп, с нашим кастомным
            HealthAttributeSync.syncMaxHealth(serverPlayer);
        }

    }


}
