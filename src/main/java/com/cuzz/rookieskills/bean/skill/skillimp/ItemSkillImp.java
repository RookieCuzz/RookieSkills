package com.cuzz.rookieskills.bean.skill.skillimp;

import com.cuzz.rookieskills.bean.skill.AbstractSkill;
import com.cuzz.rookieskills.bean.skill.ItemSkill;
import com.cuzz.rookieskills.bean.skill.SkillPrototype;
import com.cuzz.rookieskills.bean.skill.skilldata.AbstractSkillData;
import com.cuzz.rookieskills.bean.skill.skilldata.impl.ItemSkillData;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import io.lumine.mythic.lib.skill.SimpleSkill;
import io.lumine.mythic.lib.skill.handler.SkillHandler;
import io.lumine.mythic.lib.skill.result.SkillResult;
import io.lumine.mythic.lib.skill.trigger.TriggerType;
import lombok.Getter;
import org.bukkit.entity.Player;
import java.util.HashMap;


//假定  技能是 相声
//那么每个说相声的人被记录在某个档案中
public class ItemSkillImp extends AbstractSkill implements ItemSkill {

    public static HashMap<String, ItemSkillData> cache = new HashMap<>();


    @Getter
    private static HashMap<String, ItemSkillImp> skillListX = new HashMap<>();

    //K代表了某个物品,V代表了物品的技能元数据
    private HashMap<String, ItemSkillData> dataListMarkByItem = new HashMap<>();

    //K代表了某个玩家,V代表了物品的技能元数据
    //这个数据结构纯粹为mm技能 的参数变量服务的 查看 TestMMPapi
    private HashMap<String, ItemSkillData> dataListMarkByPlayer = new HashMap<>();

    public ItemSkillImp(SkillPrototype skillPrototype) {
        super(skillPrototype);
    }


    @Override
    public boolean isNotInCoolDown(String uuid, AbstractSkillData itemSkillData) {

        //冷却记录表未发现此物品
        if (!CoolDownList.keySet().contains(uuid)) {
            return true;
        }

        Long lastStamp = CoolDownList.get(uuid);

        if (null == lastStamp) {
            return true;
        }
        long currentTime = System.currentTimeMillis();
        //玩家理论可释放技能的时间为 上次释放时间+冷却时间
        //由于物品技能冷却一般标识在武器物品上
        //若再次触发时刻已过 则不在冷却内
        //冷却单位为秒
        if (currentTime >= lastStamp + itemSkillData.getCoolDown() * 1000) {
            return true;
        }

        return false;
    }

    @Override
    public void setItemCoolDown(String uuid) {
        CoolDownList.put(uuid, System.currentTimeMillis());
    }


    /**
     * 方法1: 通过技能ID直接释放技能（推荐方式 - 使用Skill.cast()）
     *
     * @param player 释放技能的玩家
     * @param skillId 技能ID（如"FIREBALL"、"HEAL"等）
     */
    public static void castSkillById(Player player, String skillId) {
        try {
            // 获取玩家数据
            MMOPlayerData playerData = MMOPlayerData.get(player);

            // 通过技能ID获取技能处理器
            SkillHandler<?> handler = MythicLib.plugin.getSkills().getHandlerOrThrow(skillId.toUpperCase().replace("-", "_"));

            // 创建简单技能并释放
            SimpleSkill skill = new SimpleSkill(handler);
            SkillResult result = skill.cast(playerData, TriggerType.CAST);

            if (result.isSuccessful()) {
                player.sendMessage("技能 " + skillId + " 释放成功！");
            } else {
                player.sendMessage("技能 " + skillId + " 释放失败！");
            }

        } catch (Exception e) {
            player.sendMessage("找不到技能: " + skillId);
            e.printStackTrace();
        }
    }
    public void castSkill(Player player, ItemSkillData itemSkillData) {
        String uuid = itemSkillData.getUuid();
        if (!isNotInCoolDown(uuid, itemSkillData)) {
            double cooldownTimeLeft = itemSkillData.getCoolDown() - (System.currentTimeMillis() - CoolDownList.get(uuid)) / 1000.0D;

            player.sendMessage("当前技能正在冷却中! 剩余" + String.format("%.2f", cooldownTimeLeft) + "秒");
            return;
        }

        cache.put(uuid, itemSkillData);
        castSkillById(player, itemSkillData.getSkillId());


//        MythicBukkit.inst().getAPIHelper().castSkill(player, itemSkillData.getSkillId());

        CoolDownList.put(uuid, System.currentTimeMillis());
    }

    public static ItemSkillImp getSkillImpl(ItemSkillData itemSkillData) {
        //获取技能实现
        ItemSkillImp itemSkillImp = skillListX.get(itemSkillData.getSkillId());
        return itemSkillImp;
    }

    @Override
    public boolean isSkillAvailable(String uuid, AbstractSkillData skillData) {
        return this.isNotInCoolDown(uuid, skillData);
    }


}
