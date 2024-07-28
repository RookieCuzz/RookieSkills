package com.cuzz.rookieskills.manager;

import com.cuzz.rookieskills.bean.player.RpgPlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RpgPlayerDataManager {


    private HashMap<Player, RpgPlayerData> RpgPlayerDataList=new HashMap<>();

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
