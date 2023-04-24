package com.example.drools.controller;

import com.example.drools.entity.Person;
import com.example.drools.model.PersonResponse;
import com.example.drools.service.PersonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("drools")
    public PersonResponse getPersonWithDrools(@RequestBody Person person){
        return personService.getPerson(person);
    }

}
