package com.cuzz.rookieskills;

import com.cuzz.rookieskills.commands.TestCmds;
import com.cuzz.rookieskills.listeners.MythicMobsListener;

import com.cuzz.rookieskills.listeners.ItemSkillListener;
import com.cuzz.rookieskills.listeners.PlayerListener;
import com.cuzz.rookieskills.manager.RpgPlayerDataManager;
import com.cuzz.rookieskills.manager.SkillConfigManager;
import com.cuzz.rookieskills.mythicmobs.placeholders.TestMMPapi;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class RookieSkills extends JavaPlugin {

    @Getter
    private static RookieSkills instance;
    @Getter
    private RpgPlayerDataManager rpgPlayerDataManager;
    @Getter
    private SkillConfigManager skillConfigManager;


    @Override
    public void onEnable() {
        instance = this;
        this.rpgPlayerDataManager = RpgPlayerDataManager.getInstance();
        this.skillConfigManager = SkillConfigManager.getInstance();
        // Plugin startup logic
        this.getCommand("rsk").setExecutor((CommandExecutor) new TestCmds());

        // 注册监听器
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new MythicMobsListener(), this);
        TestMMPapi.registerPlaceholders();

        getServer().getPluginManager().registerEvents(new ItemSkillListener(), this);

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

        skillConfigManager.loadSkills();
    }


    @Override
    public void onDisable() {
        rpgPlayerDataManager = null;
        // Plugin shutdown logic
    }
}
