package com.cuzz.rookieskills.listeners;

import com.cuzz.rookieskills.RookieSkills;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class ItemClickListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

//            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

//            if (item.getType() == Material.DIAMOND_SWORD) {
                if (meta != null) {

                    PersistentDataContainer PDC = meta.getPersistentDataContainer();

                    NamespacedKey hasSkillKey = new NamespacedKey(RookieSkills.getInstance(), "hasSkill");

                    if (PDC.has(hasSkillKey, PersistentDataType.TAG_CONTAINER)) {
                        PersistentDataContainer skillInfoContainer = PDC.get(hasSkillKey, PersistentDataType.TAG_CONTAINER);

                        for (String skillName : new ArrayList<>(RookieSkills.getInstance().getSkillConfigManager().getSkillList().keySet())) {
                            if (skillInfoContainer.get(new NamespacedKey(RookieSkills.getInstance(), "skillName"), PersistentDataType.STRING).equalsIgnoreCase(skillName)) {
                                MythicBukkit.inst().getAPIHelper().castSkill(event.getPlayer(), RookieSkills.getInstance().getSkillConfigManager().getSkillList().get(skillName).getId());

                                String name = skillInfoContainer.get(new NamespacedKey(RookieSkills.getInstance(), "skillName"), PersistentDataType.STRING);
                                Double skillCooldownValue = skillInfoContainer.get(new NamespacedKey(RookieSkills.getInstance(), "skillCooldownValue"), PersistentDataType.DOUBLE);
                                Double skillDamageValue = skillInfoContainer.get(new NamespacedKey(RookieSkills.getInstance(), "skillDamageValue"), PersistentDataType.DOUBLE);
                                Double skillManaValue = skillInfoContainer.get(new NamespacedKey(RookieSkills.getInstance(), "skillManaValue"), PersistentDataType.DOUBLE);
                                Double skillStaminaValue = skillInfoContainer.get(new NamespacedKey(RookieSkills.getInstance(), "skillStaminaValue"), PersistentDataType.DOUBLE);
                                Double skillRadiusValue = skillInfoContainer.get(new NamespacedKey(RookieSkills.getInstance(), "skillRadiusValue"), PersistentDataType.DOUBLE);
                                Double skillDurationValue = skillInfoContainer.get(new NamespacedKey(RookieSkills.getInstance(), "skillDurationValue"), PersistentDataType.DOUBLE);
                                Double skillTimerValue = skillInfoContainer.get(new NamespacedKey(RookieSkills.getInstance(), "skillTimerValue"), PersistentDataType.DOUBLE);

                                event.getPlayer().sendMessage("释放的技能名字为: " + name);
                                event.getPlayer().sendMessage("技能的冷却为: " + skillCooldownValue);
                                event.getPlayer().sendMessage("技能的伤害为: " + skillDamageValue);
                                event.getPlayer().sendMessage("技能的耗蓝为: " + skillManaValue);
                                event.getPlayer().sendMessage("技能的耗体为: " + skillStaminaValue);
                                event.getPlayer().sendMessage("技能的范围为: " + skillRadiusValue);
                                event.getPlayer().sendMessage("技能的持续时间为: " + skillDurationValue);
                                event.getPlayer().sendMessage("技能的循环触发为: " + skillTimerValue);
                            }
                        }

                    }

                }
//            }
        }
    }
}
