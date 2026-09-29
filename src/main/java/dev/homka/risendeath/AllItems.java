package dev.homka.risendeath;

import dev.homka.risendeath.item.HealthManipulationItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RiseAndDeath.MODID);

    public static final DeferredItem<Item> CURSED_HEART = ITEMS.register("cursed_heart",
            () -> new HealthManipulationItem(new Item.Properties(), false));


    public static final DeferredItem<Item> HEART_CONTAINER = ITEMS.register("heart_container",
            () -> new HealthManipulationItem(new Item.Properties()));

    public static final DeferredItem<Item> HEART_FRAGMENT = ITEMS.register("heart_fragment",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
