package com.cuzz.rookieskills.manager;

import com.cuzz.rookieskills.RookieSkills;
import com.cuzz.rookieskills.bean.skill.Skill;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class SkillConfigManager {

    @Getter
    private final HashMap<String, Skill> SkillList = new HashMap<>();

    private static SkillConfigManager singleton = null;

    private SkillConfigManager() {
    }

    public static SkillConfigManager getInstance() {
        if (singleton == null) {
            singleton = new SkillConfigManager();
        }
        return singleton;
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
                        .cooldownDisplayName(skillConfig.getString("modifier.damage.name"))
                        .cooldownValue(skillConfig.getDouble("modifier.damage.value"))
                        .manaDisplayName(skillConfig.getString("modifier.mana.name"))
                        .manaValue(skillConfig.getDouble("modifier.mana.value"))
                        .staminaDisplayName(skillConfig.getString("modifier.stamina.name"))
                        .staminaValue(skillConfig.getDouble("modifier.stamina.value"))
                        .radiusDisplayName(skillConfig.getString("modifier.radius.name"))
                        .radiusValue(skillConfig.getDouble("modifier.radius.value"))
                        .durationDisplayName(skillConfig.getString("modifier.duration.name"))
                        .durationValue(skillConfig.getDouble("modifier.duration.value"))
                        .timerDisplayName(skillConfig.getString("modifier.timer.name"))
                        .timerValue(skillConfig.getDouble("modifier.timer.value"))
                        .build();

                SkillList.put(skillConfig.getString("name"), skill);
            }
        }
    }
}
