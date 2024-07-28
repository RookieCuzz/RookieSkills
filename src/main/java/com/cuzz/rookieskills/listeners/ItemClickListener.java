package com.cuzz.rookieskills.listeners;

import com.cuzz.rookieskills.RookieSkills;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemClickListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            if (item.getType() == Material.DIAMOND_SWORD) {

                if (meta != null) {
                    PersistentDataContainer PDC = meta.getPersistentDataContainer();

                    if (PDC.has(new NamespacedKey(RookieSkills.getInstance(),"hasSkill"), PersistentDataType.STRING)) {
                        if (PDC.get(new NamespacedKey(RookieSkills.getInstance(),"hasSkill"), PersistentDataType.STRING).equalsIgnoreCase("TestSkill1")) {
                            MythicBukkit.inst().getAPIHelper().castSkill(event.getPlayer(), "SmashAttack");
                        }
                    }
                }


            }
        }
    }
}
