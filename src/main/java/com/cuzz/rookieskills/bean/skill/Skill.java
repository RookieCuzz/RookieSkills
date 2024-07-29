package com.cuzz.rookieskills.bean.skill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Skill {
    private String name;
    private String id;
    private String symbol;
    private String cooldownDisplayName;
    private Double cooldownValue;
    private String damageDisplayName;
    private Double damageValue;
    private String manaDisplayName;
    private Double manaValue;
    private String staminaDisplayName;
    private Double staminaValue;
    private String radiusDisplayName;
    private Double radiusValue;
    private String durationDisplayName;
    private Double durationValue;
    private String timerDisplayName;
    private Double timerValue;
}
