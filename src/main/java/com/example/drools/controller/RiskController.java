package com.example.drools.controller;

import com.example.drools.entity.Home;
import com.example.drools.model.HomeRequest;
import com.example.drools.service.RiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.StopWatch;


@RestController
@RequestMapping("api/v1/risk")
public class RiskController {

    private final RiskService riskService;
    Logger logger = LoggerFactory.getLogger(RiskController.class);


    public RiskController(RiskService riskService) {
        this.riskService = riskService;
    }

    @PostMapping("/evaluate/drools")
    public Home evaluateHomeWithDrools(@RequestBody HomeRequest homeRequest){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Home evaluatedHome = riskService.evaluateWithDrools(homeRequest);
        stopWatch.stop();
        logger.info("-----> Method executed for DROOLS in {} ms", stopWatch.getTotalTimeMillis());
        return evaluatedHome;
    }
    @PostMapping("/evaluate/java")
    public Home evaluateHomeWithJava(@RequestBody HomeRequest homeRequest){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Home evaluatedHome = riskService.evaluateWithCore(homeRequest);
        stopWatch.stop();
        logger.info("-----> Method executed for JAVA in {} ms", stopWatch.getTotalTimeMillis());
        return evaluatedHome;
    }
}
