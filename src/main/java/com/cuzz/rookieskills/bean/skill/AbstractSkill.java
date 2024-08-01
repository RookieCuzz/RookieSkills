package com.cuzz.rookieskills.bean.skill;


import com.cuzz.rookieskills.RookieSkills;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.entity.Player;

public abstract class AbstractSkill {
    // 技能蓝图（配置模板）
    private SkillPrototype skillPrototype;

    // 构造函数，用于初始化 skillPrototype
    public AbstractSkill(SkillPrototype skillPrototype) {
        this.skillPrototype = skillPrototype;
    }

    // 获取技能蓝图
    public SkillPrototype getSkillPrototype() {
        return skillPrototype;
    }

    // 设置技能蓝图
    public void setSkillPrototype(SkillPrototype skillPrototype) {
        this.skillPrototype = skillPrototype;
    }
    
}
