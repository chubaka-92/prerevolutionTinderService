package ru.liga.prerevolutionarytinderserver.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.api.PersonMapper;
import ru.liga.prerevolutionarytinderserver.api.PersonService;
import ru.liga.prerevolutionarytinderserver.dao.PersonDAOImp;
import ru.liga.prerevolutionarytinderserver.dao.RelationshipsDAOImp;
import ru.liga.prerevolutionarytinderserver.dto.PersonDto;
import ru.liga.prerevolutionarytinderserver.entity.Person;
import ru.liga.prerevolutionarytinderserver.entity.Relationships;
import ru.liga.prerevolutionarytinderserver.type.FavoriteStatus;
import ru.liga.prerevolutionarytinderserver.type.Gender;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImp implements PersonService {

    private static final int TWO_ROWS = 2;
    private static final int ONE_ROWS = 1;
    private static final int ZERO = 0;
    private final PersonDAOImp personDao;
    private final RelationshipsDAOImp relationshipsDAO;
    private final PersonMapper personMapper;


    public Person getPersonById(Long id) {
        log.info("Was calling getPersonById. Input id: {}", id);
        return personDao.findPersonById(id);
    }

    public Person addNewPerson(Person person) {
        log.info("Was calling addNewPerson. Input person: {}", person);
        return personDao.addPerson(person);
    }

    @Override
    public PersonDto getPersonFormById(Long id) {
        log.info("Was calling getPersonFormById. Input id: {}", id);
        Person person = personDao.findPersonById(id);
        PersonDto personDto = personMapper.personToPersonDto(person);
        return personDto;
    }

    public PersonDto getMyLikePersonsList(Long id, PageRequest pageRequest) {
        log.info("Was calling getMyLikePersonsList. Input id: {}", id);
        Page<Person> personPage = personDao.findMyLikeList(id, pageRequest);
        if (personPage.getTotalPages() > ZERO) {
            Person person = personPage.getContent().get(ZERO);
            String status = getStatus(id, person.getId());
            return personMapper.personToPersonDtoByLikeList(
                    person,
                    status,
                    personPage.getTotalPages(),
                    personPage.getNumber());
        } else {
            return null;
        }
    }

    @Override
    public PersonDto getCandidateFavorites(Long id, PageRequest pageRequest) {
        log.info("Was calling getCandidateFavorites. Input id: {}", id);
        Page<Person> personPage = getCandidateFavorites(personDao.findPersonById(id), pageRequest);
        if (personPage.getTotalPages() > ZERO) {
            return personMapper.personPageToPersonDto(personPage);
        } else {
            return null;
        }
    }

    @Override
    public String addLikeFavorites(Long id, Long currentPersonId) {
        log.info("Was calling addLikeFavorites. Input id: {}  currentPersonId: {}", id, currentPersonId);
        relationshipsDAO.addRelationships(new Relationships(null, id, currentPersonId));
        if (getStatus(id, currentPersonId).equals(FavoriteStatus.RECIPROCITY.getTranslate())) {
            relationshipsDAO.updateReciprocity(id, currentPersonId);
        }
        return getStatus(id, currentPersonId);
    }

    private Page<Person> getCandidateFavorites(Person person, PageRequest pageRequest) {
        log.debug("Was calling getCandidateFavorites. Input person: {}", person);
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
        log.debug("Was calling getStatus. Input personId: {} selectedPersonId: {}", personId, selectedPersonId);
        if (personDao.checkReciprocity(personId, selectedPersonId) == TWO_ROWS) {
            return FavoriteStatus.RECIPROCITY.getTranslate();
        }
        if (personDao.countLikedByMe(personId, selectedPersonId) == ONE_ROWS) {
            return FavoriteStatus.LIKE_BE_ME.getTranslate();
        }
        if (personDao.countYouLikeMe(personId, selectedPersonId) == ONE_ROWS) {
            return FavoriteStatus.YOU_LIKE_ME.getTranslate();
        }
        return null;
    }
}
