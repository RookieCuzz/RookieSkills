package com.cuzz.rookieskills.listeners;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.bean.TriggerType;
import com.cuzz.rookieskills.bean.skill.AbstractSkill;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.utils.functions.IFunction;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ItemSkillListener implements Listener {
    @EventHandler
    public void onItemInMainHandClick(PlayerInteractEvent event) {

        // 右键触发
        if (event.getPlayer().isSneaking() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {

            ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

                if (checkItemSkillTriggerType(meta, TriggerType.RIGHT_SHIFT_CLICK)) {
                    castItemSkill(meta, event.getPlayer(), TriggerType.RIGHT_SHIFT_CLICK);
                }

        } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

            if (checkItemSkillTriggerType(meta, TriggerType.RIGHT_CLICK)) {
                castItemSkill(meta, event.getPlayer(), TriggerType.RIGHT_CLICK);
            }
        }

        // 左键触发
        if (event.getPlayer().isSneaking() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {

            ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

            if (checkItemSkillTriggerType(meta, TriggerType.LEFT_SHIFT_CLICK)) {
                castItemSkill(meta, event.getPlayer(), TriggerType.LEFT_SHIFT_CLICK);
            }

        } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();

            if (checkItemSkillTriggerType(meta, TriggerType.LEFT_CLICK)) {
                castItemSkill(meta, event.getPlayer(), TriggerType.LEFT_CLICK);
            }
        }
    }

//    public boolean isCoolDown(Player player,String skillId){
//
//    }


    public void getSkillInfo(){


    }
    public AbstractSkill getSkillByTriggerType(ItemMeta itemMeta,TriggerType triggerType) {
        AbstractSkill abstractSkill=null;
        if (null == itemMeta) {
            return null;
        }
        //获取物品PDC
        PersistentDataContainer PDC = itemMeta.getPersistentDataContainer();
        //判断PDC数据容器内是否拥有技能
        if (PDC.has(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER)) {
            //判断获取技能列表 以触发类型为单位
            PersistentDataContainer skillList = PDC.get(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER);
            //在技能列表内遍历查找是否该触发类型的技能
            NamespacedKey namespacedKey = new NamespacedKey(RookieSkills.getInstance(), triggerType.toString().toLowerCase());
            if (!skillList.has(namespacedKey, PersistentDataType.TAG_CONTAINER)) {
                return abstractSkill;
            }else {
                PersistentDataContainer skillInfo = skillList.get(namespacedKey, PersistentDataType.TAG_CONTAINER);
                String skillId = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), "skillId"), PersistentDataType.STRING);
                String name = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), "skillName"), PersistentDataType.STRING);
                Double skillCooldownValue = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), "skillCooldownValue"), PersistentDataType.DOUBLE);
                Double skillDamageValue = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), "skillDamageValue"), PersistentDataType.DOUBLE);
                Double skillManaValue = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), "skillManaValue"), PersistentDataType.DOUBLE);
                Double skillStaminaValue = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), "skillStaminaValue"), PersistentDataType.DOUBLE);
                Double skillRadiusValue = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), "skillRadiusValue"), PersistentDataType.DOUBLE);
                Double skillDurationValue = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), "skillDurationValue"), PersistentDataType.DOUBLE);
                Double skillTimerValue = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), "skillTimerValue"), PersistentDataType.DOUBLE);


            }

        }
        return abstractSkill;
    }


    /**
     *
     * @param meta 物品元数据
     * @param type  触发类型
     * @return
     */
    public Boolean checkItemSkillTriggerType(ItemMeta meta, TriggerType type) {
        if (meta != null) {
            PersistentDataContainer skillList = meta.getPersistentDataContainer();

            if (skillList.has(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER)) {
                PersistentDataContainer skillInfo = skillList.get(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER);

                for (NamespacedKey key : skillInfo.getKeys()) {
                    if (key.getKey().equalsIgnoreCase(type.toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void castItemSkill(ItemMeta meta, Player player, TriggerType triggerType) {
        if (meta != null) {
            PersistentDataContainer skillList = meta.getPersistentDataContainer();

            if (skillList.has(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER)) {
                PersistentDataContainer skillInfo = skillList.get(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER);

                for (String skillId : new ArrayList<>(RookieSkills.getInstance().getSkillConfigManager().getSkillList().keySet())) {

                    if (skillInfo.has(new NamespacedKey(RookieSkills.getInstance(), triggerType.toString().toLowerCase()), PersistentDataType.TAG_CONTAINER)) {
                        PersistentDataContainer skillData = skillInfo.get(new NamespacedKey(RookieSkills.getInstance(), triggerType.toString().toLowerCase()), PersistentDataType.TAG_CONTAINER);

                        if (skillData.get(new NamespacedKey(RookieSkills.getInstance(), "skillId"),PersistentDataType.STRING).equalsIgnoreCase(skillId)) {

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
