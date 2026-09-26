package dev.homka.risendeath;

import dev.homka.risendeath.item.HealthDecreaseItem;
import dev.homka.risendeath.item.HealthIncreaseItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RiseAndDeath.MODID);

    public static final DeferredItem<Item> CURSED_HEART = ITEMS.register("cursed_heart",
            () -> new HealthDecreaseItem(new Item.Properties()));


    public static final DeferredItem<Item> HEART_CRYSTAL = ITEMS.register("heart_crystal",
            () -> new HealthIncreaseItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
