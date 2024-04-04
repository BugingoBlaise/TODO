package org.blaisesolutions.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.blaisesolutions.dao.PersonDao;
import org.blaisesolutions.entity.Person;
import org.blaisesolutions.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service(PersonService.NAME)
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PersonServiceImpl implements PersonService {
    private final PersonDao personDao;

    @Autowired
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public String save(Person person) {
        try {
            personDao.save(person);
            return "Person saved successfully";
        } catch (Exception e) {
            log.error("Error saving person: {}", e.getMessage());
            return "Failed to save person: " + e.getMessage();
        }
    }

    @Override
    public List<Person> queryAll() {
        return personDao.queryAll();
    }

    @Override
    public String softDelete(Person person) {
        try{
            personDao.softDelete(person);
            return "Deleted successfully";
        }catch (Exception ex){
            log.error("Error deleting person: {}", ex.getMessage());
            return "Failed to delete person: " + ex.getMessage();
        }
    }
}
