package ru.liga.prerevolutionarytinderserver.api;

import org.springframework.data.domain.Page;
import ru.liga.prerevolutionarytinderserver.dto.PersonDto;
import ru.liga.prerevolutionarytinderserver.entity.Person;

public interface PersonMapper {

    PersonDto personToPersonDto(Person person);

    PersonDto personPageToPersonDto(Page<Person> personPage);

    PersonDto personToPersonDtoByLikeList(Person person, String status, Integer totalPages, Integer currentPage);
}
