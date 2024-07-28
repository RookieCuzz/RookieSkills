package com.cuzz.rookieskills.bean.player;

import com.cuzz.rookieskills.bean.stats.Mana;
import org.bukkit.entity.Player;

public class RpgPlayerData {

    private Mana playerMana;

    private Double health;
    private Player player;
    public RpgPlayerData(Player player){

        Mana mana = new Mana();
        mana.setBaseMana(100d);
        mana.setMaxMana(100d);
        mana.setRegenSpeed(0d);
        this.playerMana=mana;

        this.health=player.getHealth();
    }

    /**
     *
     * @param value 比较的值
     * @return  返回 是否有足够的蓝量
     */
    public boolean hasEnoughMana(Double value){
//        if (value>this.playerMana.getBaseMana()+this.playerMana.getExtraMana()){
//            return false;
//        }else{
//            return true;
//        }

        return  this.playerMana.getBaseMana() >value ;
    }

    public boolean takePlayerMana(Double value){
        if (!hasEnoughMana(value)){
            return false;
        }else {
            Double baseMana = this.playerMana.getBaseMana();
            this.playerMana.setBaseMana(baseMana-value);
            return true;
        }
    }
}
