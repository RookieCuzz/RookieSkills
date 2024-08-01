package com.cuzz.rookieskills.bean.skill;

import lombok.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class Skill {



    //哪些人 持有该技能  无法被设置
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<Player> owners=new ArrayList<>();
    //玩家冷却表 上次释放的时间
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private HashMap<Player,Long> coolDownList=new HashMap<>();

    private String name;
    private String id;
    private String symbol;
    private String cooldownDisplayName;
    private Double cooldownValue;
    private String damageDisplayName;
    private Double damageValue;
    private String manaDisplayName;
    private Double manaValue;
    private String staminaDisplayName;
    private Double staminaValue;
    private String radiusDisplayName;
    private Double radiusValue;
    private String durationDisplayName;
    private Double durationValue;
    private String timerDisplayName;
    private Double timerValue;


    //记录玩家技能释放的时间
    public void setPlayerCoolDown(Player player){
        this.coolDownList.put(player,System.currentTimeMillis());
    }

    public boolean isOwner(Player player){
        return this.owners.contains(player);

    }


}