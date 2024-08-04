package com.cuzz.rookieskills.manager;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.bean.skill.Skill;
import com.cuzz.rookieskills.bean.skill.SkillPrototype;
import com.cuzz.rookieskills.bean.skill.skillimp.ItemSkillImp;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

import static com.cuzz.rookieskills.bean.skill.skillimp.ItemSkillImp.skillListX;

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

    public void initSkills(){
        ItemSkillImp itemSkillImp;
        for (SkillPrototype skillPrototype:skillPrototypeList.values()){
             itemSkillImp = new ItemSkillImp(skillPrototype);
            ItemSkillImp.getSkillListX().put(skillPrototype.getId(),itemSkillImp);

        }



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
