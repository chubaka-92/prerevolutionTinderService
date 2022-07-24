package ru.liga.prerevolutionarytinderserver.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionarytinderserver.entity.Person;
import ru.liga.prerevolutionarytinderserver.repositories.PersonRepository;

@Repository
@RequiredArgsConstructor
public class PersonDAO {
    private final PersonRepository personRepository;

    public Person findPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public Person addPerson(Person person) {
        return personRepository.save(person);
    }
}