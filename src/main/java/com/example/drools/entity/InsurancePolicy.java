package com.example.drools.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePolicy {

    private String name;
    private String surname;
    private int age;
    private String brand;
    private String model;
    private int carAge;
    private String color;
    private boolean hasAccident;
    private int insuranceTime;
    private int policyPrice = 3000;

}
