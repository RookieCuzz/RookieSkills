package com.cuzz.rookieskills.listeners;

import com.cuzz.rookieskills.bean.player.RpgPlayerData;
import com.cuzz.rookieskills.manager.RpgPlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    RpgPlayerDataManager rpgPlayerDataManager;

    {
        this.rpgPlayerDataManager= RpgPlayerDataManager.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        rpgPlayerDataManager.addPlayerData(event.getPlayer(), new RpgPlayerData(event.getPlayer()));
    }
}
