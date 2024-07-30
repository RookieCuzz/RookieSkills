package com.cuzz.rookieskills.listeners;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.bean.SkillTable;
import com.cuzz.rookieskills.bean.player.RpgPlayerData;
import com.cuzz.rookieskills.manager.RpgPlayerDataManager;
import com.cuzz.rookieskills.manager.SkillTableManager;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerListener implements Listener {

    public ArrayList<Player> skillModeList=new ArrayList<>();

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (skillModeList.contains(player)){
            skillModeList.remove(player);
            player.sendMessage("你已退出技能模式");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerItemHeld2(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        int newSlot = event.getNewSlot();
        int previousSlot = event.getPreviousSlot();

    }
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        int newSlot = event.getNewSlot();
        int previousSlot = event.getPreviousSlot();
        // 检查玩家是否蹲下并选择第五个格子（槽位索引为 4） 该状态表示要进入技能模式
        if (newSlot == 4 && player.isSneaking()&&!skillModeList.contains(player)) {
            ItemStack newItem = player.getInventory().getItem(newSlot);
            // 检查新选择的物品是否是技能模式的下界之星
            if (newItem != null && newItem.getType() == Material.NETHER_STAR) {
                skillModeList.add(player);
                SkillTable skillTable = new SkillTable();
                skillTable.setSkillLoc(1,"Test1");
                skillTable.setSkillLoc(2,"Test2");
                skillTable.setSkillLoc(3,"Test3");
                skillTable.setSkillLoc(4,"Test4");
                skillTable.setSkillLoc(6,"Test6");
                skillTable.setSkillLoc(7,"Test7");
                skillTable.setSkillLoc(8,"Test8");
                skillTable.setSkillLoc(9,"Test9");
                skillTable.setOwner(player);
                SkillTableManager.PlayersSkillList.put(player,skillTable);
                player.sendMessage("您已进入技能模式");
            }else {
                event.setCancelled(true);
            }
        }else if (newSlot == 4){
            if (previousSlot > newSlot) {
                event.setCancelled(true);
                player.getInventory().setHeldItemSlot(3);
            } else if (previousSlot < newSlot) {
                event.setCancelled(true);
                player.getInventory().setHeldItemSlot(5);

            }
        }else if (previousSlot==4&&skillModeList.contains(player)){
            event.setCancelled(true);
            SkillTable skillTable = SkillTableManager.PlayersSkillList.get(player);
            String skillName = skillTable.getSkillByLoc(previousSlot);
            if(skillName!=null){
                MythicBukkit.inst().getAPIHelper().castSkill( player, skillName);
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        RookieSkills.getInstance().getRpgPlayerDataManager().addPlayerData(event.getPlayer(), new RpgPlayerData(event.getPlayer()));
    }
}
