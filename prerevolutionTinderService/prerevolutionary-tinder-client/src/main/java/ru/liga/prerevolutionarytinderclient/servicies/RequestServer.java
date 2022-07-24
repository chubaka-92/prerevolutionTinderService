package ru.liga.prerevolutionarytinderclient.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;

import java.io.ByteArrayInputStream;

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
}
