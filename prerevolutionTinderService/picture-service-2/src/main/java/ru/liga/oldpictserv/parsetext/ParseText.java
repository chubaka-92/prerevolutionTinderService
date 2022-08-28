package ru.liga.oldpictserv.parsetext;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.oldpictserv.entity.LineEntity;
import ru.liga.oldpictserv.enums.TextType;
import ru.liga.oldpictserv.exception.ParseTextException;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ParseText {
    private final TextValidation textValidation;

    /**
     * Распарсить текст
     *
     * @param text текст
     * @return Map<String, String> распарсинный текст
     * @throws ParseTextException когда не может распарсить
     */
    public List<LineEntity> getLineEntityListByText(String text) {
        try {
            log.debug("parse text:{}", text);
            textValidation.validText(text);
            String header;
            if (text.contains("\n")) {
                header = text.split("\n")[0];
            } else {
                header = text.split(" ")[0];
            }
            String body = text.substring(header.length()).trim();
            List<LineEntity> lineEntityList = new LinkedList<>();
            lineEntityList.add(new LineEntity().builder().text(header).descriptor(TextType.header).build());
            lineEntityList.add(new LineEntity().builder().text(body).descriptor(TextType.body).build());
            log.debug("finish parse and get lineEntityList:{}", lineEntityList);
            return lineEntityList;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            log.error(e.getMessage(), e);
            throw new ParseTextException();
        }
    }
}
