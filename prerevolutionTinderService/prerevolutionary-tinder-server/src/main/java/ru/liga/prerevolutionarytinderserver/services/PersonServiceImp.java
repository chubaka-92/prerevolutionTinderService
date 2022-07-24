package ru.liga.prerevolutionarytinderserver.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionarytinderserver.api.PersonService;
import ru.liga.prerevolutionarytinderserver.api.PictureWebService;
import ru.liga.prerevolutionarytinderserver.dao.PersonDAO;
import ru.liga.prerevolutionarytinderserver.entity.Person;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
}
