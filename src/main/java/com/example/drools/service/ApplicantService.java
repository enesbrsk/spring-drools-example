package com.example.drools.service;

import com.example.drools.configuration.DroolsBeanFactory;
import com.example.drools.entity.Applicant;
import com.example.drools.entity.SuggestedRole;
import org.kie.api.runtime.KieSession;

public class ApplicantService {

    KieSession kieSession = new DroolsBeanFactory().getKieSession();

    public SuggestedRole suggestARoleForApplicant(Applicant applicant,SuggestedRole suggestedRole) {
        try {
            kieSession.insert(applicant);
            kieSession.setGlobal("suggestedRole", suggestedRole);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(suggestedRole.getRole());
        return  suggestedRole;

    }


}
