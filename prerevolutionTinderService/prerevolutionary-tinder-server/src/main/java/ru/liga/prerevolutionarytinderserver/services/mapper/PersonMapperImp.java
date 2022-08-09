package ru.liga.prerevolutionarytinderserver.services.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.api.PersonMapper;
import ru.liga.prerevolutionarytinderserver.api.PictureWebService;
import ru.liga.prerevolutionarytinderserver.dto.PersonDto;
import ru.liga.prerevolutionarytinderserver.entity.Person;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonMapperImp implements PersonMapper {

    private final PictureWebService pictureWebService;

    public PersonDto personToPersonDto(Person person) {
        log.info("Was calling personToPersonDto. Input person: {}", person);
        return PersonDto.builder()
                .id(person.getId())
                .picture(getPicture(person.getDescription()))
                .caption(person.getGender() + ", " + person.getName())
                .build();
    }

    public PersonDto personPageToPersonDto(Page<Person> personPage) {
        log.info("Was calling personPageToPersonDto.");
        Person personResult = personPage.getContent().get(0);
        return PersonDto.builder()
                .id(personResult.getId())
                .picture(getPicture(personResult.getDescription()))
                .caption(personResult.getGender() + ", " + personResult.getName())
                .totalPage(personPage.getTotalPages())
                .currentPage(personPage.getNumber())
                .build();
    }

    public PersonDto personToPersonDtoByLikeList(Person person, String status, Integer totalPages, Integer currentPage) {
        log.info("Was calling personToPersonDtoByLikeList. Input person: {} status: {} totalPages: {} currentPage: {}",
                person,
                status,
                totalPages,
                currentPage);

        return PersonDto.builder()
                .id(person.getId())
                .picture(getPicture(person.getDescription()))
                .caption(person.getGender() + ", " + person.getName() + ", " + status)
                .totalPage(totalPages)
                .currentPage(currentPage)
                .build();
    }

    private byte[] getPicture(String description) {
        log.debug("Was calling getPicture. Input description: {}", description);
        return pictureWebService.makePicture(description);
    }
}
