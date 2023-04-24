package com.example.drools.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private int age;
    private int monthlyIncome;
    private String occupation;
    int yearsOfExperience;
    private String maritalStatus;
    private int numberOfChildren;
    private int creditScore;
    private String residenceType;
    private int residenceDuration;
    private String vehicleType;
    private int yearsOfDrivingExperience;
    private boolean approved;
    private String reason;
}
