package ru.liga.prerevolutionarytinderserver.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.liga.prerevolutionarytinderserver.entity.Person;

import java.util.Collection;

public interface PersonDAO {

    Person findPersonById(Long id);

    Person addPerson(Person person);

    Page<Person> findMyLikeList(Long id, PageRequest pageRequest);

    Integer checkReciprocity(Long personId, Long selectedPersonId);

    Integer countLikedByMe(Long personId, Long selectedPersonId);

    Integer countYouLikeMe(Long personId, Long selectedPersonId);

    Page<Person> findCandidateFavoritesByMe(Long id, Collection genders, Collection preferences, PageRequest pageRequest);
}
