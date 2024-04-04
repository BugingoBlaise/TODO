package org.blaisesolutions.services;

import org.blaisesolutions.entity.Person;

import java.util.List;

public interface PersonService {

public static String NAME = "personService";
    String save(Person person);
    List<Person> queryAll();

    String softDelete(Person person);
}
