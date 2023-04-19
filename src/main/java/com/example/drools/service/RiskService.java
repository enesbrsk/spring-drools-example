package com.example.drools.service;

import com.example.drools.entity.Home;
import com.example.drools.model.HomeRequest;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskService {

    private KieServices kieServices;
    private KieContainer kieContainer;

    public RiskService() {
        kieServices = KieServices.Factory.get();
        kieContainer = createKieContainer(kieServices, "rules/risk.drl");
    }

    public Home evaluateWithCore(HomeRequest homeRequest) {
        Home home = convertToHome(homeRequest);

        if (home.isFireAlarmInstalled() && home.getNumberOfFloors() < 3 && home.isSprinklerInstalled()) {
            home.setPolicyPrice(500 + home.getPolicyPrice());
        } else if (home.isFireAlarmInstalled() && home.getNumberOfFloors() > 2 && home.getNumberOfFloors() < 5 && home.isSprinklerInstalled()) {
            home.setPolicyPrice(1000);
        } else if (!home.isFireAlarmInstalled() && home.getNumberOfFloors() > 4 && !home.isSprinklerInstalled()) {
            home.setPolicyPrice(2000);
        }

        return home;
    }

    public Home evaluateWithDrools(HomeRequest homeRequest) {
        Home home = convertToHome(homeRequest);
        KieSession kieSession = kieContainer.newKieSession();

            kieSession.insert(home);
            kieSession.fireAllRules();
            kieSession.dispose();

        return home;
    }


    private Home multiEva(Home home) {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainerRisk2 = createKieContainer(kieServices, "rules/risk2.drl");
        KieSession kieSessionRisk2 = kieContainerRisk2.newKieSession();
        kieSessionRisk2.insert(home);
        kieSessionRisk2.fireAllRules();
        kieSessionRisk2.dispose();

        return home;
    }

    private KieContainer createKieContainer(KieServices kieServices, String ruleFilePath) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(ruleFilePath));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }


    private Home convertToHome(HomeRequest homeRequest) {
        Home home = new Home();
        home.setFireAlarmInstalled(homeRequest.isFireAlarmInstalled());
        home.setNumberOfFloors(homeRequest.getNumberOfFloors());
        home.setSprinklerInstalled(homeRequest.isSprinklerInstalled());

        return home;
    }
}
