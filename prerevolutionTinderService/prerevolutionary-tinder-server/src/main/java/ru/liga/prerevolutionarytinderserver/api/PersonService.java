package ru.liga.prerevolutionarytinderserver.api;

import org.springframework.http.ResponseEntity;
import ru.liga.prerevolutionarytinderserver.entity.Person;

public interface PersonService {
    ResponseEntity getPersonById(Long id);

    ResponseEntity addNewPerson(Person person);

    ResponseEntity getImagePersonById(Long id);
}
