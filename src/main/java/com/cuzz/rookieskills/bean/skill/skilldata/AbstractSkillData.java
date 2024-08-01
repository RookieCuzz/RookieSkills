package com.cuzz.rookieskills.bean.skill.skilldata;


import lombok.Getter;

@Getter
public abstract class AbstractSkillData {
    private String skillId;
    private Double damage;

    private Double costMana;

    private Double costStamina;

    private Double radius;

    private Double timer;

    private Double duration;

    private Double coolDown;
}
