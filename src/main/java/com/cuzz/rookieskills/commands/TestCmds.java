package com.cuzz.rookieskills.commands;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.api.ItemService;
import com.cuzz.rookieskills.bean.TriggerType;
import com.cuzz.rookieskills.manager.SkillConfigManager;
import com.google.common.collect.ImmutableMap;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.*;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.adapters.BukkitEntity;
import io.lumine.mythic.bukkit.adapters.BukkitPlayer;
import io.lumine.mythic.core.players.PlayerData;
import io.lumine.mythic.core.skills.*;
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

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import io.lumine.mythic.core.skills.placeholders.Placeholder;
import org.bukkit.plugin.Plugin;

public class TestCmds implements TabExecutor {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return new ArrayList<String>() {
                {
                    add("test");
                    add("Test2");
                    add("addSkill");
                }
            };
        } else if (args.length == 2 && args[0].equals("addSkill")) {
            return new ArrayList<>(RookieSkills.getInstance().getSkillConfigManager().getSkillList().keySet());
        } else if (args.length == 3 && args[0].equals("addSkill")) {
            return Arrays.stream(TriggerType.values()).parallel().map(Enum::name).collect(Collectors.toList());
        } else {
            return (List) Bukkit.getServer().getOnlinePlayers().parallelStream().map(Player::getName).collect(Collectors.toList());
        }
    }


    public void applySkillTransformation(SkillMetadata metadata) {
        // 对技能元数据进行转换或处理
        SkillCaster caster = metadata.getCaster();
        Collection<AbstractEntity> entityTargets = metadata.getEntityTargets();
        try {
            String name = caster.getName();
            Map<String, Object> metadata1 = ((SkillMetadataImpl) metadata).getMetadata();
            Bukkit.getPlayer(name).sendMessage("大小是"+metadata1.size());
        }catch (Exception e){
                e.printStackTrace();

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
            if (args[0].equalsIgnoreCase("test")) {
                player.sendMessage("进行测试！");
                final SkillExecutor skillManager = MythicBukkit.inst().getSkillManager();
                String skillName = "SmashAttack";
                SkillTrigger skillTrigger = SkillTrigger.get("onUse");
                PlayerData playerData = new PlayerData();
                playerData.initialize(new BukkitPlayer(player));
//                MythicBukkit.inst().getAPIHelper().castSkill((Entity) sender, skillName, this::applySkillTransformation);
                Optional<Skill> maybeSkill = MythicBukkit.inst().getSkillManager().getSkill(skillName);
                MetaSkill skill = (MetaSkill)maybeSkill.get();
                try {
                    Class<MetaSkill> metaSkillClass = MetaSkill.class;
                    Field skills = metaSkillClass.getDeclaredField("skills");
                    skills.setAccessible(true);
                    LinkedList<SkillMechanic> o = (LinkedList<SkillMechanic>) skills.get(skill);
                    player.sendMessage("子技能数量为"+o.size());
                    for (SkillMechanic item:o){
                        if (item.getTypeName().equalsIgnoreCase("damage")){
                            player.sendMessage(item.getTypeName());
                            String configLine = item.getConfigLine();
                            MythicLineConfig config = item.getConfig();
                            player.sendMessage("配置为"+ config.getLine());
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if (args[0].equalsIgnoreCase("Test2")) {
                player.sendMessage(args[1]);
                MythicBukkit.inst().getAPIHelper().castSkill((Entity) sender, args[1]);
                return true;
            }
            if (args[0].equalsIgnoreCase("addSkill")) {

                if (args[1] != null) {

                    ItemStack item = player.getInventory().getItemInMainHand();
                    ItemMeta meta = item.getItemMeta();

                    if (meta != null) {

                        com.cuzz.rookieskills.bean.skill.Skill skill = SkillConfigManager.getInstance().getSkillList().get(args[1]);

                        // 主键
                        NamespacedKey skillListKey = new NamespacedKey(RookieSkills.getInstance(), "skillList");

                        // 主容器
                        PersistentDataContainer skillList = meta.getPersistentDataContainer();

                        // 子容器
                        PersistentDataContainer skillInfo;
                        if (skillList.has(skillListKey, PersistentDataType.TAG_CONTAINER)) {
                            skillInfo = skillList.get(skillListKey, PersistentDataType.TAG_CONTAINER);
                        } else {
                            skillInfo = skillList.getAdapterContext().newPersistentDataContainer();
                        }

                        // 存储技能的信息
                        PersistentDataContainer skillData = skillInfo.getAdapterContext().newPersistentDataContainer();

                        skillData.set(new NamespacedKey(RookieSkills.getInstance(), "skillName"), PersistentDataType.STRING, skill.getName());
                        skillData.set(new NamespacedKey(RookieSkills.getInstance(), "skillId"), PersistentDataType.STRING,skill.getId());
                        skillData.set(new NamespacedKey(RookieSkills.getInstance(), "skillCooldownValue"), PersistentDataType.DOUBLE, skill.getCooldownValue());
                        skillData.set(new NamespacedKey(RookieSkills.getInstance(), "skillDamageValue"), PersistentDataType.DOUBLE, skill.getDamageValue());
                        skillData.set(new NamespacedKey(RookieSkills.getInstance(), "skillManaValue"), PersistentDataType.DOUBLE, skill.getManaValue());
                        skillData.set(new NamespacedKey(RookieSkills.getInstance(), "skillStaminaValue"), PersistentDataType.DOUBLE, skill.getStaminaValue());
                        skillData.set(new NamespacedKey(RookieSkills.getInstance(), "skillRadiusValue"), PersistentDataType.DOUBLE, skill.getRadiusValue());
                        skillData.set(new NamespacedKey(RookieSkills.getInstance(), "skillDurationValue"), PersistentDataType.DOUBLE, skill.getDurationValue());
                        skillData.set(new NamespacedKey(RookieSkills.getInstance(), "skillTimerValue"), PersistentDataType.DOUBLE, skill.getTimerValue());

                        skillInfo.set(new NamespacedKey(RookieSkills.getInstance(), args[2]), PersistentDataType.TAG_CONTAINER, skillData);

                        // 将子容器再装入主容器
                        skillList.set(skillListKey, PersistentDataType.TAG_CONTAINER, skillInfo);

                        // 将元数据应用回物品
                        item.setItemMeta(meta);

                        ItemService.addUUIDToItem(item);

                        player.sendMessage("添加成功!已为手上物品添加技能: " + args[1] + "触发方式为: " + args[2]);

                    }
                }
            }
        }
        return true;
    }
}
