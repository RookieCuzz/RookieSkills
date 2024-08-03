package com.cuzz.rookieskills.bean.namespaces;

import com.cuzz.rookieskills.RookieSkills;
import org.bukkit.NamespacedKey;

public class AllNameSpacedKey {

    public static NamespacedKey skillId=new NamespacedKey(RookieSkills.getInstance(), "skillId");
    public static NamespacedKey skillName =new NamespacedKey(RookieSkills.getInstance(), "skillName");
    public static NamespacedKey skillCooldownValue=new NamespacedKey(RookieSkills.getInstance(), "skillCooldownValue");
    public static NamespacedKey skillDamageValue=new NamespacedKey(RookieSkills.getInstance(), "skillDamageValue");
    public static NamespacedKey skillManaValue=new NamespacedKey(RookieSkills.getInstance(), "skillManaValue");
    public static NamespacedKey skillStaminaValue=new NamespacedKey(RookieSkills.getInstance(), "skillStaminaValue");
    public static NamespacedKey skillRadiusValue=new NamespacedKey(RookieSkills.getInstance(), "skillRadiusValue");
    public static NamespacedKey skillDurationValue=new NamespacedKey(RookieSkills.getInstance(), "skillDurationValue");
    public static NamespacedKey skillTimerValue=new NamespacedKey(RookieSkills.getInstance(), "skillTimerValue");
}
