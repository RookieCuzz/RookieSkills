package com.cuzz.rookieskills.listeners;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.api.ItemService;
import com.cuzz.rookieskills.bean.TriggerType;
import com.cuzz.rookieskills.bean.skill.AbstractSkill;
import com.cuzz.rookieskills.bean.skill.skilldata.impl.ItemSkillData;
import com.cuzz.rookieskills.bean.skill.skillimp.ItemSkillImp;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.utils.functions.IFunction;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemSkillListener implements Listener {

    public static HashMap<String ,ItemSkillData> cache=new HashMap<>();
    @EventHandler
    public void onItemInMainHandClick(PlayerInteractEvent event) {
        new BukkitRunnable() {
            public void run() {
                // 蹲下交互判定
                if (event.getPlayer().isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                    if (!event.getPlayer().getInventory().getItemInMainHand().getType().isAir()) {
                        ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(meta, TriggerType.RIGHT_SHIFT_CLICK) != null) {
                            ItemSkillData skillData = ItemService.getSkillDataByTriggerType(meta, TriggerType.RIGHT_SHIFT_CLICK);
                            new BukkitRunnable() {
                                public void run() {
                                    ItemSkillImp.getSkillImpl(skillData).castSkill(event.getPlayer(),skillData);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
                    }
                } else if (event.getPlayer().isSneaking() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
                    if (!event.getPlayer().getInventory().getItemInMainHand().getType().isAir()) {
                        ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(meta, TriggerType.LEFT_SHIFT_CLICK) != null) {
                            new BukkitRunnable() {
                                public void run() {
                                    castItemSkill(meta, event.getPlayer(), TriggerType.LEFT_SHIFT_CLICK);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
                    }
                }

                // 非蹲下交互判定
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (!event.getPlayer().getInventory().getItemInMainHand().getType().isAir()) {
                        ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(meta, TriggerType.RIGHT_CLICK) != null) {
                            new BukkitRunnable() {
                                public void run() {
                                    castItemSkill(meta, event.getPlayer(), TriggerType.RIGHT_CLICK);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
                    }
                } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (!event.getPlayer().getInventory().getItemInMainHand().getType().isAir()) {
                        ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(meta, TriggerType.LEFT_CLICK) != null) {
                            new BukkitRunnable() {
                                public void run() {
                                    castItemSkill(meta, event.getPlayer(), TriggerType.LEFT_CLICK);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
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
                    Player player = (Player) event.getDamager();

                    if (!player.getInventory().getItemInMainHand().getType().isAir()) {
                        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(meta, TriggerType.ATTACK) != null) {
                            new BukkitRunnable() {
                                public void run() {
                                    castItemSkill(meta, player, TriggerType.ATTACK);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
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
                    Player player = (Player) event.getEntity();
                    // 头盔
                    if (player.getInventory().getHelmet() != null) {
                        ItemMeta helmetMeta = player.getInventory().getHelmet().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(helmetMeta, TriggerType.DAMAGED) != null) {
                            new BukkitRunnable() {
                                public void run() {
                                    castItemSkill(helmetMeta, player, TriggerType.DAMAGED);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
                    }

                    // 胸甲
                    if (player.getInventory().getChestplate() != null) {
                        ItemMeta chestplateMeta = player.getInventory().getChestplate().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(chestplateMeta, TriggerType.DAMAGED) != null) {
                            new BukkitRunnable() {
                                public void run() {
                                    castItemSkill(chestplateMeta, player, TriggerType.DAMAGED);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
                    }

                    // 裤子
                    if (player.getInventory().getLeggings() != null) {
                        ItemMeta leggingsMeta = player.getInventory().getLeggings().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(leggingsMeta, TriggerType.DAMAGED) != null) {
                            new BukkitRunnable() {
                                public void run() {
                                    castItemSkill(leggingsMeta, player, TriggerType.DAMAGED);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
                    }

                    // 鞋子
                    if (player.getInventory().getBoots() != null) {
                        ItemMeta bootsMeta = player.getInventory().getBoots().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(bootsMeta, TriggerType.DAMAGED) != null) {
                            new BukkitRunnable() {
                                public void run() {
                                    castItemSkill(bootsMeta, player, TriggerType.DAMAGED);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
                    }

                    // 主手
                    if (!player.getInventory().getItemInMainHand().getType().isAir()) {
                        ItemMeta mainHandMeta = player.getInventory().getItemInMainHand().getItemMeta();

                        if (ItemService.getSkillDataByTriggerType(mainHandMeta, TriggerType.DAMAGED) != null) {
                            new BukkitRunnable() {
                                public void run() {
                                    castItemSkill(mainHandMeta, player, TriggerType.DAMAGED);
                                }
                            }.runTask(RookieSkills.getInstance());
                        }
                    }
                }
            }
        }.runTaskAsynchronously(RookieSkills.getInstance());
    }

    public void castItemSkill(ItemMeta meta, Player player, TriggerType triggerType) {
        if (meta != null) {
            PersistentDataContainer PDC = meta.getPersistentDataContainer();

            if (PDC.has(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER)) {
                PersistentDataContainer skillInfo = PDC.get(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER);

                for (String skillId : new ArrayList<>(RookieSkills.getInstance().getSkillConfigManager().getSkillList().keySet())) {

                    if (skillInfo.has(new NamespacedKey(RookieSkills.getInstance(), triggerType.toString().toLowerCase()), PersistentDataType.TAG_CONTAINER)) {
                        PersistentDataContainer skillData = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), triggerType.toString().toLowerCase()), PersistentDataType.TAG_CONTAINER);

                        if (skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillId"), PersistentDataType.STRING).equalsIgnoreCase(skillId)) {

                            MythicBukkit.inst().getAPIHelper().castSkill(player, RookieSkills.getInstance().getSkillConfigManager().getSkillList().get(skillId).getId());

                            String name = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillName"), PersistentDataType.STRING);
                            Double skillCooldownValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillCooldownValue"), PersistentDataType.DOUBLE);
                            Double skillDamageValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillDamageValue"), PersistentDataType.DOUBLE);
                            Double skillManaValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillManaValue"), PersistentDataType.DOUBLE);
                            Double skillStaminaValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillStaminaValue"), PersistentDataType.DOUBLE);
                            Double skillRadiusValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillRadiusValue"), PersistentDataType.DOUBLE);
                            Double skillDurationValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillDurationValue"), PersistentDataType.DOUBLE);
                            Double skillTimerValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillTimerValue"), PersistentDataType.DOUBLE);

                            player.sendMessage("释放的技能名字为: " + name);
                            player.sendMessage("技能的冷却为: " + skillCooldownValue);
                            player.sendMessage("技能的伤害为: " + skillDamageValue);
                            player.sendMessage("技能的耗蓝为: " + skillManaValue);
                            player.sendMessage("技能的耗体为: " + skillStaminaValue);
                            player.sendMessage("技能的范围为: " + skillRadiusValue);
                            player.sendMessage("技能的持续时间为: " + skillDurationValue);
                            player.sendMessage("技能的循环触发为: " + skillTimerValue);
                        }
                    }
                }
            }
        }
    }
}
