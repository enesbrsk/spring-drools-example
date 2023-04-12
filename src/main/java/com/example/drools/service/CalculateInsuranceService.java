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
        System.out.println(insurancePolicy.getPolicyPrice());
        return insurancePolicy;
    }

    public InsurancePolicy getInsuranceWithCoreJava(InsurancePolicy policy) {
        if ((policy.getBrand().equals("BMW") || policy.getBrand().equals("AUDI"))) {
            policy.setPolicyPrice(10000 + policy.getPolicyPrice());
        }
        else if ((policy.getBrand().equals("RENAULT") || policy.getBrand().equals("OPEL"))) {
            policy.setPolicyPrice(5000 + policy.getPolicyPrice());
        }

        if (policy.getBrand().equals("RENAULT") && policy.getModel().equals("CLIO")) {
            policy.setPolicyPrice(100 + policy.getPolicyPrice());
        }
        else if (policy.getBrand().equals("RENAULT") && policy.getModel().equals("MEGANE")) {
            policy.setPolicyPrice(300 + policy.getPolicyPrice());
        }

        if ((policy.getBrand().equals("BMW") || policy.getBrand().equals("AUDI")) && policy.getCarAge() > 3) {
            int discountAmount = policy.getPolicyPrice() * 5 / 100;
            policy.setPolicyPrice(policy.getPolicyPrice() - discountAmount);
        }
        else if ((policy.getBrand().equals("RENAULT") || policy.getBrand().equals("OPEL")) && policy.getCarAge() > 3) {
            if (policy.getBrand().equals("RENAULT")) {
                policy.setPolicyPrice(policy.getPolicyPrice() - (policy.getPolicyPrice() * 7 / 100));
            } else if (policy.getBrand().equals("OPEL")) {
                policy.setPolicyPrice(policy.getPolicyPrice() - (policy.getPolicyPrice() * 10 / 100));
            }
        }

        if (policy.getColor().equals("RED")) {
            policy.setPolicyPrice(policy.getPolicyPrice() + (policy.getPolicyPrice() * 1 / 100));
        }

        if (policy.getAge() >= 2 && policy.getAge() <= 28) {
            policy.setPolicyPrice(policy.getPolicyPrice() + (policy.getPolicyPrice() * 12 / 100));
        }
        else if (policy.getAge() >= 28 && policy.getAge() <= 40) {
            policy.setPolicyPrice(policy.getPolicyPrice() + (policy.getPolicyPrice() * 9 / 100));
        }

        if (policy.getInsuranceTime() > 10 && !policy.isHasAccident()) {
            policy.setPolicyPrice(policy.getPolicyPrice() - (policy.getPolicyPrice() * 8 / 100));
        }
        else if (policy.getInsuranceTime() > 3 && policy.getInsuranceTime() <= 5 && !policy.isHasAccident()) {
            policy.setPolicyPrice(policy.getPolicyPrice() - (policy.getPolicyPrice() * 10 / 100));
        }
        else if (policy.getInsuranceTime() > 5 && !policy.isHasAccident()) {
            policy.setPolicyPrice(policy.getPolicyPrice() - (policy.getPolicyPrice() * 15 / 100));
        }

        if (policy.isHasAccident()) {
            policy.setPolicyPrice(policy.getPolicyPrice() + (policy.getPolicyPrice() * 12 / 100));
        }
        System.out.println(policy.getPolicyPrice());
        return policy;
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
