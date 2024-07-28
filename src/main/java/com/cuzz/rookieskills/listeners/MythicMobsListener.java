package com.cuzz.rookieskills.listeners;

import com.cuzz.rookieskills.mythicmobs.mechanics.TestMechanic;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MythicMobsListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMythicMechanicLoad(MythicMechanicLoadEvent event) {
        if (event.getMechanicName().equalsIgnoreCase("TestMechanic"))
        {
            TestMechanic mechanic = new TestMechanic(event.getConfig());
            event.register(mechanic);
        }
    }
}
