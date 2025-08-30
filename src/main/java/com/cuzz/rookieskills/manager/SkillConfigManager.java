package com.cuzz.rookieskills.manager;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.bean.skill.Skill;
import com.cuzz.rookieskills.bean.skill.SkillPrototype;
import com.cuzz.rookieskills.bean.skill.skillimp.ItemSkillImp;
import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.manager.SkillManager;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class SkillConfigManager {

    @Getter
    private final HashMap<String, Skill> SkillList = new HashMap<>();

    @Getter
    private final HashMap<String, SkillPrototype> skillPrototypeList = new HashMap<>();
    private static SkillConfigManager singleton = null;

    private SkillConfigManager() {
    }

    public static SkillConfigManager getInstance() {
        if (singleton == null) {
            singleton = new SkillConfigManager();
        }
        return singleton;
    }

    public void initSkills() {
        ItemSkillImp itemSkillImp;
        for (SkillPrototype skillPrototype : skillPrototypeList.values()) {
            itemSkillImp = new ItemSkillImp(skillPrototype);
            ItemSkillImp.getSkillListX().put(skillPrototype.getId(), itemSkillImp);

        }


    }
    public void registerMMOlibSkill(FileConfiguration skillConfig){

        ConfigurationSection configurationSection = convertToMythicLibFormat(skillConfig);
        SkillManager skillManager = MythicLib.plugin.getSkills();

        skillManager.registerSkillHandler(skillManager.loadSkillHandler(configurationSection));
        System.out.println("加载技能: "+skillConfig.getString("name"));
    }

    public static ConfigurationSection convertToMythicLibFormat(ConfigurationSection thirdPartyConfig) {
        String skillName = thirdPartyConfig.getString("name");
        String skillId   = thirdPartyConfig.getString("id");
        if (skillName == null || skillName.isEmpty())
            throw new IllegalArgumentException("skill name missing");
        if (skillId == null || skillId.isEmpty())
            throw new IllegalArgumentException("skill id missing");

        YamlConfiguration root = new YamlConfiguration();
        YamlConfiguration section = new YamlConfiguration();
        section.set("mythicmobs-skill-id", skillId);

        ConfigurationSection modifierSection = thirdPartyConfig.getConfigurationSection("modifier");
        if (modifierSection != null) {
//            List<String> modifiers = new ArrayList<>(modifierSection.getKeys(false));
            List<String> modifiers = new ArrayList<>();
            modifiers.add("cooldown");
            modifiers.add("damage");
            section.set("modifiers", modifiers);
            // 如需默认值，可在这里把每个 modifier 的默认值也 set 到同一层：
            // for (String k : modifiers) section.set(k, modifierSection.get(k + ".value"));
        }

        root.set(skillName, section);
        return root.getConfigurationSection(skillName); // <-- 返回“技能名那一层”！
    }

    public void loadSkillPrototypes() {
        File SkillsFolder = new File(RookieSkills.getInstance().getDataFolder(), "Skills");

        if (!SkillsFolder.exists()) {
            SkillsFolder.mkdirs();
        }

        File[] SkillsFiles = SkillsFolder.listFiles((dir, name) -> name.endsWith(".yml"));

        if (SkillsFiles != null) {

            for (File skillsFile : SkillsFiles) {
                FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillsFile);
                try {
                    registerMMOlibSkill(skillConfig);
                }catch (Exception exception){
                    exception.printStackTrace();
                    System.out.println("A skill handler with the same name already exists"+skillConfig.getString("name"));
                    continue;
                }

                SkillPrototype skillPrototype = SkillPrototype.builder()
                        .name(skillConfig.getString("name"))
                        .id(skillConfig.getString("id"))
                        .symbol(skillConfig.getString("symbol"))
                        .coolDownDisplayName(skillConfig.getString("modifier.cooldown.name"))
                        .coolDownDefaultValue(skillConfig.getDouble("modifier.cooldown.default-value"))
                        .damageDisplayName(skillConfig.getString("modifier.damage.name"))
                        .damageDefaultValue(skillConfig.getDouble("modifier.damage.default-value"))
                        .manaDisplayName(skillConfig.getString("modifier.mana.name"))
                        .manaDefaultValue(skillConfig.getDouble("modifier.mana.default-value"))
                        .staminaDisplayName(skillConfig.getString("modifier.stamina.name"))
                        .staminaDefaultValue(skillConfig.getDouble("modifier.stamina.default-value"))
                        .radiusDisplayName(skillConfig.getString("modifier.radius.name"))
                        .radiusDefaultValue(skillConfig.getDouble("modifier.radius.default-value"))
                        .durationDisplayName(skillConfig.getString("modifier.duration.name"))
                        .durationDefaultValue(skillConfig.getDouble("modifier.duration.default-value"))
                        .timerDisplayName(skillConfig.getString("modifier.timer.name"))
                        .timerDefaultValue(skillConfig.getDouble("modifier.timer.default-value"))
                        .build();

                skillPrototypeList.put(skillConfig.getString("id"), skillPrototype);
            }
        }

        SkillManager skillManager = MythicLib.plugin.getSkills();
        skillManager.getHandlers().stream().forEach( item->{
            System.out.println(item.getId());
        });
    }

    public void loadSkills() {
        File SkillsFolder = new File(RookieSkills.getInstance().getDataFolder(), "Skills");

        if (!SkillsFolder.exists()) {
            SkillsFolder.mkdirs();
        }

        File[] SkillsFiles = SkillsFolder.listFiles((dir, name) -> name.endsWith(".yml"));

        if (SkillsFiles != null) {

            for (File skillsFile : SkillsFiles) {
                FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillsFile);
                Skill skill = Skill.builder()
                        .name(skillConfig.getString("name"))
                        .id(skillConfig.getString("id"))
                        .symbol(skillConfig.getString("symbol"))
                        .cooldownDisplayName(skillConfig.getString("modifier.cooldown.name"))
                        .cooldownValue(skillConfig.getDouble("modifier.cooldown.default-value"))
                        .damageDisplayName(skillConfig.getString("modifier.damage.name"))
                        .damageValue(skillConfig.getDouble("modifier.damage.default-value"))
                        .manaDisplayName(skillConfig.getString("modifier.mana.name"))
                        .manaValue(skillConfig.getDouble("modifier.mana.default-value"))
                        .staminaDisplayName(skillConfig.getString("modifier.stamina.name"))
                        .staminaValue(skillConfig.getDouble("modifier.stamina.default-value"))
                        .radiusDisplayName(skillConfig.getString("modifier.radius.name"))
                        .radiusValue(skillConfig.getDouble("modifier.radius.default-value"))
                        .durationDisplayName(skillConfig.getString("modifier.duration.name"))
                        .durationValue(skillConfig.getDouble("modifier.duration.default-value"))
                        .timerDisplayName(skillConfig.getString("modifier.timer.name"))
                        .timerValue(skillConfig.getDouble("modifier.timer.default-value"))
                        .build();

                SkillList.put(skillConfig.getString("id"), skill);
            }
        }
    }
}
