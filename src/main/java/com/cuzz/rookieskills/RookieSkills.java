package com.cuzz.rookieskills;

import com.cuzz.rookieskills.commands.TestCmds;
import com.cuzz.rookieskills.listeners.PlayerListener;
import com.cuzz.rookieskills.manager.RpgPlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class RookieSkills extends JavaPlugin {

    private static RookieSkills instance;
    private static RpgPlayerDataManager
    public static RookieSkills getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance=this;
        // Plugin startup logic
        this.getCommand("rsk").setExecutor((CommandExecutor)new TestCmds());
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        // 创建异步定时任务
        new BukkitRunnable() {
            @Override
            public void run() {
                // 获取所有在线玩家并发送消息
                for (Player player : Bukkit.getOnlinePlayers()) {

                }
            }
        }.runTaskTimerAsynchronously(this, 0, 20 * 60); // 每 60 秒发送一次消息（20 ticks * 60 seconds）
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
