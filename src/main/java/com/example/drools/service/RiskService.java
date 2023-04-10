package com.example.drools.service;

import com.example.drools.entitiy.Home;
import com.example.drools.model.HomeRequest;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.io.KieResources;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskService {

    private final KieContainer kieContainer;

    @Autowired
    public RiskService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public Home evaluate(HomeRequest homeRequest) {
        Home home = convertToHome(homeRequest);

        home = multiEva(home);
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainerRisk1 = createKieContainer(kieServices, "rules/risk.drl");
        KieSession kieSessionRisk1 = kieContainerRisk1.newKieSession();
        kieSessionRisk1.insert(home);
        kieSessionRisk1.fireAllRules();
        kieSessionRisk1.dispose();



        return home;
    }

    private Home multiEva(Home home){
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


    private Home convertToHome(HomeRequest homeRequest){
        Home home = new Home();
        home.setFireAlarmInstalled(homeRequest.isFireAlarmInstalled());
        home.setNumberOfFloors(homeRequest.getNumberOfFloors());
        home.setSprinklerInstalled(homeRequest.isSprinklerInstalled());

        return home;
    }
}
