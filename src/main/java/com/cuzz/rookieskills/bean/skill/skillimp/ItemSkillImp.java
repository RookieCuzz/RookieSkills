package com.cuzz.rookieskills.bean.skill.skillimp;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.bean.skill.AbstractSkill;
import com.cuzz.rookieskills.bean.skill.ItemSkill;
import com.cuzz.rookieskills.bean.skill.SkillPrototype;
import com.cuzz.rookieskills.bean.skill.skilldata.AbstractSkillData;
import com.cuzz.rookieskills.bean.skill.skilldata.impl.ItemSkillData;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.print.DocFlavor;
import java.util.HashMap;


//假定  技能是 相声
//那么每个说相声的人被记录在某个档案中
public class ItemSkillImp extends AbstractSkill implements ItemSkill{





    //K代表了某个物品,V代表了物品的技能元数据
    private HashMap<String, ItemSkillData> dataList=new HashMap<>();

    //K代表了某个物品, V代表了其上次释放该技能的时间
    private HashMap<String,Long> timeStamp=new HashMap<>();


    public ItemSkillImp(SkillPrototype skillPrototype) {
        super(skillPrototype);
    }


    @Override
    public boolean isNotInCoolDown(String uuid, AbstractSkillData itemSkillData) {

        //冷却记录表未发现此物品
        if (!this.coolDownList.keySet().contains(uuid)){
            return true;
        }

        Long lastStamp = this.coolDownList.get(uuid);

        if(null==lastStamp){
            return true;
        }
        long currentTime = System.currentTimeMillis();
        //玩家理论可释放技能的时间为 上次释放时间+冷却时间
        //由于物品技能冷却一般标识在武器物品上
        //若再次触发时刻已过 则不在冷却内
        //冷却单位为秒
        if (currentTime>=lastStamp+itemSkillData.getCoolDown()*1000){
            return true;
        }

        return false;
    }

    @Override
    public void setItemCoolDown(String uuid){
        this.coolDownList.put(uuid,System.currentTimeMillis());
    }

    @Override
    public void castSkill(Player player, ItemSkillData itemSkillData) {
        MythicBukkit.inst().getAPIHelper().castSkill(player, itemSkillData.getSkillId());
    }

    @Override
    public boolean isSkillAvailable(String uuid,AbstractSkillData skillData) {
        return this.isNotInCoolDown(uuid,skillData);
    }



}
