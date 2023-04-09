package com.example.drools.model;

import lombok.Data;

@Data
public class HomeRequest {
    private boolean fireAlarmInstalled;
    private int numberOfFloors;
    private boolean sprinklerInstalled;
}
