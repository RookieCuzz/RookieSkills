package com.cuzz.rookieskills.bean.stats;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.value.qual.ArrayLenRange;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mana {
    private Double BaseMana;
    private Double maxMana;
    private Double regenSpeed;
}
