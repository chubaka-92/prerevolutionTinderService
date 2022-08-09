package ru.liga.prerevolutionarytinderserver.api;

import org.springframework.data.domain.PageRequest;
import ru.liga.prerevolutionarytinderserver.dto.PersonDto;
import ru.liga.prerevolutionarytinderserver.entity.Person;

public interface PersonService {

    Person getPersonById(Long id);

    Person addNewPerson(Person person);

    PersonDto getPersonFormById(Long id);

    PersonDto getMyLikePersonsList(Long id, PageRequest pageRequest);

    PersonDto getCandidateFavorites(Long id, PageRequest pageRequest);

    String addLikeFavorites(Long id, Long currentPersonId);
}
