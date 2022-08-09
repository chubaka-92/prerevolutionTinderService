package ru.liga.prerevolutionarytinderserver.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionarytinderserver.api.PersonDAO;
import ru.liga.prerevolutionarytinderserver.entity.Person;
import ru.liga.prerevolutionarytinderserver.repositories.PersonRepository;

import java.util.Collection;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PersonDAOImp implements PersonDAO {

    private final PersonRepository personRepository;

    public Person findPersonById(Long id) {
        log.info("Was calling findPersonById. Input id: {}", id);
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public Person addPerson(Person person) {
        log.info("Was calling addPerson. Input person: {}", person);
        return personRepository.save(person);
    }

    public Page<Person> findMyLikeList(Long id, PageRequest pageRequest) {
        log.info("Was calling findMyLikeList. Input id: {}", id);
        return personRepository.findMyLikeList(id, pageRequest);
    }

    public Integer checkReciprocity(Long id, Long selectedId) {
        log.info("Was calling findMyLikeList. Input id: {} selectedId: {}", id, selectedId);
        return personRepository.countReciprocity(id, selectedId);
    }

    public Integer countLikedByMe(Long id, Long selectedId) {
        log.info("Was calling countLikedByMe. Input id: {} selectedId: {}", id, selectedId);
        return personRepository.countLikedByMe(id, selectedId);
    }

    public Integer countYouLikeMe(Long id, Long selectedId) {
        log.info("Was calling countYouLikeMe. Input id: {} selectedId: {}", id, selectedId);
        return personRepository.countYouLikeMe(id, selectedId);
    }

    public Page<Person> findCandidateFavoritesByMe(
            Long id,
            Collection genders,
            Collection preferences,
            PageRequest pageRequest) {
        log.info("Was calling findCandidateFavoritesByMe. Input id: {}", id);
        return personRepository.findCandidateFavoritesByMe(id, genders, preferences, pageRequest);
    }
}