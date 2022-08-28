package ru.liga.oldpictserv.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.liga.oldpictserv.entity.TempImageEntity;
import ru.liga.oldpictserv.entity.TextEntity;
import ru.liga.oldpictserv.painting.PictureService;

/**
 * Контроллер
 */
@Slf4j
@RestController
public class PictureServiceController {
    private final TempImageEntity tempImageEntity;
    private final PictureService pictureService;

    @Autowired
    public PictureServiceController(TempImageEntity tempImageEntity, PictureService pictureService) {
        this.tempImageEntity = tempImageEntity;
        this.pictureService = pictureService;
    }

    /**
     * POST Request
     *
     * @param textEntity текст
     * @return String Картинка
     */
    @PostMapping(value = "/pict", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] sendImage(@RequestBody TextEntity textEntity) {
        log.info("Receive POST Request");
        pictureService.createPicture(textEntity.getText());
        log.info("Send Response from POST Request");
        return tempImageEntity.getBytesImage();
    }

    /**
     * GET Request
     *
     * @return Ответ
     */
    @GetMapping
    public String getUrl() {
        log.info("Receive GET Request");
        return "Picture Service is working";
    }
}
