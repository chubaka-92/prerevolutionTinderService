package ru.liga.prerevolutionarytinderserver.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import ru.liga.prerevolutionarytinderserver.dto.PersonDto;
import ru.liga.prerevolutionarytinderserver.entity.Person;

public interface PersonService {
    ResponseEntity getPersonById(Long id);

    ResponseEntity getPersonPicture(Long id);

    ResponseEntity addNewPerson(Person person);

    ResponseEntity getImagePersonById(Long id);

    Page<Person> getPersons(PageRequest pageRequest);

    PersonDto getPersonsLikedByMe(Long id, PageRequest pageRequest);

    PersonDto getCandidateFavorites(Long id, PageRequest pageRequest);

}
