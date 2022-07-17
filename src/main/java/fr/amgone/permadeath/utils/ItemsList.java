package fr.amgone.permadeath.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.UUID;

public class ItemsList {
    public static final ItemStack DIAMONED_NETHER_STAR;
    public static final ItemStack PERFECT_GOLD_NUGGET;
    public static final ItemStack RAVAGER_BONE;
    public static final ItemStack REVIVE_CORE;
    public static final ItemStack REVIVE_STONE;
    public static final ItemStack EMPEROR_STONE;
    public static final ItemStack CRYING_GHAST_TEAR;
    public static final ItemStack EMPEROR_CRYSTAL;
    public static final ItemStack WARDEN_HEART;
    public static final ItemStack POWER_TOTEM;
    public static final ItemStack MAGIC_CORE;
    public static final ItemStack MAGMATIC_CORE;
    public static final ItemStack PURE_INFUSED_NETHERITE_BOOTS;
    public static final ItemStack PURE_INFUSED_NETHERITE_LEGGINGS;
    public static final ItemStack PURE_INFUSED_NETHERITE_CHESTPLATE;
    public static final ItemStack PURE_INFUSED_NETHERITE_HELMET;
    public static final ItemStack ROUGH_INFUSED_NETHERITE_INGOT;
    public static final ItemStack PURE_INFUSED_NETHERITE_INGOT;
    public static final ItemStack ENDER_BOW;
    public static final ItemStack POWER_4_BOW;
    public static final ItemStack ARCHER_WITHER_SKELETON_BOW;
    public static final ItemStack ARCHER_SKELETON_BOW;
    public static final ItemStack WARRIOR_SKELETON_AXE;
    public static final ItemStack EMPEROR_SKELETON_BOW;
    public static final ItemStack EMPEROR_BANNER;
    public static final ItemStack AMETHYST_CRYSTAL;


