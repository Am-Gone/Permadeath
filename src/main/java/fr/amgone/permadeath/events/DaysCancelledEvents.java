package fr.amgone.permadeath.events;

import fr.amgone.permadeath.Main;
import fr.amgone.permadeath.utils.ItemsList;
import fr.amgone.permadeath.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class DaysCancelledEvents implements Listener {
    @EventHandler
    public void onEndPortalEnter(PlayerTeleportEvent event) {
        if(!Utils.hasDaysPassed(14) && event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onUndyingTotemConsumed(EntityResurrectEvent event) {
        if(!event.isCancelled() && event instanceof Player player) {
            Bukkit.broadcast(player.displayName().append(Component.text(" a utilisé un totem!")));
        }
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if(event.getEntity() instanceof Mob mob) {
            mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getValue() * 2);
        }

        if(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.RAID && !Utils.hasDaysPassed(7)) {
            if (livingEntity instanceof Ravager || livingEntity instanceof Pillager || livingEntity instanceof Vindicator) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 0));
            } else if (livingEntity instanceof Witch && !Utils.hasDaysPassed(7)) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 1));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0));
            }
        }

        if(livingEntity instanceof Witch) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for(Entity entities : livingEntity.getNearbyEntities(7, 7, 7)) {
                        if(entities instanceof Monster monster) {
                            monster.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 3));
                            // TODO spawn particles
                        }
                    }
                }
            }.runTaskTimer(Main.PLUGIN, 0, 20);

            if(Utils.hasDaysPassed(7) && !Utils.hasDaysPassed(21)) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 2));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 1));
            } else if(Utils.hasDaysPassed(21)) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 3));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 1));
            }
        }

        if(livingEntity instanceof Piglin || livingEntity instanceof Hoglin && !Utils.hasDaysPassed(14)) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 0));
        }

        if(livingEntity instanceof Wither && !livingEntity.getPersistentDataContainer().has(new NamespacedKey("permadeath", "custom"))) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    fr.amgone.permadeath.entities.Wither wither = new fr.amgone.permadeath.entities.Wither(livingEntity.getWorld());
                    Location location = livingEntity.getLocation();
                    ((CraftWorld) livingEntity.getWorld()).getHandle().addFreshEntity(wither);
                    wither.setPos(location.getX(), location.getY(), location.getZ());

                    livingEntity.remove();
                }
            }.runTaskLater(Main.PLUGIN, 1);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.ANCIENT_DEBRIS) {
            event.setDropItems(ThreadLocalRandom.current().nextBoolean());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        int daysPassed = Utils.getDaysPassed();
        if(daysPassed == 0) daysPassed = 1;

        World world = Bukkit.getWorld("world");
        if(world == null) return;
        world.setThunderDuration(world.getThunderDuration() + daysPassed * 60 * 60 * 20);
        world.setStorm(true);
        world.setThundering(true);
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if(event.getRecipe() == null) return;

        Material recipeResult = event.getRecipe().getResult().getType();
        if(!Utils.hasDaysPassed(7) && recipeResult == Material.NETHERITE_HELMET || recipeResult == Material.NETHERITE_CHESTPLATE
                || recipeResult == Material.NETHERITE_LEGGINGS || recipeResult == Material.NETHERITE_BOOTS) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof Hoglin && event.getEntity().getKiller() != null) {
            if(ThreadLocalRandom.current().nextInt(0, 100) <= 10) {
                event.getDrops().add(ItemsList.PERFECT_GOLD_NUGGET);
            }
        }
    }
}
