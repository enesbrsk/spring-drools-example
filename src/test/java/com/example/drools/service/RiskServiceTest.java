package com.example.drools.service;

import com.example.drools.controller.RiskController;
import org.springframework.util.StopWatch;
import com.example.drools.model.HomeRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
class RiskServiceTest {

    Logger logger = LoggerFactory.getLogger(RiskServiceTest.class);


    @InjectMocks
    private RiskService riskService;


    @Test
    public void droolsTest(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HomeRequest homeRequest = createHomeRequest();
        riskService.evaluateWithDrools(homeRequest);
        stopWatch.stop();
        logger.info("-----> Method executed for DROOLS in {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    public void javaTest(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HomeRequest homeRequest = createHomeRequest();
        riskService.evaluateWithCore(homeRequest);
        stopWatch.stop();
        logger.info("-----> Method executed for JAVA in {} ms", stopWatch.getTotalTimeMillis());
    }

    private HomeRequest createHomeRequest(){
        HomeRequest homeRequest = new HomeRequest();
        homeRequest.setFireAlarmInstalled(true);
        homeRequest.setNumberOfFloors(2);
        homeRequest.setSprinklerInstalled(true);

        return homeRequest;
    }

}