package ru.liga.prerevolutionarytinderserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.dao.PersonDAO;
import ru.liga.prerevolutionarytinderserver.entity.Person;


@Service
@RequiredArgsConstructor
public class PersonServiceImp implements PersonService {

    private final PersonDAO personDao;

    public ResponseEntity getPersonById(Long id) {

        return ResponseEntity.ok(personDao.findPersonById(id));
    }

    public ResponseEntity addNewPerson(Person person) {

        return ResponseEntity.ok(personDao.addPerson(person));
    }
}
