package ru.liga.prerevolutionarytinderclient.servicies;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.dto.PersonsResponse;

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

    public void creatProfile(PersonRequest personRequest) {
        log.info("Was colling creatProfile. By personRequest: {}", personRequest);
        restTemplate.postForEntity(url + "", personRequest, String.class);
    }


    public PersonsResponse getFavorites(Long id, int page) {
        log.info("Was colling getFavorites. Input id: {} page: {}", id, page);
        PersonsResponse response = restTemplate.getForObject(url + "/favorites/" + id + "/pages/" + page, PersonsResponse.class);
        if (response == null) {
            throw new RuntimeException("Любимцев нет пуст");
        }
        return response;
    }

    public PersonsResponse getCandidateFavorites(Long id, int page) {
        log.info("Was colling getCandidateFavorites. Input id: {} page: {}", id, page);
        PersonsResponse response = restTemplate.getForObject(url + "/search/" + id + "/pages/" + page, PersonsResponse.class);
        if (response == null) {
            throw new RuntimeException("Кандидатов нет");
        }
        return response;
    }
}
