package dev.homka.risendeath.item;

import dev.homka.risendeath.AllAttachments;
import dev.homka.risendeath.AllParticleTypes;
import dev.homka.risendeath.Config;
import dev.homka.risendeath.custom.HealthAttributeSync;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static dev.homka.risendeath.Config.MIN_HEALTH;

public class HealthManipulationItem extends Item {
    private final boolean addingHealth;

    // Overload конструктор(перегруженный)
    //вот этот this(....) вызывает основной конструктор с параметрами в скобках
    public HealthManipulationItem(Properties properties) {
        this(properties, true);
    }

    // Кастомный КОНСТРУКТОР класса !!
    public HealthManipulationItem(Properties properties, boolean addingHealth) {
        super(properties);
        this.addingHealth = addingHealth;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        final ItemStack stack = player.getItemInHand(usedHand);

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResultHolder.fail(stack);
        }


        double currentPlayerHealth = serverPlayer.getData(AllAttachments.CUSTOM_HEALTH.get());
        double newPlayerHealth;
        // проверяем добавляем лм хп, или уменьшаем
        if (addingHealth) {
            newPlayerHealth = Math.min(Config.MAX_HEALTH.get(), currentPlayerHealth + Config.BONUS_HEALTH.get());
        }
        else {
            newPlayerHealth = Math.max(MIN_HEALTH.get(), currentPlayerHealth - Config.DEATH_PENALITY.get());
        }

        // Сработает, только если не упираемся в мин или макс
        if ((currentPlayerHealth > MIN_HEALTH.get() && !addingHealth) || (currentPlayerHealth < Config.MAX_HEALTH.get() && addingHealth)) {
            serverPlayer.setData(AllAttachments.CUSTOM_HEALTH.get(), newPlayerHealth);

            // Синхронизируем Реальное хп, с нашим кастомным
            HealthAttributeSync.syncMaxHealth(serverPlayer);
            if(addingHealth) HealthAttributeSync.healToFull(serverPlayer);

            if (!player.isCreative()) stack.shrink(1);
            player.playNotifySound(SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.PLAYERS, 0.6f, 2f);

            // Spawn particles
            if (level instanceof ServerLevel serverLevel) {
                // через лямбда выражение выбираем какие частицы нужны
                SimpleParticleType particleType = addingHealth
                        ? AllParticleTypes.HEART_CONTAINER_BURST.get()
                        : AllParticleTypes.CURSED_HEART_BURST.get();

                spawnBurstParticles(serverLevel, player, particleType);
            }



            //DEBUG
            serverPlayer.sendSystemMessage(Component.literal("custom_health: " + currentPlayerHealth + " -> " + newPlayerHealth), true);

            return InteractionResultHolder.success(stack);
        }


        //DEBUG
        if (addingHealth) {
            serverPlayer.sendSystemMessage(
                    Component.literal("You have maximum HP: " + currentPlayerHealth),
                    true
            );
        }
        else {
            serverPlayer.sendSystemMessage(
                    Component.literal("You have minimum HP: " + currentPlayerHealth),
                    true
            );
        }

        // Если упёрлись в минимум или максимум
        player.playNotifySound(SoundEvents.ANVIL_DESTROY, SoundSource.PLAYERS, 0.6f, 2f);

        return InteractionResultHolder.fail(stack);
    }

    private void spawnBurstParticles(ServerLevel serverLevel, Player player, SimpleParticleType particleType) {
        for (int i = 0; i < 20; i++) {
            float yaw = i * ((float) (2 * StrictMath.PI) / 20);
            float velocity = 0.2F;
            float vx = velocity * Mth.cos(yaw);
            float vz = velocity * Mth.sin(yaw);

            serverLevel.sendParticles(
                    particleType,
                    player.getX(), player.getY() + 1, player.getZ(),
                    0, vx, 0.0, vz,
                    1.0
            );
        }
    }
}
