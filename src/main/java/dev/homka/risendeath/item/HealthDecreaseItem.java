package dev.homka.risendeath.item;

import dev.homka.risendeath.AllAttachments;
import dev.homka.risendeath.custom.HealthAttributeSync;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HealthDecreaseItem extends Item {

    public HealthDecreaseItem(Properties properties) {
        super(properties);
    }

    private static final double MIN_HEALTH = 6.0;
    private static final double MAX_HEALTH = 40.0;
    private static final double DEATH_PENALITY = 2.0;
    private static final double BONUS_HEALTH = 2.0;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        final ItemStack stack = player.getItemInHand(usedHand);

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResultHolder.fail(stack);
        }

        double currentPlayerHealth = serverPlayer.getData(AllAttachments.CUSTOM_HEALTH.get());
        double newPlayerHealth = Math.max(MIN_HEALTH, currentPlayerHealth - DEATH_PENALITY);

        if (currentPlayerHealth > newPlayerHealth) {
            serverPlayer.setData(AllAttachments.CUSTOM_HEALTH.get(), newPlayerHealth);

            // Синхронизируем Реальное хп, с нашим кастомным
            HealthAttributeSync.syncMaxHealth(serverPlayer);

            stack.shrink(1);
            player.playNotifySound(SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.PLAYERS, 0.6f, 2f);

            //DEBUG
            serverPlayer.sendSystemMessage(
                    Component.literal("custom_health: " + currentPlayerHealth + " -> " + newPlayerHealth), true
            );

            return InteractionResultHolder.success(stack);
        }

        //DEBUG
        serverPlayer.sendSystemMessage(
                Component.literal("You have minimum HP: " + currentPlayerHealth),
                true
        );
        player.playNotifySound(SoundEvents.ANVIL_DESTROY, SoundSource.PLAYERS, 0.6f, 2f);
        return InteractionResultHolder.fail(stack);
    }
}
