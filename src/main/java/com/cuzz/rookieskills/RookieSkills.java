package com.cuzz.rookieskills;

import com.cuzz.rookieskills.commands.TestCmds;
import com.cuzz.rookieskills.listeners.ItemClickListener;
import com.cuzz.rookieskills.listeners.PlayerListener;
import com.cuzz.rookieskills.manager.RpgPlayerDataManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class RookieSkills extends JavaPlugin {

    private static RookieSkills instance;
    @Getter
    private RpgPlayerDataManager rpgPlayerDataManager;
    public static RookieSkills getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance=this;
        this.rpgPlayerDataManager=RpgPlayerDataManager.getInstance();
        // Plugin startup logic
        this.getCommand("rsk").setExecutor((CommandExecutor)new TestCmds());

        // 注册监听器
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        getServer().getPluginManager().registerEvents(new ItemClickListener(), this);

        // 创建异步定时任务
        new BukkitRunnable() {
            @Override
            public void run() {
                // 获取所有在线玩家并发送消息
                for (Player player : rpgPlayerDataManager.getRpgPlayerDataList().keySet()) {
                    if (!player.isOnline())
                        rpgPlayerDataManager.removePlayerData(player);
                }
            }
        }.runTaskTimerAsynchronously(this, 0, 20 * 600); // 每 600 秒清理一次（20 ticks * 60 seconds）
    }

    @Override
    public void onDisable() {
        rpgPlayerDataManager=null;
        // Plugin shutdown logic
    }
}
