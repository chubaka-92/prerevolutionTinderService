package ru.liga.prerevolutionarytinderclient.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.prerevolutionarytinderclient.dto.PersonPageResponse;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServer {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${telegram.host-url}")
    private String url;


    public PersonRequest getProfile(Long id) {
        log.info("Was colling getProfile. By id: " + id);
        return restTemplate.getForObject(url + "/" + id, PersonRequest.class);
    }

    public void creatProfile(PersonRequest personRequest) {
        log.info("Was colling creatProfile. By personRequest: " + personRequest);
        restTemplate.postForEntity(url + "", personRequest, String.class);
    }

    public InputFile getProfileImage(Long id) {
        byte[] imageBytes = restTemplate.getForEntity(url + "/" + id + "/image", byte[].class).getBody();

        return new InputFile().setMedia(new ByteArrayInputStream(imageBytes), "image.jpg");
    }

    public InputStream getProfileImage2(Long id) {

        return restTemplate.getForEntity(url + "/" + id + "/image2", InputStream.class).getBody();
    }

    public PersonRequest getFavorites(Long id, int page) {
        PersonPageResponse response = restTemplate.getForObject(url + "/"+id+"/pages/"+page, PersonPageResponse.class);

        PersonRequest person = response.getContent().get(0);
        person.setTotalPage((int) response.getTotalElements());
        person.setCurrentPage(response.getNumber());

        return person;
    }
}
