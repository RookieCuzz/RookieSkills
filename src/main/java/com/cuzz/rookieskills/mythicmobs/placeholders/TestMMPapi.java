package com.cuzz.rookieskills.mythicmobs.placeholders;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.skills.placeholders.Placeholder;

public class TestMMPapi {

    public static void registerPlaceholders(){
        // Power placeholder
        register("modifier.damage", Placeholder.meta((meta, arg) -> {
            AbstractEntity entity = meta.getCaster().getEntity();
            String name = meta.getCaster().getName();
            return Double.toString(20);
        }));
        register("modifier.duration", Placeholder.meta((meta, arg) -> {
            AbstractEntity entity = meta.getCaster().getEntity();
            String name = meta.getCaster().getName();
            return Double.toString(20);
        }));
        register("modifier.mana", Placeholder.meta((meta, arg) -> {
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
