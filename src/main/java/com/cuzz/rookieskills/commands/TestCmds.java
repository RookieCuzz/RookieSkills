package com.cuzz.rookieskills.commands;

import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.api.skills.SkillTrigger;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.adapters.BukkitPlayer;
import io.lumine.mythic.core.players.PlayerData;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillTriggers;
import io.lumine.mythic.core.utils.MythicUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class TestCmds implements TabExecutor {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>() {{
            add("Test2");
        }};
    }
    private IronGolem findNearestIronGolem(Player player) {
        double closestDistance = Double.MAX_VALUE;
        IronGolem closestGolem = null;

        for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
            if (entity.getType() == EntityType.IRON_GOLEM) {
                double distance = player.getLocation().distance(entity.getLocation());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestGolem = (IronGolem) entity;
                }
            }
        }

        return closestGolem;
    }
    private Collection<IronGolem> findNearbyIronGolems(Player player, int radius) {
        Collection<IronGolem> golems = new ArrayList<>();

        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity.getType() == EntityType.IRON_GOLEM) {
                golems.add((IronGolem) entity);
            }
        }

        return golems;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args[0].equalsIgnoreCase("test")){
                player.sendMessage("进行测试！");
                final SkillExecutor skillManager = MythicBukkit.inst().getSkillManager();
                String skillName="SmashAttack";
                Optional<Skill> opt = skillManager.getSkill(skillName);
                Skill skill = opt.get();
                SkillTrigger skillTrigger=SkillTriggers.API;
                PlayerData playerData=new PlayerData(player.getUniqueId(),player.getName());
                playerData.initialize(new BukkitPlayer(player));
                IronGolem nearestIronGolem = findNearestIronGolem(player);

            }
            if (args[0].equalsIgnoreCase("Test2")){
                player.sendMessage(args[1]);
                MythicBukkit.inst().getAPIHelper().castSkill( (Entity)sender, args[1]);
                return true;
            }
        }
        return true;
    }
}
