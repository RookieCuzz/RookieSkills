package com.cuzz.rookieskills.manager;

import com.cuzz.rookieskills.bean.player.RpgPlayerData;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class RpgPlayerDataManager {


    @Getter
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
    public void removePlayerData(Player player) {
        if (!this.getRpgPlayerDataList().contains(player)){
            return;
        }else {
            this.RpgPlayerDataList.remove(player);

        }
    };
    public void addPlayerData(Player player,RpgPlayerData rpgPlayerData) {
        this.RpgPlayerDataList.put(player,rpgPlayerData);
    };
}
