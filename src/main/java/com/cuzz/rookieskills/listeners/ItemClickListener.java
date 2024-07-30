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
                PersistentDataContainer skillList = meta.getPersistentDataContainer();

                if (skillList.has(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER)) {
                    PersistentDataContainer skillInfo = skillList.get(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER);

                    for (String skillId : new ArrayList<>(RookieSkills.getInstance().getSkillConfigManager().getSkillList().keySet())) {

                        if (skillInfo.has(new NamespacedKey(RookieSkills.getInstance(), skillId), PersistentDataType.TAG_CONTAINER)) {
                            PersistentDataContainer skillData = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), skillId), PersistentDataType.TAG_CONTAINER);

//                            if (skillData.get(new NamespacedKey(RookieSkills.getInstance(), skillId), PersistentDataType.STRING).equalsIgnoreCase(skillId)) {
                            MythicBukkit.inst().getAPIHelper().castSkill(event.getPlayer(), RookieSkills.getInstance().getSkillConfigManager().getSkillList().get(skillId).getId());

                            String name = RookieSkills.getInstance().getSkillConfigManager().getSkillList().get(skillId).getName();
                            Double skillCooldownValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillCooldownValue"), PersistentDataType.DOUBLE);
                            Double skillDamageValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillDamageValue"), PersistentDataType.DOUBLE);
                            Double skillManaValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillManaValue"), PersistentDataType.DOUBLE);
                            Double skillStaminaValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillStaminaValue"), PersistentDataType.DOUBLE);
                            Double skillRadiusValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillRadiusValue"), PersistentDataType.DOUBLE);
                            Double skillDurationValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillDurationValue"), PersistentDataType.DOUBLE);
                            Double skillTimerValue = skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillTimerValue"), PersistentDataType.DOUBLE);

                            event.getPlayer().sendMessage("释放的技能名字为: " + name);
                            event.getPlayer().sendMessage("技能的冷却为: " + skillCooldownValue);
                            event.getPlayer().sendMessage("技能的伤害为: " + skillDamageValue);
                            event.getPlayer().sendMessage("技能的耗蓝为: " + skillManaValue);
                            event.getPlayer().sendMessage("技能的耗体为: " + skillStaminaValue);
                            event.getPlayer().sendMessage("技能的范围为: " + skillRadiusValue);
                            event.getPlayer().sendMessage("技能的持续时间为: " + skillDurationValue);
                            event.getPlayer().sendMessage("技能的循环触发为: " + skillTimerValue);
//                            }
                        }


                    }

                }

            }
//            }
        }
    }
}
