package ru.liga.prerevolutionarytinderserver.services;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutionarytinderserver.api.PictureWebService;

@Slf4j
@Service
public class PictureWebServiceImp implements PictureWebService {

    private final String createPersonUrl = "https://pict-serv-2-0-fixey-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com/pict";

    public byte[] makePicture(String text) {
        log.info("Was calling makePicture. Input text: {}", text);
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject pictureJsonObject = new JSONObject();
            pictureJsonObject.put("text", text);
            HttpEntity<String> request = new HttpEntity<>(pictureJsonObject.toString(), headers);
            byte[] picture = restTemplate.postForObject(createPersonUrl, request, byte[].class);
            if (picture == null) {
                throw new RuntimeException("Что то пошло не так при формировании картинки");
            }
            return picture;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
