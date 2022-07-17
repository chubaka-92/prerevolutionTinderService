package ru.liga.prerevolutionarytinderclient.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytinderclient.dto.PersonRequest;
import ru.liga.prerevolutionarytinderclient.dto.PersonResponse;

@Service
@RequiredArgsConstructor
public class RequestServer {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${telegram.host-url}")
    private String url;


    public PersonRequest getProfile(Long id) {
        return restTemplate.getForObject(url + "/"+id,PersonRequest.class);
    }
    public void creatProfile(PersonRequest personRequest) {
        restTemplate.postForEntity(url + "", personRequest, String.class);
    }

}
