package com.cuzz.rookieskills.mythicmobs.mechanics;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.bukkit.BukkitAdapter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TestMechanic implements ITargetedEntitySkill {
    String aMessage;

    public TestMechanic(MythicLineConfig config) {
        this.aMessage = config.getString(   new String[]{"test_message"}, null);
        System.out.println("消息是: " +this.aMessage);
    }
    @Override
    public SkillResult castAtEntity(SkillMetadata skillMetadata, AbstractEntity target) {
        Entity player = BukkitAdapter.adapt(target);
        if (player instanceof Player) {
            player.sendMessage("自定义技能 参数值为"+aMessage);
        }
        return SkillResult.SUCCESS;
    }
}
