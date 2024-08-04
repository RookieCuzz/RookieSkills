package com.cuzz.rookieskills.bean.skill.skillimp;

import com.cuzz.rookieskills.bean.skill.AbstractSkill;
import com.cuzz.rookieskills.bean.skill.ItemSkill;
import com.cuzz.rookieskills.bean.skill.SkillPrototype;
import com.cuzz.rookieskills.bean.skill.skilldata.AbstractSkillData;
import com.cuzz.rookieskills.bean.skill.skilldata.impl.ItemSkillData;
import io.lumine.mythic.bukkit.MythicBukkit;
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
        if (!this.coolDownList.keySet().contains(uuid)) {
            return true;
        }

        Long lastStamp = this.coolDownList.get(uuid);

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
        this.coolDownList.put(uuid, System.currentTimeMillis());
    }

    public void castSkill(Player player, ItemSkillData itemSkillData) {
        String uuid = itemSkillData.getUuid();
        if (!isNotInCoolDown(uuid, itemSkillData)) {
            double cooldownTimeLeft = itemSkillData.getCoolDown() - (System.currentTimeMillis() - this.coolDownList.get(uuid)) / 1000.0D;

            player.sendMessage("当前技能正在冷却中! 剩余" + String.format("%.2f", cooldownTimeLeft) + "秒");
            return;
        }

        cache.put(uuid, itemSkillData);
        MythicBukkit.inst().getAPIHelper().castSkill(player, itemSkillData.getSkillId());

        this.coolDownList.put(uuid, System.currentTimeMillis());
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
