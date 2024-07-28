package com.cuzz.rookieskills.manager;

import com.cuzz.rookieskills.bean.player.RpgPlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class RpgPlayerDataManager {


    private ConcurrentHashMap<Player, RpgPlayerData> RpgPlayerDataList=new ConcurrentHashMap<>();

    private static RpgPlayerDataManager singleton;
    private RpgPlayerDataManager(){

    }
    public static RpgPlayerDataManager getInstance(){
        if (null==singleton){
            singleton=new RpgPlayerDataManager();
        }
            return singleton;
    }

    public void addPlayerData(Player player,RpgPlayerData rpgPlayerData) {
        this.RpgPlayerDataList.put(player,rpgPlayerData);
    };
}
