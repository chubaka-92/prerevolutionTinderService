package ru.liga.prerevolutionarytinderserver.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import ru.liga.prerevolutionarytinderserver.entity.Person;

import java.io.File;

public interface PersonService {
    ResponseEntity getPersonById(Long id);

    ResponseEntity addNewPerson(Person person);

    ResponseEntity getImagePersonById(Long id);


    File getImagePersonById2(Long id);

    Page<Person> getPersons(PageRequest pageRequest);

}
