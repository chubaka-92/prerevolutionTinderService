package ru.liga.prerevolutionarytinderserver.services;

import com.google.common.io.Files;
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
import ru.liga.prerevolutionarytinderserver.entity.Person;

import java.io.*;

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


    public Page<Person> getPersons(PageRequest pageRequest) {
        log.info("Was calling getPersonById");
        return personDao.findPersons(pageRequest);
    }

    public Page<Person> getPersonsLikedByMe(Long id, PageRequest pageRequest) {
        log.info("Was calling getPersonsLikedByMe");
        return personDao.findLikedPersonsByMe(id,pageRequest);
    }


    //@saivanov : тут я чото опять мудрил с картинкой...херня не рабочая вроде
    @Override
    public File getImagePersonById2(Long id) {
        log.info("Was calling getImagePersonById2. Input id: " + id);


        Person person = personDao.findPersonById(id);
        try {
            InputStream initialStream = new FileInputStream(
                    new File("src\\main\\resources\\wp4808160-amoled-cat-wallpapers.jpg"));
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);

            File targetFile = new File("src\\main\\resources\\targetFile.tmp");
            Files.write(buffer, targetFile);

            return targetFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