    static {
        DIAMONED_NETHER_STAR = new ItemStack(Material.NETHER_STAR);
        ItemMeta diamonedNetherStarMeta = DIAMONED_NETHER_STAR.getItemMeta();
        diamonedNetherStarMeta.displayName(Component.text("Diamoned Nether Star").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        diamonedNetherStarMeta.setCustomModelData(4);
        DIAMONED_NETHER_STAR.setItemMeta(diamonedNetherStarMeta);

        PERFECT_GOLD_NUGGET = new ItemStack(Material.RAW_GOLD);
        ItemMeta perfectGoldNuggetMeta = PERFECT_GOLD_NUGGET.getItemMeta();
        perfectGoldNuggetMeta.displayName(Component.text("Perfect Gold Nugget").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        perfectGoldNuggetMeta.setCustomModelData(1);
        perfectGoldNuggetMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        perfectGoldNuggetMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        PERFECT_GOLD_NUGGET.setItemMeta(perfectGoldNuggetMeta);

        RAVAGER_BONE = new ItemStack(Material.BONE);
        ItemMeta ravagerBoneMeta = RAVAGER_BONE.getItemMeta();
        ravagerBoneMeta.displayName(Component.text("Ravager Bone").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false));
        ravagerBoneMeta.setCustomModelData(1);
        ravagerBoneMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        ravagerBoneMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        RAVAGER_BONE.setItemMeta(ravagerBoneMeta);

        REVIVE_CORE = new ItemStack(Material.NETHER_STAR);
        ItemMeta reviveCoreMeta = REVIVE_CORE.getItemMeta();
        reviveCoreMeta.displayName(Component.text("Revive Core").color(NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        reviveCoreMeta.setCustomModelData(1);
        REVIVE_CORE.setItemMeta(reviveCoreMeta);

        REVIVE_STONE = new ItemStack(Material.NETHER_STAR);
        ItemMeta reviveStoneMeta = REVIVE_STONE.getItemMeta();
        reviveStoneMeta.displayName(Component.text("Revive Stone").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        reviveStoneMeta.setCustomModelData(2);
        REVIVE_STONE.setItemMeta(reviveStoneMeta);

        EMPEROR_STONE = new ItemStack(Material.NETHER_STAR);
        ItemMeta emperorStoneMeta = EMPEROR_STONE.getItemMeta();
        emperorStoneMeta.displayName(Component.text("Emperor Stone").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        emperorStoneMeta.setCustomModelData(3);
        EMPEROR_STONE.setItemMeta(emperorStoneMeta);

        CRYING_GHAST_TEAR = new ItemStack(Material.GHAST_TEAR);
        ItemMeta cryingGhastTearMeta = CRYING_GHAST_TEAR.getItemMeta();
        cryingGhastTearMeta.displayName(Component.text("Crying Ghast Tear").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        cryingGhastTearMeta.setCustomModelData(1);
        cryingGhastTearMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        cryingGhastTearMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        CRYING_GHAST_TEAR.setItemMeta(cryingGhastTearMeta);

        EMPEROR_CRYSTAL = new ItemStack(Material.DIAMOND);
        ItemMeta emperorCrystalMeta = EMPEROR_CRYSTAL.getItemMeta();
        emperorCrystalMeta.displayName(Component.text("Emperor Crystal").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
        emperorCrystalMeta.setCustomModelData(1);
        emperorCrystalMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        emperorCrystalMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        EMPEROR_CRYSTAL.setItemMeta(emperorCrystalMeta);

        WARDEN_HEART = new ItemStack(Material.COAL);
        ItemMeta wardenHeartMeta = WARDEN_HEART.getItemMeta();
        wardenHeartMeta.displayName(Component.text("Warden Heart").color(NamedTextColor.DARK_AQUA).decoration(TextDecoration.ITALIC, false));
        wardenHeartMeta.setCustomModelData(1);
        WARDEN_HEART.setItemMeta(wardenHeartMeta);

        POWER_TOTEM = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta powerTotemMeta = POWER_TOTEM.getItemMeta();
        powerTotemMeta.displayName(Component.text("Power Totem").color(NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));
        powerTotemMeta.setCustomModelData(1);
        POWER_TOTEM.setItemMeta(powerTotemMeta);

        MAGIC_CORE = new ItemStack(Material.PURPLE_DYE);
        ItemMeta magicCoreMeta = MAGIC_CORE.getItemMeta();
        magicCoreMeta.displayName(Component.text("Magic Core").color(NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        magicCoreMeta.setCustomModelData(1);
        magicCoreMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        magicCoreMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        MAGIC_CORE.setItemMeta(magicCoreMeta);

        MAGMATIC_CORE = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta magmaticCoreMeta = MAGMATIC_CORE.getItemMeta();
        magmaticCoreMeta.displayName(Component.text("Magmatic Core").color(TextColor.color(255, 101, 18)).decoration(TextDecoration.ITALIC, false));
        magmaticCoreMeta.setCustomModelData(1);
        magmaticCoreMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        magmaticCoreMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        MAGMATIC_CORE.setItemMeta(magmaticCoreMeta);

        PURE_INFUSED_NETHERITE_BOOTS = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta pureInfusedNetheriteBootsMeta = (LeatherArmorMeta) PURE_INFUSED_NETHERITE_BOOTS.getItemMeta();
        pureInfusedNetheriteBootsMeta.displayName(Component.text("Pure Infused Netherite Boots").color(TextColor.color(255, 58, 18)).decoration(TextDecoration.ITALIC, false));
        pureInfusedNetheriteBootsMeta.setCustomModelData(1);
        pureInfusedNetheriteBootsMeta.setColor(Color.ORANGE);
        pureInfusedNetheriteBootsMeta.addItemFlags(ItemFlag.HIDE_DYE);

        AttributeModifier armorBootsModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        pureInfusedNetheriteBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorBootsModifier);

        AttributeModifier armorToughnessBootsModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        pureInfusedNetheriteBootsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughnessBootsModifier);

        AttributeModifier knockbackResistanceBootsModifier = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET);
        pureInfusedNetheriteBootsMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceBootsModifier);
        PURE_INFUSED_NETHERITE_BOOTS.setItemMeta(pureInfusedNetheriteBootsMeta);

        PURE_INFUSED_NETHERITE_LEGGINGS = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta pureInfusedNetheriteLeggingsMeta = (LeatherArmorMeta) PURE_INFUSED_NETHERITE_LEGGINGS.getItemMeta();
        pureInfusedNetheriteLeggingsMeta.displayName(Component.text("Pure Infused Netherite Leggings").color(TextColor.color(255, 58, 18)).decoration(TextDecoration.ITALIC, false));
        pureInfusedNetheriteLeggingsMeta.setCustomModelData(1);
        pureInfusedNetheriteLeggingsMeta.setColor(Color.ORANGE);
        pureInfusedNetheriteLeggingsMeta.addItemFlags(ItemFlag.HIDE_DYE);

        AttributeModifier armorLeggingsModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        pureInfusedNetheriteLeggingsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorLeggingsModifier);

        AttributeModifier armorToughnessLeggingsModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        pureInfusedNetheriteLeggingsMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughnessLeggingsModifier);

        AttributeModifier knockbackResistanceLeggingsModifier = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS);
        pureInfusedNetheriteLeggingsMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceLeggingsModifier);
        PURE_INFUSED_NETHERITE_LEGGINGS.setItemMeta(pureInfusedNetheriteLeggingsMeta);

        PURE_INFUSED_NETHERITE_CHESTPLATE = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta pureInfusedNetheriteChestplateMeta = (LeatherArmorMeta) PURE_INFUSED_NETHERITE_CHESTPLATE.getItemMeta();
        pureInfusedNetheriteChestplateMeta.displayName(Component.text("Pure Infused Netherite Chestplate").color(TextColor.color(255, 58, 18)).decoration(TextDecoration.ITALIC, false));
        pureInfusedNetheriteChestplateMeta.setCustomModelData(1);
        pureInfusedNetheriteChestplateMeta.setColor(Color.ORANGE);
        pureInfusedNetheriteChestplateMeta.addItemFlags(ItemFlag.HIDE_DYE);

        AttributeModifier armorChestplateModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        pureInfusedNetheriteChestplateMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorChestplateModifier);

        AttributeModifier armorToughnessChestplateModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        pureInfusedNetheriteChestplateMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughnessChestplateModifier);

