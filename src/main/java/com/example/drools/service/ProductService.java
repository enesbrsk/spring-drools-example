package com.example.drools.service;

import com.example.drools.configuration.DroolsBeanFactory;
import com.example.drools.entity.Product;
import org.kie.api.runtime.KieSession;

public class ProductService {

    private KieSession kieSession = new DroolsBeanFactory().getKieSession();

    public Product applyLabelToProduct(Product product){
        try {
            kieSession.insert(product);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }
        System.out.println(product.getLabel());
        return product;

    }

}
