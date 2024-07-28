package com.cuzz.rookieskills;

import com.cuzz.rookieskills.commands.TestCmds;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class RookieSkills extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("rsk").setExecutor((CommandExecutor)new TestCmds());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
