package com.cuzz.rookieskills.bean.skill;


import org.bukkit.entity.Player;

import java.util.HashMap;

//技能应该进行分类 归为
public interface ItemSkill {

    public static HashMap<String,Long> coolDownList=new HashMap<>();
    public boolean isNotInCoolDown(String uuid,double coolDown);
    public void setItemCoolDown(String uuid);

    public boolean isSkillAvailable(String uuid,double coolDown);
}
