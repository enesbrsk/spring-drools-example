package com.example.drools.controller;

import com.example.drools.entity.InsurancePolicy;
import com.example.drools.service.CalculateInsuranceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/insurance")
public class CalculateInsuranceController {

    private final CalculateInsuranceService calculateInsuranceService;

    public CalculateInsuranceController(CalculateInsuranceService calculateInsuranceService) {
        this.calculateInsuranceService = calculateInsuranceService;
    }


    @PostMapping("drools")
    public InsurancePolicy getInsuranceWithDrools(@RequestBody InsurancePolicy insurancePolicy){
        return calculateInsuranceService.getInsurance(insurancePolicy);
    }

    @PostMapping("java")
    public InsurancePolicy getInsuranceWithJava(@RequestBody InsurancePolicy insurancePolicy){
        return calculateInsuranceService.getInsuranceWithCoreJava(insurancePolicy);
    }

}
