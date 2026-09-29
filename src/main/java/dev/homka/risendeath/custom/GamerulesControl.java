package dev.homka.risendeath.custom;

import dev.homka.risendeath.Config;
import dev.homka.risendeath.RiseAndDeath;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = RiseAndDeath.MODID)
public class GamerulesControl {

    @SubscribeEvent
    public static void onServerStart(ServerStartedEvent event) {
        if (Config.KEEP_INVENTORY.get()) {
            MinecraftServer server = event.getServer();
            server.getGameRules().getRule(GameRules.RULE_KEEPINVENTORY).set(true, server);
        }

    }

}
