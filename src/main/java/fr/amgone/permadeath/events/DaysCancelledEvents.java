package fr.amgone.permadeath.events;

import fr.amgone.permadeath.Main;
import fr.amgone.permadeath.utils.ItemsList;
import fr.amgone.permadeath.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class DaysCancelledEvents implements Listener {
    @EventHandler
    public void onEndPortalEnter(PlayerTeleportEvent event) {
        if (!Utils.hasDaysPassed(14) && event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onUndyingTotemConsumed(EntityResurrectEvent event) {
        if (event.getEntity() instanceof Player player) {
            Bukkit.broadcast(player.displayName().append(Component.text(" a utilisé un Totem of Undying!")).color(TextColor.color(NamedTextColor.GRAY)));
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Fireball && Utils.hasDaysPassed(7) && !Utils.hasDaysPassed(28)) {
            ((Fireball) entity).setYield(5.0F);
        } else if (entity instanceof Fireball && Utils.hasDaysPassed(28)) {
            ((Fireball) entity).setYield(6.0F);
        }
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (livingEntity instanceof Mob mob) {
            mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getValue() * 2);
        }

        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.RAID && !Utils.hasDaysPassed(7)) {
            if (livingEntity instanceof Ravager || livingEntity instanceof Pillager || livingEntity instanceof Vindicator || livingEntity instanceof Vex) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 0, false, false));
            } else if (livingEntity instanceof Witch) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 1, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
            }
        } else if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.RAID && Utils.hasDaysPassed(7)) {
            if (livingEntity instanceof Ravager || livingEntity instanceof Pillager || livingEntity instanceof Vindicator || livingEntity instanceof Vex) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 1, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 1, false, false));
            }
        }



        if (livingEntity instanceof Witch && !Utils.hasDaysPassed(28)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Entity entities : livingEntity.getNearbyEntities(7, 7, 7)) {
                        if (entities instanceof Monster monster && !(entities instanceof Witch)) {
                            monster.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 2, false, false));
                            monster.getWorld().spawnParticle(Particle.SPELL_WITCH, monster.getLocation().clone().add(0.0D, 1.0D, 0.0D), 13, 0.35, 0.7, 0.35, 0);
                            monster.getWorld().playSound(monster.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 0.7F, 1);
                        }

                        if (entities instanceof Player player && Utils.hasDaysPassed(21) && entities.getWorld().getEnvironment() == World.Environment.THE_END) {
                            player.spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 30, 1.2, 1.5, 1.2, 0.25);
                            player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_HURT, 1.6F, 0);

                            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (15 * 20), 2, false, false));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, (4 * 20), 0, false, false));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (10 * 20), 1, false, false));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, (10 * 20), 0, false, false));
                        }
                    }
                }
            }.runTaskTimer(Main.PLUGIN, 0, 20); //TODO L'effet de zone des withes ne disparait pas quand la witch est tuée

            if (Utils.hasDaysPassed(7) && !Utils.hasDaysPassed(21)) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 2, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 1, false, false));
            } else if (Utils.hasDaysPassed(21)) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 3, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 1, false, false));
            }
        }



        if (livingEntity instanceof Hoglin || livingEntity instanceof Piglin) {
            if (!Utils.hasDaysPassed(14)) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 0, false, false));
            } else {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 1, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 1, false, false));
            }

        }

        if (livingEntity instanceof MagmaCube && Utils.hasDaysPassed(14)) {
            if (livingEntity.customName() == null) {
                if (ThreadLocalRandom.current().nextInt(0, 100) <= 2) {
                    MagmaCube gigaMagmaCube = (MagmaCube) livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), EntityType.MAGMA_CUBE);

                    gigaMagmaCube.setSize(16);
                    gigaMagmaCube.customName(Component.text("Giga Magma Cube").color(TextColor.color(NamedTextColor.GOLD)));
                    gigaMagmaCube.setCustomNameVisible(true);
                    gigaMagmaCube.setPersistent(true);

                    gigaMagmaCube.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(35 * 2);
                    gigaMagmaCube.setHealth(gigaMagmaCube.getMaxHealth());

                    gigaMagmaCube.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(30);

                    if (!gigaMagmaCube.getEyeLocation().getBlock().getType().isAir()) {
                        gigaMagmaCube.remove();
                    }
                }
            } else {
                livingEntity.setCustomNameVisible(true);
            }
        }

        if (livingEntity instanceof Phantom && Utils.hasDaysPassed(14)) {
            ((Phantom) livingEntity).setSize(2);
        }

        if (livingEntity instanceof Zombie && Utils.hasDaysPassed(14) && !Utils.hasDaysPassed(21)) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 1, false, false));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 1, false, false));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 1, false, false));

            livingEntity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));

        } else if (livingEntity instanceof Zombie && Utils.hasDaysPassed(21)) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0, false, false));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 1, false, false));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 2, false, false));

            livingEntity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            livingEntity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            livingEntity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            livingEntity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            livingEntity.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
        }

        if (livingEntity instanceof Creeper) {
            if (Utils.hasDaysPassed(14)) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0, false, false));

                if (Utils.hasDaysPassed(21)) {
                    ((Creeper) livingEntity).setPowered(true);
                }
            }
        }

        if (livingEntity instanceof PigZombie) {
            if (Utils.hasDaysPassed(7)) {
                ((PigZombie) livingEntity).setAngry(true);
                if (Utils.hasDaysPassed(21)) {
                    livingEntity.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                    livingEntity.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    livingEntity.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    livingEntity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 1, false, false));
                }
            }
        }

        if (livingEntity instanceof Warden) {
            if (Utils.hasDaysPassed(14)) {
                livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200 * 2);
                livingEntity.setHealth(livingEntity.getMaxHealth());
            }
        } //TODO Show warden bossbar to nearby players

        if (livingEntity instanceof Skeleton) {
            if (Utils.hasDaysPassed(14) && !Utils.hasDaysPassed(21)) {
                livingEntity.getEquipment().setItemInMainHand(ItemsList.POWER_4_BOW);
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0, false, false));
            } else if (Utils.hasDaysPassed(21)) {
                if (ThreadLocalRandom.current().nextInt(0, 100) <= 50) {
                    livingEntity.getEquipment().setItemInMainHand(ItemsList.ARCHER_SKELETON_BOW);

                    livingEntity.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                    livingEntity.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                    livingEntity.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                    livingEntity.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));

                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 2, false, false));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 1, false, false));
                } else {
                    livingEntity.getEquipment().setItemInMainHand(ItemsList.WARRIOR_SKELETON_AXE);

                    livingEntity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                    livingEntity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                    livingEntity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    livingEntity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));

                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0, false, false));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 1, false, false));
                }

                livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20 * 2);
                livingEntity.setHealth(livingEntity.getMaxHealth());

            }

        }

        if (livingEntity instanceof WitherSkeleton && Utils.hasDaysPassed(21)) {

            int randomClass = ThreadLocalRandom.current().nextInt(0, 50);

            if (randomClass <= 25) {

                // Warrior Wither Skeleton

                livingEntity.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));

                livingEntity.getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
                livingEntity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                livingEntity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                livingEntity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));

                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 1, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 1, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));

                livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30 * 2);
                livingEntity.setHealth(livingEntity.getMaxHealth());
            } else if (randomClass > 27) {

                // Archer Wither Skeleton

                livingEntity.getEquipment().setItemInMainHand(ItemsList.ARCHER_WITHER_SKELETON_BOW);

                livingEntity.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                livingEntity.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                livingEntity.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                livingEntity.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));

                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 1, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));

                livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20 * 2);
                livingEntity.setHealth(livingEntity.getMaxHealth());

            } else if (randomClass == 26) {

                // Emperor Wither Skeleton

                livingEntity.getEquipment().setItemInMainHand(ItemsList.EMPEROR_SKELETON_BOW);

                livingEntity.getEquipment().setHelmet(ItemsList.EMPEROR_BANNER);
                livingEntity.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
                livingEntity.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
                livingEntity.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));

                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 1, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));

                livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40 * 2);
                livingEntity.setHealth(livingEntity.getMaxHealth());

                livingEntity.customName(Component.text("Emperor Wither Skeleton").color(TextColor.color(NamedTextColor.GOLD)));
                livingEntity.setCustomNameVisible(true);
            }
        }

        if (livingEntity instanceof Ghast) {
            if (Utils.hasDaysPassed(7) && !Utils.hasDaysPassed(28)) {
                livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30 * 2);
                livingEntity.setHealth(livingEntity.getMaxHealth());
            } else if (Utils.hasDaysPassed(28)) {
                livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(50 * 2);
                livingEntity.setHealth(livingEntity.getMaxHealth());
            }
        }

        if (livingEntity instanceof Wither && !livingEntity.getPersistentDataContainer().has(new NamespacedKey("permadeath", "custom"))) {
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

        if (livingEntity instanceof Zombie || livingEntity instanceof Skeleton || livingEntity instanceof Creeper) {
            if (livingEntity.getEyeLocation().clone().add(0.0D, 2.0D, 0.0D).getBlock().getType().equals(Material.AIR) && livingEntity.getEyeLocation().getBlockY() < 0) {
                if (livingEntity.getWorld().getEnvironment() == World.Environment.NORMAL) {
                    if (ThreadLocalRandom.current().nextInt(0, 14) == 1) {

                        IronGolem amethystGolem = (IronGolem) livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), EntityType.IRON_GOLEM);
                        livingEntity.remove();

                        amethystGolem.customName(Component.text("Amethyst Golem").color(TextColor.color(NamedTextColor.LIGHT_PURPLE)));
                        amethystGolem.setCustomNameVisible(true);

                    }
                }
            }
        }

        if (livingEntity instanceof Enderman) {

            if (ThreadLocalRandom.current().nextInt(0, 12) == 1 && livingEntity.getWorld().getEnvironment() == World.Environment.THE_END) {
                Ghast enderGhast = (Ghast) livingEntity.getWorld().spawnEntity(livingEntity.getLocation().clone().add(0.0D, 30.0D, 0.0D), EntityType.GHAST);

                enderGhast.customName(Component.text("Ender Ghast").color(TextColor.color(NamedTextColor.LIGHT_PURPLE)));
                enderGhast.setCustomNameVisible(true);

                if (!enderGhast.getEyeLocation().getBlock().getType().isAir()) {
                    enderGhast.remove();
                }

            }
            if (Utils.hasDaysPassed(28) && ThreadLocalRandom.current().nextInt(0, 14) == 1 && livingEntity.getWorld().getEnvironment() == World.Environment.THE_END) {
                IronGolem amethystGolem = (IronGolem) livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), EntityType.IRON_GOLEM);
                livingEntity.remove();

                amethystGolem.customName(Component.text("Amethyst Golem").color(TextColor.color(NamedTextColor.LIGHT_PURPLE)));
                amethystGolem.setCustomNameVisible(true);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Entity entities : amethystGolem.getNearbyEntities(32, 32, 32)) {
                            if (entities instanceof Player player) {
                                amethystGolem.setTarget(player);
                            }
                        }
                    }
                }.runTaskTimer(Main.PLUGIN, 0, 60);
            }

            if (ThreadLocalRandom.current().nextInt(0, 6) == 1 && livingEntity.getWorld().getEnvironment() == World.Environment.THE_END) {
                Vindicator enderVindicator = (Vindicator) livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), EntityType.VINDICATOR);
                livingEntity.remove();

                enderVindicator.customName(Component.text("Ender Vindicator").color(TextColor.color(NamedTextColor.LIGHT_PURPLE)));
                enderVindicator.setCustomNameVisible(true);
                enderVindicator.setPatrolLeader(false);
                enderVindicator.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20 * 2);
                enderVindicator.setHealth(enderVindicator.getMaxHealth());
                enderVindicator.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 0, false, false));
                enderVindicator.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));

                if (!Utils.hasDaysPassed(28)) {
                    ItemStack ironAxe = new ItemStack(Material.IRON_AXE);
                    ItemMeta ironAxeMeta = ironAxe.getItemMeta();
                    ironAxeMeta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
                    ironAxe.setItemMeta(ironAxeMeta);

                    enderVindicator.getEquipment().setItemInMainHand(ironAxe);
                } else {
                    ItemStack diamondAxe = new ItemStack(Material.DIAMOND_AXE);
                    ItemMeta diamondAxeMeta = diamondAxe.getItemMeta();
                    diamondAxeMeta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
                    diamondAxe.setItemMeta(diamondAxeMeta);

                    enderVindicator.getEquipment().setItemInMainHand(diamondAxe);
                }
            }

            if (Utils.hasDaysPassed(21) && !Utils.hasDaysPassed(28)) {
                if (ThreadLocalRandom.current().nextInt(0, 12) == 1 && livingEntity.getWorld().getEnvironment() == World.Environment.THE_END) {
                    Witch enderWitch = (Witch) livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), EntityType.WITCH);
                    livingEntity.remove();

                    enderWitch.customName(Component.text("Ender Witch").color(TextColor.color(NamedTextColor.DARK_PURPLE)));
                    enderWitch.setCustomNameVisible(true);

                }
            }

            if (Utils.hasDaysPassed(14) && !Utils.hasDaysPassed(28)) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 1, false, false));
            } else if (Utils.hasDaysPassed(28)) {
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 1, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 2, false, false));
            }
        }

        if (livingEntity instanceof IronGolem ironGolem && ironGolem.customName() != null) {
            if (ironGolem.customName().equals(Component.text("Amethyst Golem").color(TextColor.color(NamedTextColor.LIGHT_PURPLE)))) {
                ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000000, 0, false, false));

                if (!Utils.hasDaysPassed(7)) {
                    ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 0, false, false));
                    ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 6000000, 0, false, false));
                } else {
                    if(Utils.hasDaysPassed(7) && !Utils.hasDaysPassed(28)) {
                        ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 2, false, false));
                        ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                        ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 0, false, false));
                        ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 6000000, 0, false, false));
                    } else {
                        ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000000, 2, false, false));
                        ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000000, 0, false, false));
                        ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000000, 1, false, false));
                        ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 6000000, 0, false, false));
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Entity entities : ironGolem.getNearbyEntities(9, 9, 9)) {
                                if (entities instanceof Player player && ironGolem.getHealth() <= (ironGolem.getMaxHealth() / 2)) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 7 * 20, 0, false, false));
                                }
                            }
                        }
                    }.runTaskTimer(Main.PLUGIN, 0, 40); //TODO l'effet ne disparait pas même si le golem est mort
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Entity entities : ironGolem.getNearbyEntities(32, 32, 32)) {
                            if (entities instanceof Player player) {
                                ironGolem.setTarget(player);
                            }
                        }
                    }
                }.runTaskTimer(Main.PLUGIN, 0, 200);
            }
        }

        if (livingEntity instanceof EnderDragon) {
            if (!Utils.hasDaysPassed(28)) {
                livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(250 * 2);
            } else {
                livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(500 * 2);
            }

            livingEntity.setHealth(livingEntity.getMaxHealth());
            livingEntity.customName(Component.text("Ender Dragon").color(TextColor.color(173, 3, 252)).decoration(TextDecoration.BOLD, true));
            livingEntity.setCustomNameVisible(true);
        }

        if (livingEntity.getEquipment() != null) {
            livingEntity.getEquipment().setHelmetDropChance(0.0F);
            livingEntity.getEquipment().setChestplateDropChance(0.0F);
            livingEntity.getEquipment().setLeggingsDropChance(0.0F);
            livingEntity.getEquipment().setBootsDropChance(0.0F);
            livingEntity.getEquipment().setItemInMainHandDropChance(0.0F);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        if (event.getEntity().getType() == EntityType.CREEPER) {
            LivingEntity entity = (LivingEntity) event.getEntity();

            entity.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            entity.removePotionEffect(PotionEffectType.SPEED);
            entity.removePotionEffect(PotionEffectType.INVISIBILITY);
        }

        if (event.getEntity() instanceof EnderCrystal) {
            Ghast crystalGhast = (Ghast) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.GHAST);

            crystalGhast.customName(Component.text("Ender Ghast").color(TextColor.color(NamedTextColor.LIGHT_PURPLE)));
            crystalGhast.setCustomNameVisible(true);
        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.ANCIENT_DEBRIS) {
            event.setDropItems(ThreadLocalRandom.current().nextBoolean());
        }

        if (event.getBlock().getType() == Material.SCULK || event.getBlock().getType() == Material.SCULK_CATALYST) {
            if (Utils.hasDaysPassed(14)) {
                event.setDropItems(false);
                event.setExpToDrop(0);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        int daysPassed = Utils.getDaysPassed();
        if (daysPassed == 0) daysPassed = 1;

        World world = Bukkit.getWorld("world");
        if (world == null) return;
        world.setThunderDuration(world.getThunderDuration() + daysPassed * 60 * 60 * 20);
        world.setStorm(true);
        world.setThundering(true);
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if (event.getRecipe() == null) return;

        Material recipeResult = event.getRecipe().getResult().getType();
        if (!Utils.hasDaysPassed(7) && recipeResult == Material.NETHERITE_HELMET || recipeResult == Material.NETHERITE_CHESTPLATE
                || recipeResult == Material.NETHERITE_LEGGINGS || recipeResult == Material.NETHERITE_BOOTS) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }

        if (recipeResult == Material.END_CRYSTAL) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onSmithingCraft(PrepareSmithingEvent event) {
        if (Utils.hasDaysPassed(14)) {
            if (event.getInventory().getInputEquipment() != null && event.getInventory().getInputMineral() != null) {
                if (event.getInventory().getInputEquipment().getType() == Material.NETHERITE_BOOTS && event.getInventory().getInputMineral().isSimilar(ItemsList.PURE_INFUSED_NETHERITE_INGOT)) {
                    event.setResult(ItemsList.PURE_INFUSED_NETHERITE_BOOTS);
                }
                if (event.getInventory().getInputEquipment().getType() == Material.NETHERITE_LEGGINGS && event.getInventory().getInputMineral().isSimilar(ItemsList.PURE_INFUSED_NETHERITE_INGOT)) {
                    event.setResult(ItemsList.PURE_INFUSED_NETHERITE_LEGGINGS);
                }
                if (event.getInventory().getInputEquipment().getType() == Material.NETHERITE_CHESTPLATE && event.getInventory().getInputMineral().isSimilar(ItemsList.PURE_INFUSED_NETHERITE_INGOT)) {
                    event.setResult(ItemsList.PURE_INFUSED_NETHERITE_CHESTPLATE);
                }
                if (event.getInventory().getInputEquipment().getType() == Material.NETHERITE_HELMET && event.getInventory().getInputMineral().isSimilar(ItemsList.PURE_INFUSED_NETHERITE_INGOT)) {
                    event.setResult(ItemsList.PURE_INFUSED_NETHERITE_HELMET);
                } //TODO on ne peux pas récupérer le result jsp pk
            }
        }
    }

    @EventHandler
    public void onAnvilCraft(PrepareAnvilEvent event) {
        if (Utils.hasDaysPassed(14)) {
            if (event.getInventory().getFirstItem() != null && event.getInventory().getSecondItem() != null) {
                if (event.getInventory().getFirstItem().isSimilar(ItemsList.ROUGH_INFUSED_NETHERITE_INGOT) && event.getInventory().getSecondItem().isSimilar(ItemsList.MAGMATIC_CORE)) {
                    event.setResult(ItemsList.PURE_INFUSED_NETHERITE_INGOT);
                }
                if (event.getInventory().getFirstItem().isSimilar(ItemsList.MAGMATIC_CORE) && event.getInventory().getSecondItem().isSimilar(ItemsList.ROUGH_INFUSED_NETHERITE_INGOT)) {
                    event.setResult(ItemsList.PURE_INFUSED_NETHERITE_INGOT);
                } //TODO on ne peux pas récupérer le result jsp pk
            }
        }
    }

    @EventHandler
    public void OnBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getEnvironment() == World.Environment.NORMAL && Utils.hasDaysPassed(7)) {
            event.setCancelled(true);
            player.sendMessage(Component.text("Dormir est impossible.").color(TextColor.color(NamedTextColor.RED)));
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Hoglin && event.getEntity().getKiller() != null) {
            if (ThreadLocalRandom.current().nextInt(0, 100) <= 10) {
                event.getDrops().add(ItemsList.PERFECT_GOLD_NUGGET);
            }
        }

        if (event.getEntity() instanceof Ravager && event.getEntity().getKiller() != null && Utils.hasDaysPassed(7) && !Utils.hasDaysPassed(14)) {
            if (ThreadLocalRandom.current().nextInt(0, 100) <= 40) {
                event.getDrops().add(ItemsList.RAVAGER_BONE);
            }
        }

        if (event.getEntity() instanceof Witch && event.getEntity().getKiller() != null && event.getEntity().getWorld().getEnvironment() == World.Environment.THE_END && Utils.hasDaysPassed(21) && !Utils.hasDaysPassed(28)) {
            if (ThreadLocalRandom.current().nextInt(0, 100) <= 4) {
                event.getDrops().add(ItemsList.MAGIC_CORE);
            }
        }

        if (event.getEntity() instanceof Ghast && event.getEntity().getKiller() != null && Utils.hasDaysPassed(7)) {
            event.getDrops().add(ItemsList.CRYING_GHAST_TEAR);
        }

        if (event.getEntity() instanceof Warden && event.getEntity().getKiller() != null && Utils.hasDaysPassed(14)) {
            event.getDrops().add(ItemsList.WARDEN_HEART);
        }

        if(event.getEntity().getKiller() != null) {
            if(event.getEntity().getKiller().equals(EntityType.IRON_GOLEM)) {
                event.getDrops().clear();
                event.setDroppedExp(0);
            }
        }

        if(event.getEntity().getKiller() != null && event.getEntity() instanceof EnderCrystal) {
            if(event.getEntity().getKiller().equals(EntityType.GHAST)) {
                event.setCancelled(true);
            }
        }

        if (event.getEntity() instanceof IronGolem && event.getEntity().customName() != null) {
            if (event.getEntity().customName().equals(Component.text("Amethyst Golem").color(TextColor.color(NamedTextColor.LIGHT_PURPLE)))) {
                event.getDrops().clear();

                if (ThreadLocalRandom.current().nextInt(0, 100) <= 2 && !Utils.hasDaysPassed(7) && event.getEntity().getKiller() != null) {
                    event.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING));
                } else if (ThreadLocalRandom.current().nextInt(0, 100) <= 7 && Utils.hasDaysPassed(7) && event.getEntity().getKiller() != null) {
                    event.getDrops().add(ItemsList.AMETHYST_CRYSTAL);
                }
            }
        }

        if (event.getEntity() instanceof Wither && event.getEntity().getKiller() != null) {
            if (Utils.hasDaysPassed(21)) {
                event.getDrops().add(ItemsList.POWER_TOTEM);
                event.getDrops().add(ItemsList.POWER_TOTEM);
            }

            event.getEntity().getWorld().spawnParticle(Particle.SMOKE_LARGE, event.getEntity().getLocation(), 150, 0.25, 0.25, 0.25, 0.25);
            event.getEntity().getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, event.getEntity().getLocation(), 150, 0.25, 0.25, 0.25, 0.25);
            event.getEntity().getWorld().spawnParticle(Particle.SCULK_SOUL, event.getEntity().getLocation(), 150, 0.25, 0.25, 0.25, 0.25);

        }

        if (event.getEntity() instanceof WitherSkeleton && event.getEntity().getKiller() != null && event.getEntity().customName() != null && Utils.hasDaysPassed(21)) {
            if (event.getEntity().customName().equals(Component.text("Emperor Wither Skeleton").color(TextColor.color(NamedTextColor.GOLD)))) {
                if (ThreadLocalRandom.current().nextInt(0, 100) <= 50) {
                    event.getDrops().add(ItemsList.EMPEROR_CRYSTAL);
                }
            }
        }

        if (event.getEntity() instanceof MagmaCube && event.getEntity().getKiller() != null && event.getEntity().customName() != null && Utils.hasDaysPassed(14)) {
            if (event.getEntity().customName().equals(Component.text("Giga Magma Cube").color(TextColor.color(NamedTextColor.GOLD)))) {
                if (ThreadLocalRandom.current().nextInt(0, 100) <= 15) {
                    event.getDrops().add(ItemsList.MAGMATIC_CORE);
                }
            }
        }

        if (event.getEntity() instanceof Vindicator && event.getEntity().getKiller() != null && event.getEntity().customName() != null && Utils.hasDaysPassed(14) && !Utils.hasDaysPassed(28)) {
            if (event.getEntity().customName().equals(Component.text("Ender Vindicator").color(TextColor.color(NamedTextColor.LIGHT_PURPLE)))) {
                if (ThreadLocalRandom.current().nextInt(0, 100) <= 5) {
                    event.getDrops().add(ItemsList.ENDER_BOW);
                }
            }
        }

        if (event.getEntity() instanceof Evoker && Utils.hasDaysPassed(7)) {
            event.getDrops().clear();
        }

        if (event.getEntity() instanceof Enderman || event.getEntity() instanceof Guardian || event.getEntity() instanceof PigZombie) {
            if (Utils.hasDaysPassed(14)) {
                event.getDrops().clear();
            }
        }

        if (event.getEntity() instanceof PiglinBrute && event.getEntity().getKiller() != null && Utils.hasDaysPassed(14)) {
            if (ThreadLocalRandom.current().nextInt(0, 100) <= 50) {
                event.getDrops().add(ItemsList.ROUGH_INFUSED_NETHERITE_INGOT);
            }
        }
    }
}
