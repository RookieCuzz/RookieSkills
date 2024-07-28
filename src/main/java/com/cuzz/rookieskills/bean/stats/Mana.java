package com.cuzz.rookieskills.bean.stats;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.value.qual.ArrayLenRange;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mana {
    private Integer BaseMana;
    private Integer extraMana;
    private Float regenSpeed;
}
