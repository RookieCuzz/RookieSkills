package com.cuzz.rookieskills.bean.skill.skilldata.impl;

import com.cuzz.rookieskills.bean.skill.skilldata.AbstractSkillData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSkillData extends AbstractSkillData {
    //持有改数据的物品uuid
    private String uuid;
}
