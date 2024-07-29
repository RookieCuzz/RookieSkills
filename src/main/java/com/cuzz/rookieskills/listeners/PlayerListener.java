package com.cuzz.rookieskills.listeners;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.bean.player.RpgPlayerData;
import com.cuzz.rookieskills.manager.RpgPlayerDataManager;
import org.bukkit.Material;
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
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        int newSlot = event.getNewSlot();
        int previousSlot = event.getPreviousSlot();

        // 检查玩家是否蹲下并选择第五个格子（槽位索引为 4）
        if (newSlot == 4 && player.isSneaking()) {
            ItemStack newItem = player.getInventory().getItem(newSlot);

            // 检查新选择的物品是否是技能模式的下界之星
            if (newItem != null && newItem.getType() == Material.NETHER_STAR) {
                ItemMeta meta = newItem.getItemMeta();
                if (meta != null && "技能模式".equals(meta.getDisplayName())) {
                    skillModeList.add(player);
                    player.sendMessage("您已进入技能模式");
                }
            }
        } else if (newSlot == 4) {
            if (previousSlot > newSlot) {
                player.sendMessage("跌");
                event.setCancelled(true);
                player.getInventory().setHeldItemSlot(3);
            } else if (previousSlot < newSlot) {
                player.sendMessage("升");
                event.setCancelled(true);
                player.getInventory().setHeldItemSlot(5);
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        RookieSkills.getInstance().getRpgPlayerDataManager().addPlayerData(event.getPlayer(), new RpgPlayerData(event.getPlayer()));
    }
}
