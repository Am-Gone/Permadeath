package fr.amgone.permadeath.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Wither extends WitherBoss {
    private final ServerLevel serverLevel;
    private int nextHealthStunPercentage = 80;
    private int stunWait = -1;

    public Wither(World world) {
        super(EntityType.WITHER, ((CraftWorld) world).getHandle());
        this.serverLevel = ((CraftWorld) world).getHandle();

        getBukkitEntity().getPersistentDataContainer().set(new NamespacedKey("permadeath", "custom"), PersistentDataType.BYTE, (byte) 1);
        makeInvulnerable();

        getAttribute(Attributes.MAX_HEALTH).setBaseValue(getMaxHealth() * 2);
        setHealth(getMaxHealth());
    }

    @Override
    public void tick() {
        super.tick();

        if(getInvulnerableTicks() > 0) {
            return;
        }

        float healthPercentageLeft = getHealth() * 100 / getMaxHealth();
        if(healthPercentageLeft <= nextHealthStunPercentage) {
            nextHealthStunPercentage -= 20;
            if(nextHealthStunPercentage == 0) nextHealthStunPercentage = -20; // We don't want to trigger a stun if the wither has 0 health.

            stunWait = 20 * 3;
            setNoAi(true);
            setInvulnerable(true);

            Location witherLocation = new Location(serverLevel.getWorld(), getX(), getY(), getZ());
            serverLevel.getWorld().strikeLightning(witherLocation);
            serverLevel.getWorld().createExplosion(getBukkitCreature(), witherLocation, 3, false, true);
        }

        if(stunWait > 0) {
            stunWait--;
        } else if(stunWait == 0) {
            stunWait--;
            setNoAi(false);
            setInvulnerable(false);

            for(Entity entities : getBukkitEntity().getNearbyEntities(7, 7, 7)) {
                if(entities instanceof Player player) {
                    player.getWorld().strikeLightning(player.getLocation());
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5 * 20, 2));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 10 * 20, 0));
                    player.damage(4, getBukkitMonster());
                    // TODO play anvil sound
                }
            }
        }
    }
}
