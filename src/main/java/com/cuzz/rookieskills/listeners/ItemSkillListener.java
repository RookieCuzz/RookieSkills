package com.cuzz.rookieskills.listeners;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.api.ItemService;
import com.cuzz.rookieskills.bean.TriggerType;
import com.cuzz.rookieskills.bean.skill.skilldata.impl.ItemSkillData;
import com.cuzz.rookieskills.bean.skill.skillimp.ItemSkillImp;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemSkillListener implements Listener {

    private static Player player;

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        new BukkitRunnable() {
            public void run() {
                player = event.getPlayer();

                // 头盔
                if (player.getInventory().getHelmet() != null) {
                    ItemStack helmetItem = player.getInventory().getHelmet();

                    castItemSkillByTriggerType(player, helmetItem, TriggerType.SHIFT);
                }

                // 胸甲
                if (player.getInventory().getChestplate() != null) {
                    ItemStack chestplateItem = player.getInventory().getChestplate();

                    castItemSkillByTriggerType(player, chestplateItem, TriggerType.SHIFT);
                }

                // 裤子
                if (player.getInventory().getLeggings() != null) {
                    ItemStack leggingsItem = player.getInventory().getLeggings();

                    castItemSkillByTriggerType(player, leggingsItem, TriggerType.SHIFT);
                }

                // 鞋子
                if (player.getInventory().getBoots() != null) {
                    ItemStack bootsItem = player.getInventory().getBoots();

                    castItemSkillByTriggerType(player, bootsItem, TriggerType.SHIFT);
                }

                // 主手
                if (!player.getInventory().getItemInMainHand().getType().isAir()) {
                    ItemStack mainHandItem = player.getInventory().getItemInMainHand();

                    if (!isArmour(mainHandItem)) {
                        castItemSkillByTriggerType(player, mainHandItem, TriggerType.SHIFT);
                    }
                }
            }
        }.runTaskAsynchronously(RookieSkills.getInstance());
    }

    @EventHandler
    public void onItemInMainHandClick(PlayerInteractEvent event) {
        player = event.getPlayer();

        new BukkitRunnable() {
            public void run() {
                // 蹲下交互判定
                if (player.isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                    if (!player.getInventory().getItemInMainHand().getType().isAir()) {
                        ItemStack item = player.getInventory().getItemInMainHand();

                        castItemSkillByTriggerType(player, item, TriggerType.RIGHT_SHIFT_CLICK);

                    }
                } else if (player.isSneaking() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
                    if (!player.getInventory().getItemInMainHand().getType().isAir()) {
                        ItemStack item = player.getInventory().getItemInMainHand();

                        castItemSkillByTriggerType(player, item, TriggerType.LEFT_SHIFT_CLICK);

                    }
                }

                // 非蹲下交互判定
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (!player.getInventory().getItemInMainHand().getType().isAir()) {
                        ItemStack item = player.getInventory().getItemInMainHand();

                        castItemSkillByTriggerType(player, item, TriggerType.RIGHT_CLICK);

                    }
                } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (!player.getInventory().getItemInMainHand().getType().isAir()) {
                        ItemStack item = player.getInventory().getItemInMainHand();

                        castItemSkillByTriggerType(player, item, TriggerType.LEFT_CLICK);

                    }
                }
            }
        }.runTaskAsynchronously(RookieSkills.getInstance());
    }

    @EventHandler
    public void onItemInMainHandAttack(EntityDamageByEntityEvent event) {
        new BukkitRunnable() {
            public void run() {
                // 当攻击造成者为玩家
                if (event.getDamager() instanceof Player) {
                    player = (Player) event.getDamager();

                    if (!player.getInventory().getItemInMainHand().getType().isAir()) {
                        ItemStack item = player.getInventory().getItemInMainHand();

                        castItemSkillByTriggerType(player, item, TriggerType.ATTACK);

                    }
                }
            }
        }.runTaskAsynchronously(RookieSkills.getInstance());
    }

    @EventHandler
    public void onItemEquipmentDamaged(EntityDamageByEntityEvent event) {
        new BukkitRunnable() {
            public void run() {
                if (event.getEntity() instanceof Player) {
                    player = (Player) event.getEntity();
                    // 头盔
                    if (player.getInventory().getHelmet() != null) {
                        ItemStack helmetItem = player.getInventory().getHelmet();

                        castItemSkillByTriggerType(player, helmetItem, TriggerType.DAMAGED);

                    }

                    // 胸甲
                    if (player.getInventory().getChestplate() != null) {
                        ItemStack chestplateItem = player.getInventory().getChestplate();

                        castItemSkillByTriggerType(player, chestplateItem, TriggerType.DAMAGED);

                    }

                    // 裤子
                    if (player.getInventory().getLeggings() != null) {
                        ItemStack leggingsItem = player.getInventory().getLeggings();

                        castItemSkillByTriggerType(player, leggingsItem, TriggerType.DAMAGED);

                    }

                    // 鞋子
                    if (player.getInventory().getBoots() != null) {
                        ItemStack bootsItem = player.getInventory().getBoots();

                        castItemSkillByTriggerType(player, bootsItem, TriggerType.DAMAGED);

                    }

                    // 主手
                    if (!player.getInventory().getItemInMainHand().getType().isAir()) {
                        ItemStack mainHandItem = player.getInventory().getItemInMainHand();

                        if (!isArmour(mainHandItem)) {
                            castItemSkillByTriggerType(player, mainHandItem, TriggerType.DAMAGED);
                        }
                    }
                }
            }
        }.runTaskAsynchronously(RookieSkills.getInstance());
    }

    @EventHandler
    public void onArrowShoot(ProjectileLaunchEvent event) {
        new BukkitRunnable() {
            public void run() {
                if (event.getEntity() instanceof Arrow) {
                    Arrow arrow = (Arrow) event.getEntity();

                    if (arrow.getShooter() instanceof Player) {
                        player = (Player) arrow.getShooter();
                        ItemStack item = player.getInventory().getItemInMainHand();

                        castItemSkillByTriggerType(player, item, TriggerType.BOW_SHOOT);

                    }
                }
            }
        }.runTaskAsynchronously(RookieSkills.getInstance());
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event) {
        new BukkitRunnable() {
            public void run() {
                if (event.getDamager() instanceof Arrow) {
                    Arrow arrow = (Arrow) event.getDamager();

                    if (arrow.getShooter() instanceof Player) {
                        player = (Player) arrow.getShooter();
                        ItemStack item = player.getInventory().getItemInMainHand();

                        castItemSkillByTriggerType(player, item, TriggerType.BOW_HIT);

                    }
                }
            }
        }.runTaskAsynchronously(RookieSkills.getInstance());
    }

    private void castItemSkillByTriggerType(Player player, ItemStack item, TriggerType type) {
        if (ItemService.getSkillDataByTriggerType(item, type) != null) {
            new BukkitRunnable() {
                public void run() {
                    ItemSkillData itemSkillData = ItemService.getSkillDataByTriggerType(item, type);
                    if (itemSkillData != null) {
                        ItemSkillImp.getSkillImpl(itemSkillData).castSkill(player, itemSkillData);
                    }
                }
            }.runTask(RookieSkills.getInstance());
        }
    }

    static boolean isArmour(ItemStack item) {
        if (item == null) {
            return false;
        }

        switch (item.getType()) {
            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_BOOTS:
            case IRON_HELMET:
            case IRON_CHESTPLATE:
            case IRON_LEGGINGS:
            case IRON_BOOTS:
            case GOLDEN_HELMET:
            case GOLDEN_CHESTPLATE:
            case GOLDEN_LEGGINGS:
            case GOLDEN_BOOTS:
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
            case NETHERITE_HELMET:
            case NETHERITE_CHESTPLATE:
            case NETHERITE_LEGGINGS:
            case NETHERITE_BOOTS:
            case TURTLE_HELMET:
            case ELYTRA:
                return true;
            default:
                return false;
        }
    }

}
