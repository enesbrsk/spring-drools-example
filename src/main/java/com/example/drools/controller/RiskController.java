package com.example.drools.controller;

import com.example.drools.entity.Home;
import com.example.drools.model.HomeRequest;
import com.example.drools.service.RiskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/risk")
public class RiskController {

    private final RiskService riskService;

    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @PostMapping("/evaluate/drools")
    public Home evaluateHomeWithDrools(@RequestBody HomeRequest homeRequest){
        Home evaluatedHome = riskService.evaluateWithDrools(homeRequest);
        return evaluatedHome;
    }
    @PostMapping("/evaluate/java")
    public Home evaluateHomeWithJava(@RequestBody HomeRequest homeRequest){
        Home evaluatedHome = riskService.evaluateWithDrools(homeRequest);
        return evaluatedHome;
    }
}
