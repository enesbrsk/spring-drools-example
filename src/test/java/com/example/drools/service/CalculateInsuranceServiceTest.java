package com.example.drools.service;

import com.example.drools.entity.InsurancePolicy;
import com.example.drools.model.HomeRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculateInsuranceServiceTest {

    Logger logger = LoggerFactory.getLogger(RiskServiceTest.class);


    @InjectMocks
    private CalculateInsuranceService calculateInsuranceService;

    @Test
    void getInsuranceWithDrools() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        InsurancePolicy policy = createInsurancePolicy();

        calculateInsuranceService.getInsurance(policy);

        stopWatch.stop();
        logger.info("executed for getInsuranceWithDrools in {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    void getInsuranceWithCoreJava() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        InsurancePolicy policy = createInsurancePolicy();
        calculateInsuranceService.getInsuranceWithCoreJava(policy);

        stopWatch.stop();
        logger.info("executed for getInsuranceWithCoreJava in {} ms", stopWatch.getTotalTimeMillis());
    }

    private InsurancePolicy createInsurancePolicy() {
        InsurancePolicy policy = new InsurancePolicy();
        policy.setHasAccident(true);
        policy.setAge(4);
        policy.setBrand("BMW");
        policy.setColor("RED");
        return policy;
    }
}