package fr.amgone.permadeath;

import fr.amgone.permadeath.events.DaysCancelledEvents;
import fr.amgone.permadeath.utils.DaysListener;
import fr.amgone.permadeath.utils.GameFileConfig;
import fr.amgone.permadeath.utils.ItemsList;
import fr.amgone.permadeath.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    public static Plugin PLUGIN;
    @Override
    public void onEnable() {
        PLUGIN = this;
        try {
            GameFileConfig.readFile();
        } catch (IOException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Couldn't read the config file.", exception);
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new DaysCancelledEvents(), this);

        if(!Utils.hasDaysPassed(5)) {
            NamespacedKey netheriteKey = new NamespacedKey("minecraft", "netherite_ingot");
            Bukkit.removeRecipe(netheriteKey);

            ShapedRecipe diamonedNetherStarRecipe = new ShapedRecipe(new NamespacedKey("permadeath", "diamoned_nether_star"),
                    ItemsList.DIAMONED_NETHER_STAR);
            diamonedNetherStarRecipe.shape("DDD", "DND", "DDD");
            diamonedNetherStarRecipe.setIngredient('D', Material.DIAMOND);
            diamonedNetherStarRecipe.setIngredient('N', Material.NETHER_STAR);
            Bukkit.addRecipe(diamonedNetherStarRecipe);

            ShapedRecipe netheriteIngotRecipe = new ShapedRecipe(netheriteKey, new ItemStack(Material.NETHERITE_INGOT));
            netheriteIngotRecipe.shape("NNN", "NSP", "PPP");
            netheriteIngotRecipe.setIngredient('N', Material.NETHERITE_SCRAP);
            netheriteIngotRecipe.setIngredient('S', ItemsList.DIAMONED_NETHER_STAR);
            netheriteIngotRecipe.setIngredient('P', ItemsList.PERFECT_GOLD_NUGGET);
            Bukkit.addRecipe(netheriteIngotRecipe);

        } else if(Utils.hasDaysPassed(5) && !Utils.hasDaysPassed(10)) {

            ShapedRecipe reviveStoneRecipe = new ShapedRecipe(new NamespacedKey("permadeath", "revive_stone"),
                    ItemsList.REVIVE_STONE);
            reviveStoneRecipe.shape("CRC", "RTR", "CRC");
            reviveStoneRecipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
            reviveStoneRecipe.setIngredient('R', ItemsList.RAVAGER_BONE);
            reviveStoneRecipe.setIngredient('C', ItemsList.CRYING_GHAST_TEAR);
            Bukkit.addRecipe(reviveStoneRecipe);

        } else if(Utils.hasDaysPassed(10) && !Utils.hasDaysPassed(15)) {

            ShapedRecipe reviveCoreRecipe = new ShapedRecipe(new NamespacedKey("permadeath", "revive_core"),
                    ItemsList.REVIVE_CORE);
            reviveCoreRecipe.shape("DSD", "SWS", "DSD");
            reviveCoreRecipe.setIngredient('D', Material.DIAMOND);
            reviveCoreRecipe.setIngredient('S', Material.SHULKER_SHELL);
            reviveCoreRecipe.setIngredient('W', ItemsList.WARDEN_HEART);
            Bukkit.addRecipe(reviveCoreRecipe);

        } else if(Utils.hasDaysPassed(15)) {

            ShapedRecipe emperorReviveStoneRecipe = new ShapedRecipe(new NamespacedKey("permadeath", "emperor_revive_stone"),
                    ItemsList.EMPEROR_STONE);
            emperorReviveStoneRecipe.shape("NEN", "PGP", "NEN");
            emperorReviveStoneRecipe.setIngredient('N', ItemsList.DIAMONED_NETHER_STAR);
            emperorReviveStoneRecipe.setIngredient('E', ItemsList.EMPEROR_CRYSTAL);
            emperorReviveStoneRecipe.setIngredient('P', ItemsList.POWER_TOTEM);
            emperorReviveStoneRecipe.setIngredient('G', Material.NETHERITE_INGOT);
            Bukkit.addRecipe(emperorReviveStoneRecipe);

        }

        new DaysListener().runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        try {
            GameFileConfig.saveFile();
        } catch (IOException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Couldn't save the config file.", exception);
        }
    }
}
