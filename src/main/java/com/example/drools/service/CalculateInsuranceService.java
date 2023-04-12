package com.example.drools.service;

import com.example.drools.entity.Home;
import com.example.drools.entity.InsurancePolicy;
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
public class CalculateInsuranceService {

    private final KieContainer kieContainer;

    @Autowired
    public CalculateInsuranceService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public InsurancePolicy getInsurance(InsurancePolicy insurancePolicy) {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainerRisk1 = createKieContainer(kieServices, "rules/InsuranceCheck.drl");
        KieSession kieSessionRisk1 = kieContainerRisk1.newKieSession();
        kieSessionRisk1.insert(insurancePolicy);
        kieSessionRisk1.fireAllRules();
        kieSessionRisk1.dispose();

        return insurancePolicy;
    }

    private KieContainer createKieContainer(KieServices kieServices, String ruleFilePath) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(ruleFilePath));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }


}