        AttributeModifier knockbackResistanceChestplateModifier = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        pureInfusedNetheriteChestplateMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceChestplateModifier);
        PURE_INFUSED_NETHERITE_CHESTPLATE.setItemMeta(pureInfusedNetheriteChestplateMeta);

        PURE_INFUSED_NETHERITE_HELMET = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta pureInfusedNetheriteHelmetMeta = (LeatherArmorMeta) PURE_INFUSED_NETHERITE_HELMET.getItemMeta();
        pureInfusedNetheriteHelmetMeta.displayName(Component.text("Pure Infused Netherite Helmet").color(TextColor.color(255, 58, 18)).decoration(TextDecoration.ITALIC, false));
        pureInfusedNetheriteHelmetMeta.setCustomModelData(1);
        pureInfusedNetheriteHelmetMeta.setColor(Color.ORANGE);
        pureInfusedNetheriteHelmetMeta.addItemFlags(ItemFlag.HIDE_DYE);

        AttributeModifier armorHelmetModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        pureInfusedNetheriteHelmetMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorHelmetModifier);

        AttributeModifier armorToughnessHelmetModifier = new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        pureInfusedNetheriteHelmetMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughnessHelmetModifier);

        AttributeModifier knockbackResistanceHelmetModifier = new AttributeModifier(UUID.randomUUID(), "generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        pureInfusedNetheriteHelmetMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceHelmetModifier);
        PURE_INFUSED_NETHERITE_HELMET.setItemMeta(pureInfusedNetheriteHelmetMeta);

        ROUGH_INFUSED_NETHERITE_INGOT = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta roughInfusedNetheriteIngotMeta = ROUGH_INFUSED_NETHERITE_INGOT.getItemMeta();
        roughInfusedNetheriteIngotMeta.displayName(Component.text("Rough Infused Netherite Ingot").color(TextColor.color(176, 12, 12)).decoration(TextDecoration.ITALIC, false));
        roughInfusedNetheriteIngotMeta.setCustomModelData(1);
        ROUGH_INFUSED_NETHERITE_INGOT.setItemMeta(roughInfusedNetheriteIngotMeta);

        PURE_INFUSED_NETHERITE_INGOT = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta pureInfusedNetheriteIngotMeta = PURE_INFUSED_NETHERITE_INGOT.getItemMeta();
        pureInfusedNetheriteIngotMeta.displayName(Component.text("Pure Infused Netherite Ingot").color(TextColor.color(250, 49, 22)).decoration(TextDecoration.ITALIC, false));
        pureInfusedNetheriteIngotMeta.setCustomModelData(2);
        PURE_INFUSED_NETHERITE_INGOT.setItemMeta(pureInfusedNetheriteIngotMeta);

        ENDER_BOW = new ItemStack(Material.BOW);
        ItemMeta enderBowMeta = ENDER_BOW.getItemMeta();
        enderBowMeta.displayName(Component.text("Ender Bow").color(NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false));
        enderBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 10, true);
        ENDER_BOW.setItemMeta(enderBowMeta);

        POWER_4_BOW = new ItemStack(Material.BOW);
        ItemMeta power4BowMeta = POWER_4_BOW.getItemMeta();
        power4BowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 4, false);
        POWER_4_BOW.setItemMeta(power4BowMeta);

        ARCHER_WITHER_SKELETON_BOW = new ItemStack(Material.BOW);
        ItemMeta archerWitherSkeletonBowMeta = ARCHER_WITHER_SKELETON_BOW.getItemMeta();
        archerWitherSkeletonBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 25, true);
        archerWitherSkeletonBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 5, true);
        ARCHER_WITHER_SKELETON_BOW.setItemMeta(archerWitherSkeletonBowMeta);

        ARCHER_SKELETON_BOW = new ItemStack(Material.BOW);
        ItemMeta archerSkeletonBowMeta = ARCHER_SKELETON_BOW.getItemMeta();
        archerSkeletonBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 20, true);
        archerSkeletonBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);
        archerSkeletonBowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        ARCHER_SKELETON_BOW.setItemMeta(archerSkeletonBowMeta);

        WARRIOR_SKELETON_AXE = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta warriorSkeletonAxeMeta = WARRIOR_SKELETON_AXE.getItemMeta();
        warriorSkeletonAxeMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
        warriorSkeletonAxeMeta.addEnchant(Enchantment.FIRE_ASPECT, 10, true);
        WARRIOR_SKELETON_AXE.setItemMeta(warriorSkeletonAxeMeta);

        EMPEROR_SKELETON_BOW = new ItemStack(Material.BOW);
        ItemMeta emperorSkeletonBowMeta = EMPEROR_SKELETON_BOW.getItemMeta();
        emperorSkeletonBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 50, true);
        emperorSkeletonBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 5, true);
        EMPEROR_SKELETON_BOW.setItemMeta(emperorSkeletonBowMeta);

        EMPEROR_BANNER = new ItemStack(Material.BLACK_BANNER);
        BannerMeta emperorBannerMeta = (BannerMeta) EMPEROR_BANNER.getItemMeta();
        emperorBannerMeta.addPattern(new Pattern(DyeColor.RED, PatternType.MOJANG));
        emperorBannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.BRICKS));
        emperorBannerMeta.addPattern(new Pattern(DyeColor.ORANGE, PatternType.TRIANGLE_BOTTOM));
        emperorBannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE));
        emperorBannerMeta.addPattern(new Pattern(DyeColor.RED, PatternType.GRADIENT_UP));
        emperorBannerMeta.addPattern(new Pattern(DyeColor.ORANGE, PatternType.TRIANGLES_BOTTOM));
        EMPEROR_BANNER.setItemMeta(emperorBannerMeta);

        AMETHYST_CRYSTAL = new ItemStack(Material.EMERALD);
        ItemMeta amethystCrystalMeta = AMETHYST_CRYSTAL.getItemMeta();
        amethystCrystalMeta.displayName(Component.text("Amethyst Crystal").color(TextColor.color(NamedTextColor.DARK_AQUA)).decoration(TextDecoration.ITALIC, false));
        amethystCrystalMeta.setCustomModelData(1);
        AMETHYST_CRYSTAL.setItemMeta(amethystCrystalMeta);
    }
}
