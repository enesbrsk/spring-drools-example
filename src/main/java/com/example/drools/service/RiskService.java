package com.example.drools.service;

import com.example.drools.entitiy.Home;
import com.example.drools.model.HomeRequest;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class RiskService {

    private KieServices kieServices = KieServices.Factory.get();

    private final KieContainer kieContainer;

    public RiskService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public Home evaluate(HomeRequest homeRequest) {
        final Home home = convertToHome(homeRequest);

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(home);
        kieSession.fireAllRules();
        kieSession.dispose();
        return home;
    }

    private Home convertToHome(HomeRequest homeRequest){
        Home home = new Home();
        home.setFireAlarmInstalled(homeRequest.isFireAlarmInstalled());
        home.setNumberOfFloors(homeRequest.getNumberOfFloors());
        home.setSprinklerInstalled(homeRequest.isSprinklerInstalled());

        return home;
    }
}
