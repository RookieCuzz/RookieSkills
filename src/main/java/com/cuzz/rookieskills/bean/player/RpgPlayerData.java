package com.cuzz.rookieskills.bean.player;

import com.cuzz.rookieskills.bean.stats.Mana;
import org.bukkit.entity.Player;

public class RpgPlayerData {

    private Mana playerMana;

    private Double health;
    public RpgPlayerData(Player player){

        Mana mana = new Mana();
        mana.setBaseMana(100);
        mana.setExtraMana(0);
        mana.setRegenSpeed(0f);
        this.playerMana=mana;

        this.health=player.getHealth();



    }
}
