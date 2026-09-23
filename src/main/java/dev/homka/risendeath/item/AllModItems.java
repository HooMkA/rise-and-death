package dev.homka.risendeath.item;

import dev.homka.risendeath.RiseAndDeath;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RiseAndDeath.MODID);

    public static final DeferredItem<Item> CURSED_HEART = ITEMS.register("cursed_heart",
    () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
