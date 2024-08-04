package com.cuzz.rookieskills.mythicmobs.placeholders;

import com.cuzz.rookieskills.api.ItemService;
import com.cuzz.rookieskills.bean.skill.skilldata.impl.ItemSkillData;
import com.cuzz.rookieskills.bean.skill.skillimp.ItemSkillImp;
import com.cuzz.rookieskills.listeners.ItemSkillListener;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.skills.SkillMetadataImpl;
import io.lumine.mythic.core.skills.placeholders.Placeholder;
import io.lumine.mythic.core.skills.placeholders.PlaceholderMeta;
import io.lumine.mythic.core.skills.placeholders.all.CasterDamagePlaceholder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;

public class TestMMPapi {

    public static void registerPlaceholders(){
        // Power placeholder
        register("modifier.damage", Placeholder.meta((meta, arg) -> {
            Double value=1d;
            AbstractEntity entity = meta.getCaster().getEntity();
            Player bukkitEntity = (Player)entity.getBukkitEntity();
            ItemStack itemInMainHand = bukkitEntity.getInventory().getItemInMainHand();
            String uuidFromItem = ItemService.getUUIDFromItem(itemInMainHand);
            if (uuidFromItem!=null){
                ItemSkillData itemSkillData = ItemSkillImp.cache.get(uuidFromItem);
                value = itemSkillData.getDamage();
            }
            return Double.toString(value);
        }));
        register("modifier.duration", Placeholder.meta((meta, arg) -> {
            AbstractEntity entity = meta.getCaster().getEntity();
            String name = meta.getCaster().getName();
            return Double.toString(20);
        }));
        register("modifier.ma·na", Placeholder.meta((meta, arg) -> {
            AbstractEntity entity = meta.getCaster().getEntity();
            String name = meta.getCaster().getName();
            return Double.toString(20);
        }));
        register("modifier.stamina", Placeholder.meta((meta, arg) -> {
            AbstractEntity entity = meta.getCaster().getEntity();
            String name = meta.getCaster().getName();
            return Double.toString(20);
        }));
        register("modifier.cooldown", Placeholder.meta((meta, arg) -> {
            AbstractEntity entity = meta.getCaster().getEntity();
            String name = meta.getCaster().getName();
            return Double.toString(20);
        }));
        register("modifier.timer", Placeholder.meta((meta, arg) -> {

            AbstractEntity entity = meta.getCaster().getEntity();
            String name = meta.getCaster().getName();
            return Double.toString(20);
        }));
        register("modifier.radius", Placeholder.meta((meta, arg) -> {
            AbstractEntity entity = meta.getCaster().getEntity();
            String name = meta.getCaster().getName();
            return Double.toString(20);
        }));
    }
    private static void register(String placeholder, Placeholder function)
    {
        MythicBukkit.inst().getPlaceholderManager().register(placeholder, function);
    }
}
