package ru.liga.prerevolutionarytinderserver.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    public Page<Person> findPersons(PageRequest pageRequest) {
        return personRepository.findAll(pageRequest);
    }

    public Page<Person> findLikedPersonsByMe(Long id,PageRequest pageRequest){
        return personRepository.findLikedPersonsByMe(id,pageRequest);
    }
}