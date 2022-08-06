package ru.liga.prerevolutionarytinderserver.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.api.PersonService;
import ru.liga.prerevolutionarytinderserver.api.PictureWebService;
import ru.liga.prerevolutionarytinderserver.dao.PersonDAO;
import ru.liga.prerevolutionarytinderserver.dto.PersonDto;
import ru.liga.prerevolutionarytinderserver.entity.Person;
import ru.liga.prerevolutionarytinderserver.type.Gender;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImp implements PersonService {

    private final PersonDAO personDao;

    private final PictureWebService pictureWebService;


    public ResponseEntity getPersonById(Long id) {
        log.info("Was calling getPersonById. Input id: " + id);
        return ResponseEntity.ok(personDao.findPersonById(id));
    }

    public ResponseEntity getPersonPicture(Long id) {
        log.info("Was calling getPersonPicture. Input id: " + id);
        return ResponseEntity.ok(pictureWebService.makePicture2(personDao.findPersonById(id).getDescription()));
    }

    public ResponseEntity addNewPerson(Person person) {
        log.info("Was calling addNewPerson. Input person: " + person);
        try {
            person.setPicture(pictureWebService.makePicture(person.getDescription()).readAllBytes());
        } catch (IOException e) {
            log.info("Error in addNewPerson. Error: " + e);
        }
        return ResponseEntity.ok(personDao.addPerson(person));
    }

    @Override
    public ResponseEntity getImagePersonById(Long id) {
        log.info("Was calling getImagePersonById. Input id: " + id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        //из базы берем картиночку, приобразуем и отправляем в путишествие. через постман норм робит
        InputStreamResource inputStreamResource =
                new InputStreamResource(new ByteArrayInputStream(personDao.findPersonById(id).getPicture()));
        return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
    }


    public Page<Person> getPersons(PageRequest pageRequest) {
        log.info("Was calling getPersonById");
        return personDao.findPersons(pageRequest);
    }

    public PersonDto getPersonsLikedByMe(Long id, PageRequest pageRequest) {
        log.info("Was calling getPersonsLikedByMe");
        Page<Person> personPage = personDao.findMyLikeList(id, pageRequest);
        Person person = personPage.getContent().get(0);
        String status = getStatus(id, person.getId());
        PersonDto personDto = PersonDto.builder()
                .picture(getPicture(person.getDescription()))
                .caption(person.getGender() + ", " + person.getName() + ", " + status)
                .totalPage(personPage.getTotalPages())
                .currentPage(personPage.getNumber())
                .build();

        return personDto;
    }

    @Override
    public PersonDto getCandidateFavorites(Long id, PageRequest pageRequest) {
        log.info("Was calling getPersonsLikedByMe");

        Page<Person> personsPage = getCandidateFavorites(personDao.findPersonById(id), pageRequest);

        Person personResult = personsPage.getContent().get(0);
        PersonDto personDto = PersonDto.builder()
                .picture(getPicture(personResult.getDescription()))
                .caption(personResult.getGender() + ", " + personResult.getName())
                .totalPage(personsPage.getTotalPages())
                .currentPage(personsPage.getNumber())
                .build();
        return personDto;
    }

    private Page<Person> getCandidateFavorites(Person person, PageRequest pageRequest) {
        if (Gender.GENTLEMEN.getTranslate().equals(person.getPreference())) {
            return personDao.findCandidateFavoritesByMe(
                    person.getId(),
                    List.of(Gender.SIR.getTranslate()),
                    Arrays.asList(person.getGender(), Gender.ALL.getTranslate()),
                    pageRequest);
        } else if (Gender.LADIES.getTranslate().equals(person.getPreference())) {
            return personDao.findCandidateFavoritesByMe(
                    person.getId(),
                    List.of(Gender.MADAM.getTranslate()),
                    Arrays.asList(person.getGender(), Gender.ALL.getTranslate()),
                    pageRequest);
        } else if (Gender.ALL.getTranslate().equals(person.getPreference())) {
            return personDao.findCandidateFavoritesByMe(
                    person.getId(),
                    Arrays.asList(Gender.MADAM.getTranslate(), Gender.SIR.getTranslate()),
                    Arrays.asList(person.getGender(), Gender.ALL.getTranslate()),
                    pageRequest);
        }
        return null;
    }

    private String getStatus(Long personId, Long selectedPersonId) {

        if (personDao.checkReciprocity(personId, selectedPersonId) == 2) {
            return "Взаимность";
        }
        if (personDao.countLikedByMe(personId, selectedPersonId) == 1) {
            return "Любим вами.";
        }
        if (personDao.countYouLikeMe(personId, selectedPersonId) == 1) {
            return "Вы любимы.";
        }
        return null;
    }

    private byte[] getPicture(String description) {
        return pictureWebService.makePicture2(description);
    }
}
