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

    /**
     *
     * @param player 玩家
     * @return
     */
    public boolean isInCoolDown(Player player){
        //判断是否持有技能
        if (!isOwner(player)){
            new Exception("请检查你的设计逻辑,有玩家尝试释放一个他未持有的技能");
            return true;
        }else {
            long l = System.currentTimeMillis();
            Long coolDownStamp = this.coolDownList.get(player);
            //玩家还未释放过技能 则未进入冷却
            if (null==coolDownStamp){
                return false;
            }
            //玩家理论可释放技能的时间为 上次释放的时间+冷却及以上
            if (l>=coolDownStamp+cooldownValue){
                return false;
            }
            return true;

        }


    }
}