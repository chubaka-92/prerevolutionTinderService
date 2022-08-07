package ru.liga.prerevolutionarytinderserver.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.api.PersonService;
import ru.liga.prerevolutionarytinderserver.api.PictureWebService;
import ru.liga.prerevolutionarytinderserver.dao.PersonDAO;
import ru.liga.prerevolutionarytinderserver.dao.RelationshipsDAO;
import ru.liga.prerevolutionarytinderserver.dto.PersonDto;
import ru.liga.prerevolutionarytinderserver.entity.Person;
import ru.liga.prerevolutionarytinderserver.entity.Relationships;
import ru.liga.prerevolutionarytinderserver.type.FavoriteStatus;
import ru.liga.prerevolutionarytinderserver.type.Gender;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImp implements PersonService {

    private final PersonDAO personDao;
    private final RelationshipsDAO relationshipsDAO;

    private final PictureWebService pictureWebService;


    public ResponseEntity getPersonById(Long id) {
        log.info("Was calling getPersonById. Input id: " + id);
        return ResponseEntity.ok(personDao.findPersonById(id));
    }

    public ResponseEntity getPersonPicture(Long id) {
        log.info("Was calling getPersonPicture. Input id: " + id);
        return ResponseEntity.ok(pictureWebService.makePicture(personDao.findPersonById(id).getDescription()));
    }

    public Person addNewPerson(Person person) {
        log.info("Was calling addNewPerson. Input person: " + person);
        return personDao.addPerson(person);
    }

    @Override
    public PersonDto getPersonFormById(Long id) {
        log.info("Was calling getPersonFormById. Input id: " + id);

        Person person = personDao.findPersonById(id);
        PersonDto personDto = PersonDto.builder()
                .id(person.getId())
                .picture(getPicture(person.getDescription()))
                .caption(person.getGender() + ", " + person.getName())
                .build();
        return personDto;
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
                .id(person.getId())
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

        if(personsPage.getTotalPages() > 0){

        Person personResult = personsPage.getContent().get(0);

            PersonDto personDto = PersonDto.builder()
                    .id(personResult.getId())
                    .picture(getPicture(personResult.getDescription()))
                    .caption(personResult.getGender() + ", " + personResult.getName())
                    .totalPage(personsPage.getTotalPages())
                    .currentPage(personsPage.getNumber())
                    .build();
            return personDto;
        } else {
            return null;
        }
    }

    @Override
    public String addLikeFavorites(Long id, Long currentPersonId) {
        relationshipsDAO.addRelationships(new Relationships(null,id,currentPersonId));
        //проверяем есть ли взаимность, и если есть то проставляем флаги TRUE
        if ( getStatus(id,currentPersonId).equals(FavoriteStatus.RECIPROCITY.getTranslate())) {
            personDao.updateReciprocity(id, currentPersonId);
        }
        return getStatus(id,currentPersonId);
    }

    private Page<Person> getCandidateFavorites(Person person, PageRequest pageRequest) {
        log.debug("Was calling getCandidateFavorites");
        if (Gender.GENTLEMEN.getTranslate().equals(person.getPreference())) {
            return personDao.findCandidateFavoritesByMe(
                    person.getId(),
                    List.of(Gender.SIR.getTranslate()),
                    Arrays.asList(person.getPreference(), Gender.ALL.getTranslate()),
                    pageRequest);
        } else if (Gender.LADIES.getTranslate().equals(person.getPreference())) {
            return personDao.findCandidateFavoritesByMe(
                    person.getId(),
                    List.of(Gender.MADAM.getTranslate()),
                    Arrays.asList(person.getPreference(), Gender.ALL.getTranslate()),
                    pageRequest);
        } else if (Gender.ALL.getTranslate().equals(person.getPreference())) {
            return personDao.findCandidateFavoritesByMe(
                    person.getId(),
                    Arrays.asList(Gender.MADAM.getTranslate(), Gender.SIR.getTranslate()),
                    Arrays.asList(person.getPreference(), Gender.ALL.getTranslate()),
                    pageRequest);
        }
        return null;
    }

    private String getStatus(Long personId, Long selectedPersonId) {
        log.debug("Was calling getStatus");
        if (personDao.checkReciprocity(personId, selectedPersonId) == 2) {
            return FavoriteStatus.RECIPROCITY.getTranslate();
        }
        if (personDao.countLikedByMe(personId, selectedPersonId) == 1) {
            return FavoriteStatus.LIKE_BE_ME.getTranslate();
        }
        if (personDao.countYouLikeMe(personId, selectedPersonId) == 1) {
            return FavoriteStatus.YOU_LIKE_ME.getTranslate();
        }
        return null;
    }

    private byte[] getPicture(String description) {
        log.debug("Was calling getPicture");
        return pictureWebService.makePicture(description);
    }
}
