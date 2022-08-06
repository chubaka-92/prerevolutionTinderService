package ru.liga.prerevolutionarytinderserver.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionarytinderserver.entity.Person;
import ru.liga.prerevolutionarytinderserver.repositories.PersonRepository;

import java.util.Collection;

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

    public Page<Person> findMyLikeList(Long id, PageRequest pageRequest){
        return personRepository.findMyLikeList(id,pageRequest);
    }

    public Integer checkReciprocity(Long personId, Long selectedPersonId) {
        return personRepository.countReciprocity(personId,selectedPersonId);
    }

    public Integer countLikedByMe(Long personId, Long selectedPersonId) {
        return personRepository.countLikedByMe(personId,selectedPersonId);
    }

    public Integer countYouLikeMe(Long personId, Long selectedPersonId) {
        return personRepository.countYouLikeMe(personId,selectedPersonId);
    }

    public Page<Person> findCandidateFavoritesByMe(Long id, Collection genders,Collection preferences, PageRequest pageRequest) {
        return personRepository.findCandidateFavoritesByMe(id,genders,preferences, pageRequest);
    }
}