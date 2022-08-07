package ru.liga.prerevolutionarytinderclient.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.dto.PersonResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServer {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${telegram.host-url}")
    private String url;


    public PersonRequest getProfile(Long id) {
        log.info("Was colling getProfile. By id: {}", id);
        return restTemplate.getForObject(url + "/" + id, PersonRequest.class);
    }

    public PersonResponse getMyProfileForm(Long id) {
        log.info("Was colling getMyProfileForm. By id: {}", id);
        return restTemplate.getForObject(url + "/form/" + id, PersonResponse.class);
    }

    public void creatProfile(PersonRequest personRequest) {
        log.info("Was colling creatProfile. By personRequest: {}", personRequest);
        restTemplate.postForEntity(url + "", personRequest, String.class);
    }


    public PersonResponse getFavorites(Long id, int page) {
        log.info("Was colling getFavorites. Input id: {} page: {}", id, page);
        PersonResponse response = restTemplate.getForObject(url + "/favorites/" + id + "/pages/" + page, PersonResponse.class);
        if (response == null) {
            throw new RuntimeException("Любимцев нет пуст");
        }
        return response;
    }

    public PersonResponse getCandidateFavorites(Long id, int page) {
        log.info("Was colling getCandidateFavorites. Input id: {} page: {}", id, page);
        PersonResponse response = restTemplate.getForObject(url +"/" + id + "/search/pages/" + page, PersonResponse.class);
        if (response == null) {
            throw new RuntimeException("Кандидатов нет");
        }
        return response;
    }

    public String saveLikeFavorites(Long userId, Long currentPersonId) {
        log.info("Was colling getCandidateFavorites. Input userId: {} currentPersonId: {}", userId, currentPersonId);
        String  response = restTemplate.postForEntity(url + "/"+userId+"/search/",currentPersonId, String.class).getBody();
        return response;
    }
}
