package com.cuzz.rookieskills.mythicmobs.targeters;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.skills.targeters.IEntitySelector;

import java.util.Collection;
import java.util.HashSet;

public class TestTarget extends IEntitySelector {

    public TestTarget(MythicLineConfig paramMythicLineConfig) {
        super( MythicBukkit.inst().getSkillManager(),paramMythicLineConfig);
    }

    public Collection<AbstractEntity> getEntities(SkillMetadata paramSkillMetadata) {
        HashSet<AbstractEntity> hashSet = new HashSet();
        AbstractEntity abstractEntity = paramSkillMetadata.getCaster().getEntity();

        return hashSet;
    }


}