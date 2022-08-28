package ru.liga.oldpictserv.parsetext;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.oldpictserv.exception.EmptyTextException;
import ru.liga.oldpictserv.exception.FormatTextException;

/**
 * Валидация текста
 */
@Service
public class TextValidation {
    public void validText(String text) {
        if (text == null) {
            throw new EmptyTextException();
        }
        if (!(text.contains("\n") || text.contains(" "))) {
            throw new FormatTextException();
        }
    }
}
