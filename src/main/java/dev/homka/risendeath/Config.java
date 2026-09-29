package dev.homka.risendeath;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;


// СДЕЛАТЬ КОРРЕКЦИЮ У МИН И МАКС !!!
//

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /*public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);*/

    public static final ModConfigSpec.BooleanValue KEEP_INVENTORY = BUILDER
            .comment("On keep inventory, through config \n If true it will overwrite commands")
            .define("keepInventory",true);

    public static final ModConfigSpec.DoubleValue MIN_HEALTH = BUILDER
            .comment("A minimum health. Must be smaller then maxHealth")
            .defineInRange("minHealth", 6.0, 1.0, Double.MAX_VALUE);


    public static final ModConfigSpec.DoubleValue MAX_HEALTH = BUILDER
            .comment("A maximum health. Must be greater then minHealth")
            .defineInRange("maxHealth", 40.0, 2.0, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BONUS_HEALTH = BUILDER
            .comment("A bonus health per item")
            .defineInRange("bonusHealth", 2.0, 1.0, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue DEATH_PENALITY = BUILDER
            .comment("A health penality per death or item")
            .defineInRange("deathPenality", 2.0, 1.0, Double.MAX_VALUE);

    //каждую N смерть у нас будут отбтрать хп...
    public static final ModConfigSpec.IntValue DEATH_COUNT = BUILDER
            .comment("Each N death will be punished")
            .defineInRange("deathCount", 2, 1, Integer.MAX_VALUE);

    /*public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);


     */
    static final ModConfigSpec SPEC = BUILDER.build();

    /*
    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
    */
}
