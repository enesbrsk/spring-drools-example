package com.example.drools.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Home {
    private boolean fireAlarmInstalled;
    private int numberOfFloors;
    private boolean sprinklerInstalled;
    private int policyPrice;
}
