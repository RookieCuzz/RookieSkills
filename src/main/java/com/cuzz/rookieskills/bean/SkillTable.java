package com.cuzz.rookieskills.bean;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SkillTable {
    @Getter
    @Setter
    private Player owner;

    private HashMap<Integer,String> skillsTable=new HashMap<>();

    public boolean setSkillLoc(int loc,String skill){
        //技能位置必须是1-4or 6-9
        if (5==loc||loc>9||loc<1){
            return false;
        }
        skillsTable.put(loc,skill);
        return true;
    }
    public String getSkillByLoc(int loc){
        return skillsTable.get(loc);

    }
}
