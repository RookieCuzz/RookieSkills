package com.cuzz.rookieskills.api;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.bean.TriggerType;
import com.cuzz.rookieskills.bean.namespaces.AllNameSpacedKey;
import com.cuzz.rookieskills.bean.skill.AbstractSkill;
import com.cuzz.rookieskills.bean.skill.skilldata.impl.ItemSkillData;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class ItemService {

    public static ItemStack addSkillData2Item(ItemStack item, ItemSkillData data, TriggerType triggerType) {
        ItemMeta meta = item.getItemMeta();
        // 主键
        NamespacedKey skillListKey = new NamespacedKey(RookieSkills.getInstance(), "skillList");

        // 主容器
        PersistentDataContainer PDC = meta.getPersistentDataContainer();

        // 子容器
        PersistentDataContainer skillList;
        if (PDC.has(skillListKey, PersistentDataType.TAG_CONTAINER)) {
            skillList = PDC.get(skillListKey, PersistentDataType.TAG_CONTAINER);
        } else {
            skillList = PDC.getAdapterContext().newPersistentDataContainer();
        }

        // 存储技能的信息
        PersistentDataContainer skillData = skillList.getAdapterContext().newPersistentDataContainer();

        skillData.set(AllNameSpacedKey.skillId, PersistentDataType.STRING,data.getSkillId());
        skillData.set(AllNameSpacedKey.skillCooldownValue, PersistentDataType.DOUBLE, data.getCoolDown());
        skillData.set(AllNameSpacedKey.skillDamageValue, PersistentDataType.DOUBLE, data.getDamage());
        skillData.set(AllNameSpacedKey.skillManaValue, PersistentDataType.DOUBLE, data.getCostMana());
        skillData.set(AllNameSpacedKey.skillStaminaValue, PersistentDataType.DOUBLE, data.getCostStamina());
        skillData.set(AllNameSpacedKey.skillRadiusValue, PersistentDataType.DOUBLE, data.getRadius());
        skillData.set(AllNameSpacedKey.skillDurationValue, PersistentDataType.DOUBLE, data.getDuration());
        skillData.set(AllNameSpacedKey.skillTimerValue, PersistentDataType.DOUBLE, data.getTimer());

        skillList.set(new NamespacedKey(RookieSkills.getInstance(), String.valueOf(triggerType)), PersistentDataType.TAG_CONTAINER, skillData);

        // 将子容器再装入主容器
        skillList.set(skillListKey, PersistentDataType.TAG_CONTAINER, skillList);

        // 将元数据应用回物品
        item.setItemMeta(meta);

        return item;

    }

    // 为物品添加唯一的 NBT UUID
    public static void addUUIDToItem(ItemStack item) {
        // 生成一个新的 UUID
        UUID uuid = UUID.randomUUID();

        // 获取物品的 ItemMeta
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return;

        // 获取或创建 PersistentDataContainer
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        // 创建 NamespacedKey，用于唯一标识 UUID 标签
        NamespacedKey key = new NamespacedKey(RookieSkills.getInstance(), "unique_id");

        // 将 UUID 转换为字符串并存储在 PersistentDataContainer 中
        container.set(key, PersistentDataType.STRING, uuid.toString());

        // 将修改后的 ItemMeta 重新应用到物品上
        item.setItemMeta(itemMeta);
    }

    public static ItemSkillData getSkillDataByTriggerType(ItemMeta itemMeta,TriggerType triggerType){
        if (null == itemMeta) {
            return null;
        }

        PersistentDataContainer PDC = itemMeta.getPersistentDataContainer();
        //判断PDC数据容器内是否拥有技能
        if (PDC.has(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER)) {
            //判断获取技能列表 以触发类型为单位
            PersistentDataContainer skillList = PDC.get(new NamespacedKey(RookieSkills.getInstance(), "skillList"), PersistentDataType.TAG_CONTAINER);
            //在技能列表内遍历查找是否该触发类型的技能
            NamespacedKey namespacedKey = new NamespacedKey(RookieSkills.getInstance(), triggerType.toString().toLowerCase());
            if (!skillList.has(namespacedKey, PersistentDataType.TAG_CONTAINER)) {
                return null;
            }else {
                PersistentDataContainer itemData = skillList.get(namespacedKey, PersistentDataType.TAG_CONTAINER);
                ItemSkillData itemSkillData = new ItemSkillData();
                //必有
                itemSkillData.setSkillId(itemData.get(AllNameSpacedKey.skillId, PersistentDataType.STRING));

                //伤害数值
                if (itemData.has(AllNameSpacedKey.skillDamageValue,PersistentDataType.DOUBLE)){
                    itemSkillData.setDamage(itemData.get(AllNameSpacedKey.skillDamageValue,PersistentDataType.DOUBLE));
                }
                //冷却
                if (itemData.has(AllNameSpacedKey.skillCooldownValue,PersistentDataType.DOUBLE)){
                    itemSkillData.setCoolDown(itemData.get(AllNameSpacedKey.skillCooldownValue,PersistentDataType.DOUBLE));
                }
                //消耗蓝量
                if (itemData.has(AllNameSpacedKey.skillManaValue,PersistentDataType.DOUBLE)){
                    itemSkillData.setCostMana(itemData.get(AllNameSpacedKey.skillManaValue,PersistentDataType.DOUBLE));
                }
                //消耗体力
                if (itemData.has(AllNameSpacedKey.skillStaminaValue,PersistentDataType.DOUBLE)){
                    itemSkillData.setCostStamina(itemData.get(AllNameSpacedKey.skillStaminaValue,PersistentDataType.DOUBLE));
                }

                //范围 例如玩家向后闪现  四格
                if (itemData.has(AllNameSpacedKey.skillRadiusValue,PersistentDataType.DOUBLE)){
                    itemSkillData.setRadius(itemData.get(AllNameSpacedKey.skillRadiusValue,PersistentDataType.DOUBLE));
                }

                //持续时间
                if (itemData.has(AllNameSpacedKey.skillDurationValue,PersistentDataType.DOUBLE)){
                    itemSkillData.setDuration(itemData.get(AllNameSpacedKey.skillDurationValue,PersistentDataType.DOUBLE));
                }
                //周期
                if (itemData.has(AllNameSpacedKey.skillTimerValue,PersistentDataType.DOUBLE)){
                    itemSkillData.setTimer(itemData.get(AllNameSpacedKey.skillTimerValue,PersistentDataType.DOUBLE));
                }
                return itemSkillData;
            }

        }
        return null;
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
}
