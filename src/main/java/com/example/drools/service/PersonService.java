package com.example.drools.service;

import com.example.drools.entity.InsurancePolicy;
import com.example.drools.entity.Person;
import com.example.drools.model.PersonResponse;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final KieServices kieServices = KieServices.Factory.get();
    private final KieContainer kieContainer = createKieContainer(kieServices, "rules/PersonCheck.drl");
    private final KieSession kieSession = kieContainer.newKieSession();

    private KieContainer createKieContainer(KieServices kieServices, String ruleFilePath) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(ruleFilePath));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    public PersonResponse getPerson(Person person) {
        kieSession.insert(person);
        kieSession.fireAllRules();
        return convertToPersonResponse(person);
    }

    private PersonResponse convertToPersonResponse(Person person){
        PersonResponse personResponse = new PersonResponse();
        personResponse.setReason(person.getReason());

        return personResponse;
    }

}
