package com.example.drools.service;

import com.example.drools.configuration.DroolsBeanFactory;
import com.example.drools.entity.Fact;
import com.example.drools.entity.Result;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Service;

@Service
public class BackwardChainingService {

    public Result backwardChaining() {
        Result result = new Result();
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainerRisk1 = createKieContainer(kieServices, "com/baeldung/drools/rules/BackwardChaining.drl");
        KieSession ksession = kieContainerRisk1.newKieSession();
        ksession.setGlobal("result", result);
        ksession.insert(new Fact("Asia", "Planet Earth"));
        ksession.insert(new Fact("China", "Asia"));
        ksession.insert(new Fact("Great Wall of China", "China"));

        ksession.fireAllRules();

        return result;
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
