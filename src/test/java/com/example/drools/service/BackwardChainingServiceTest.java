package com.example.drools.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BackwardChainingServiceTest {

    @InjectMocks
    private BackwardChainingService backwardChainingService;

    @Test
    void backwardChainingWithDrools(){
        backwardChainingService.backwardChaining();
    }

}