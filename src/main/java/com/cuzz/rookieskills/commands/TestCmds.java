package com.cuzz.rookieskills.commands;

import com.cuzz.rookieskills.RookieSkills;
import com.google.common.collect.ImmutableMap;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.skills.*;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.adapters.BukkitEntity;
import io.lumine.mythic.bukkit.adapters.BukkitPlayer;
import io.lumine.mythic.core.players.PlayerData;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillMetadataImpl;
import io.lumine.mythic.core.skills.SkillTriggers;
import io.lumine.mythic.core.skills.variables.Variable;
import io.lumine.mythic.core.skills.variables.VariableRegistry;
import io.lumine.mythic.core.utils.MythicUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import io.lumine.mythic.core.skills.placeholders.Placeholder;
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
    private Collection<AbstractEntity> findNearbyIronGolems(Player player, int radius) {
        Collection<AbstractEntity> golems = new ArrayList<>();

        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity.getType() == EntityType.IRON_GOLEM) {
                golems.add(new BukkitEntity(entity));
            }
        }
        return golems;
    }

    public void applySkillTransformation(SkillMetadata metadata) {
        // 对技能元数据进行转换或处理
        SkillCaster caster = metadata.getCaster();
        Collection<AbstractEntity> entityTargets = metadata.getEntityTargets();

        String name = caster.getName();
        Bukkit.getPlayer(name).sendMessage("BEFORE目标数量"+entityTargets.size());
        System.out.println("caster是"+name);
        Collection<AbstractEntity> nearbyIronGolems = findNearbyIronGolems(Bukkit.getPlayer(name), 5);
        Bukkit.getPlayer(name).sendMessage("铁傀儡数量"+nearbyIronGolems.size());
        metadata.setEntityTargets(nearbyIronGolems);
        System.out.println("对技能进行修正");
        Collection<AbstractEntity> entityTargets2 = metadata.getEntityTargets();
        Bukkit.getPlayer(name).sendMessage("AFTER目标数量"+entityTargets2.size());
        VariableRegistry variables = metadata.getVariables();
        ImmutableMap<String, Variable> map = variables.asMap();
        System.out.println(variables.asMap().size());
        for (String str:map.keySet()){
            Bukkit.getPlayer(name).sendMessage("key "+str);
        }
        // 其它操作...
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
                String skillName="TestSkill";
                SkillTrigger skillTrigger = SkillTrigger.get("onUse");
                PlayerData playerData = new PlayerData();
                playerData.initialize(new BukkitPlayer(player));

                MythicBukkit.inst().getAPIHelper().castSkill((Entity) sender,skillName,this::applySkillTransformation);
                Optional<Skill> skill = skillManager.getSkill(skillName);
                Skill skill1 = skill.get();

                Collection<SkillHolder> parents = skill1.getParents();
            }
            if (args[0].equalsIgnoreCase("Test2")){
                player.sendMessage(args[1]);
                MythicBukkit.inst().getAPIHelper().castSkill( (Entity)sender, args[1]);
                return true;
            }
            if(args[0].equalsIgnoreCase("addPDC")) {
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    PersistentDataContainer PDC = meta.getPersistentDataContainer();
                    PDC.set(new NamespacedKey(RookieSkills.getInstance(),"hasSkill"), PersistentDataType.STRING,"TestSkill1");
                    item.setItemMeta(meta);
                    player.sendMessage("添加成功!");
                }
            }
        }
        return true;
    }
}
