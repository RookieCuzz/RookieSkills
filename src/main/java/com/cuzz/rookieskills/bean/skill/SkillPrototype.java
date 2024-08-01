package com.cuzz.rookieskills.bean.skill;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class SkillPrototype {

    private String name;
    private String id;
    private String symbol;
    private String coolDownDisplayName;
    private Double coolDownDefaultValue;
    private String damageDisplayName;
    private Double damageDefaultValue;
    private String manaDisplayName;
    private Double manaDefaultValue;
    private String staminaDisplayName;
    private Double staminaDefaultValue;
    private String radiusDisplayName;
    private Double radiusDefaultValue;
    private String durationDisplayName;
    private Double durationDefaultValue;
    private String timerDisplayName;
    private Double timerDefaultValue;

}
