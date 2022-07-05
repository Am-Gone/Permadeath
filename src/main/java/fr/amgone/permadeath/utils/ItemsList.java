package fr.amgone.permadeath.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsList {
    public static final ItemStack DIAMONED_NETHER_STAR;
    public static final ItemStack PERFECT_GOLD_NUGGET;

    static {
        DIAMONED_NETHER_STAR = new ItemStack(Material.NETHER_STAR);
        ItemMeta diamonedNetherStarMeta = DIAMONED_NETHER_STAR.getItemMeta();
        diamonedNetherStarMeta.displayName(Component.text("Diamoned Nether Star").color(NamedTextColor.AQUA));
        DIAMONED_NETHER_STAR.setItemMeta(diamonedNetherStarMeta);

        PERFECT_GOLD_NUGGET = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta perfectGoldNuggetMeta = PERFECT_GOLD_NUGGET.getItemMeta();
        perfectGoldNuggetMeta.displayName(Component.text("Perfect Gold Nugget").color(NamedTextColor.YELLOW));
        PERFECT_GOLD_NUGGET.setItemMeta(perfectGoldNuggetMeta);
    }
}
